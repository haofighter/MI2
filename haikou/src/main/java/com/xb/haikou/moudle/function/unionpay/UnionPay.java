package com.xb.haikou.moudle.function.unionpay;

import com.szxb.java8583.core.Iso8583Message;
import com.szxb.java8583.core.Iso8583MessageFactory;
import com.szxb.java8583.module.SignIn;
import com.szxb.java8583.module.manager.BusllPosManage;
import com.szxb.java8583.quickstart.SingletonFactory;
import com.szxb.java8583.quickstart.special.SpecialField62;
import com.szxb.mlog.SLog;
import com.xb.haikou.base.App;
import com.xb.haikou.db.dao.UnionPayEntityDao;
import com.xb.haikou.db.manage.DBCore;
import com.xb.haikou.moudle.function.unionpay.config.UnionConfig;
import com.xb.haikou.moudle.function.unionpay.entity.UnionPayEntity;
import com.xb.haikou.moudle.function.unionpay.http.CallServer;
import com.xb.haikou.moudle.function.unionpay.http.HttpListener;
import com.xb.haikou.moudle.function.unionpay.unionutil.SSLContextUtil;
import com.xb.haikou.util.BusToast;
import com.xb.haikou.voice.SoundPoolUtil;
import com.xb.haikou.voice.VoiceConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.xb.haikou.moudle.function.unionpay.unionutil.HexUtil.yuan2Fen;
import static com.xb.haikou.moudle.function.unionpay.unionutil.ParseUtil.parseMackey;


/**
 * 作者：Tangren on 2018-07-06
 * 包名：com.szxb.unionpay
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class UnionPay {

    private volatile static UnionPay instance = null;

    private UnionPay() {
    }

    public static UnionPay getInstance() {
        if (instance == null) {
            synchronized (UnionPay.class) {
                if (instance == null) {
                    instance = new UnionPay();
                }
            }
        }
        return instance;
    }

    public void exeSSL(int what, byte[] sendData) {
        exeSSL(what, sendData, false);
    }

    public void exeSSL(int what, byte[] sendData, final boolean isTip) {

        String url = BusllPosManage.getPosManager().getUnionPayUrl();
        final Request<byte[]> request = NoHttp.createByteArrayRequest(url, RequestMethod.POST);
        request.setHeader("User-Agent", "Donjin Http 0.1");
        request.setHeader("Cache-Control", "no-cache");
        request.setHeader("Accept", "*/*");
        request.setHeader("Accept-Encoding", "*");
        request.setHeader("Connection", "close");
        request.setHeader("HOST", "120.204.69.139:30000");

        if (what == UnionConfig.PAY) {
            request.setConnectTimeout(3000);
            request.setReadTimeout(3000);
        }
        try {
            InputStream stream = new ByteArrayInputStream(sendData);
            request.setDefineRequestBody(stream, "x-ISO-TPDU/x-auth");
            SSLContext sslContext = SSLContextUtil.getSSLContext(App.getInstance());
            request.setHostnameVerifier(SSLContextUtil.getHostnameVerifier());
            if (sslContext != null) {
                SSLSocketFactory socketFactory = sslContext.getSocketFactory();
                request.setSSLSocketFactory(socketFactory);

                CallServer.getHttpclient().add(what, request, new HttpListener<byte[]>() {
                    @Override
                    public void success(int what, Response<byte[]> response) {
                        try {
                            Iso8583MessageFactory factory = SingletonFactory.forQuickStart();
                            factory.setSpecialFieldHandle(62, new SpecialField62());
                            Iso8583Message message0810 = factory.parse(response.get());

                            if (what == UnionConfig.SIGN) {//签到
                                if (message0810.getValue(39).getValue().equals("00")) {
                                    String batchNum = message0810.getValue(60).getValue().substring(2, 8);
                                    BusllPosManage.getPosManager().setBatchNum(batchNum);
                                    parseMackey(message0810.getValue(62).getValue(), isTip);
                                } else {
                                    if (isTip) {
                                        BusToast.showToast("银联签到失败[" + message0810.getValue(39).getValue() + "]", false);
                                    }
                                }
                            } else if (what == UnionConfig.PAY) {//消费
                                String pay_fee = message0810.getValue(4).getValue();
                                String resCode = message0810.getValue(39).getValue();
                                String tradeSeq = message0810.getValue(11).getValue();
                                String batchNum = message0810.getValue(60).getValue().substring(2, 8);
                                String uniqueFlag = tradeSeq + batchNum;
                                UnionPayEntityDao dao = DBCore.getDaoSession().getUnionPayEntityDao();
                                UnionPayEntity unique = dao.queryBuilder()
                                        .where(UnionPayEntityDao.Properties.UniqueFlag
                                                .eq(uniqueFlag)).limit(1).build().unique();
                                if (unique != null) {
                                    unique.setResCode(resCode);
                                    switch (resCode) {
                                        case "00":
                                        case "A2":
                                        case "A4":
                                        case "A5":
                                        case "A6":
                                            //支付成功
                                            unique.setPayFee(pay_fee);
                                            SoundPoolUtil.play(VoiceConfig.ZHIFU_SUC);
                                            String amount = message0810.getValue(4).getValue();
                                            BusToast.showToast("扣款成功\n扣款金额" + yuan2Fen(amount) + "元", true);
                                            SLog.d("UnionPay(success.java:104)修改成功");
                                            break;
                                        case "A0":
                                            //重新签到
                                            BusToast.showToast("刷卡失败,正在签到,稍后重试", false);
                                            BusllPosManage.getPosManager().setTradeSeq();
                                            Iso8583Message message = SignIn.getInstance().message(BusllPosManage.getPosManager().getTradeSeq());
                                            UnionPay.getInstance().exeSSL(UnionConfig.SIGN, message.getBytes());
                                            break;
                                        case "94":
                                            //重复交易（流水号重复）
                                            BusToast.showToast("刷卡失败,流水重复,请重试", false);
                                            SLog.d("UnionPay(success.java:104)重复支付");
                                            break;
                                        case "51":
                                            //余额不足
                                            SoundPoolUtil.play(VoiceConfig.EC_BALANCE);
                                            BusToast.showToast("刷卡失败,余额不足", false);
                                            SLog.d("UnionPay(success.java:130)余额不足");
                                            break;
                                        case "54":
                                            //卡过期
                                            SoundPoolUtil.play(VoiceConfig.IC_PUSH_MONEY);
                                            BusToast.showToast("卡过期", false);
                                            SLog.d("UnionPay(success.java:136)卡过期");
                                            break;
                                        default:
                                            SLog.d("刷卡失败报错" + UnionUtil.unionPayStatus(resCode));
                                            BusToast.showToast("刷卡失败[" + UnionUtil.unionPayStatus(resCode) + "]", false);
                                            break;
                                    }
                                }
                                dao.update(unique);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            BusToast.showToast("刷卡失败[异常]\n" + e.toString(), false);
                        }
                    }

                    @Override
                    public void fail(int what, String e) {
                        BusToast.showToast("刷卡失败[超时]", false);
                    }
                });
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public byte[] exeSyncSSL(byte[] sendData) {
        String url = BusllPosManage.getPosManager().getUnionPayUrl();
        Request<byte[]> request = NoHttp.createByteArrayRequest(url, RequestMethod.POST);
        request.setHeader("User-Agent", "Donjin Http 0.1");
        request.setHeader("Cache-Control", "no-cache");
        request.setHeader("Accept", "*/*");
        request.setHeader("Accept-Encoding", "*");
        request.setHeader("Connection", "close");
        request.setHeader("HOST", "120.204.69.139:30000");

        InputStream stream = new ByteArrayInputStream(sendData);
        request.setDefineRequestBody(stream, "x-ISO-TPDU/x-auth");
        SSLContext sslContext = SSLContextUtil.getSSLContext(App.getInstance().getApplicationContext());
        request.setHostnameVerifier(SSLContextUtil.getHostnameVerifier());

        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        request.setSSLSocketFactory(socketFactory);
        Response<byte[]> execute = SyncRequestExecutor.INSTANCE.execute(request);
        return execute.get();
    }


}
