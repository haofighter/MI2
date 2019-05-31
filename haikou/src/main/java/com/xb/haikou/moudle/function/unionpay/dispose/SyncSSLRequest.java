package com.xb.haikou.moudle.function.unionpay.dispose;

import android.os.SystemClock;
import com.hao.lib.Util.ThreadUtils;
import com.szxb.java8583.core.Iso8583Message;
import com.szxb.java8583.core.Iso8583MessageFactory;
import com.szxb.java8583.module.SignIn;
import com.szxb.java8583.module.manager.BusllPosManage;
import com.szxb.java8583.quickstart.SingletonFactory;
import com.szxb.mlog.SLog;
import com.xb.haikou.db.dao.UnionPayEntityDao;
import com.xb.haikou.db.manage.DBCore;
import com.xb.haikou.moudle.function.unionpay.UnionPay;
import com.xb.haikou.moudle.function.unionpay.UnionUtil;
import com.xb.haikou.moudle.function.unionpay.config.UnionConfig;
import com.xb.haikou.moudle.function.unionpay.entity.UnionPayEntity;
import com.xb.haikou.moudle.function.unionpay.http.BaseByteRequest;
import com.xb.haikou.record.RecordUpload;
import com.xb.haikou.util.BusToast;
import com.xb.haikou.voice.SoundPoolUtil;
import com.xb.haikou.voice.VoiceConfig;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static com.xb.haikou.moudle.function.unionpay.unionutil.HexUtil.yuan2Fen;
import static com.xb.haikou.voice.VoiceConfig.SCAN_SUCCESS;


/**
 * 作者：Tangren on 2018-09-08
 * 包名：com.szxb.unionpay.dispose
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class SyncSSLRequest {

    /**
     * @param type     类型
     * @param sendData 源数据
     * @return 银联响应
     */
    synchronized public BankResponse request(int type, byte[] sendData) {
        BankResponse response = new BankResponse();
        response.setType(type);
        String url = BusllPosManage.getPosManager().getUnionPayUrl();
        BaseByteRequest baseSyncRequest = new BaseByteRequest(url, RequestMethod.POST);
        InputStream stream = new ByteArrayInputStream(sendData);
        baseSyncRequest.setDefineRequestBody(stream, "x-ISO-TPDU/x-auth");
        Response<byte[]> execute = SyncRequestExecutor.INSTANCE.execute(baseSyncRequest);
        if (execute.isSucceed()) {
            Iso8583MessageFactory factory = SingletonFactory.forQuickStart();
            Iso8583Message message0810 = factory.parse(execute.get());
            SLog.d("SyncSSLRequest(request.java:58)type=" + (type == UnionUtil.PAY_TYPE_BANK_IC ? "银联卡>>\n" : "银联二维码>>\n") + message0810.toFormatString());
            doDispose(type, response, message0810);
        } else {
            response.setResCode(BankCardParse.ERROR_NET);
            BusToast.showToast("网络超时", false);
            ThreadUtils.getInstance().createSch("bankRefund").schedule(new Runnable() {
                @Override
                public void run() {
                    new BankRefund().run();
                }
            }, 3, TimeUnit.SECONDS);
        }
        return response;
    }

    /**
     * 银联
     *
     * @param type        类型
     * @param icResponse  .
     * @param message0810 返回的报文
     */
    private void doDispose(int type, BankResponse icResponse, Iso8583Message message0810) {
        String pay_fee = message0810.getValue(4).getValue();
        String resCode = message0810.getValue(39).getValue();
        String tradeSeq = message0810.getValue(11).getValue();
        String batchNum = message0810.getValue(60).getValue().substring(2, 8);
        String uniqueFlag = tradeSeq + batchNum;
        UnionPayEntityDao dao = DBCore.getDaoSession().getUnionPayEntityDao();
        UnionPayEntity unique;
        if (type == UnionUtil.PAY_TYPE_BANK_IC) {
            unique = dao.queryBuilder()
                    .where(UnionPayEntityDao.Properties.Reserve_2.isNull())
                    .where(UnionPayEntityDao.Properties.UniqueFlag.eq(uniqueFlag))
                    .limit(1).build().unique();
        } else {
            unique = dao.queryBuilder()
                    .where(UnionPayEntityDao.Properties.Reserve_2.isNotNull())
                    .where(UnionPayEntityDao.Properties.UniqueFlag.eq(uniqueFlag))
                    .limit(1).build().unique();
        }

        if (unique != null) {
            unique.setResCode(resCode);
            switch (resCode) {
                case "00":
                case "A2":
                case "A4":
                case "A5":
                case "A6":
                    //支付成功
                    String amount = message0810.getValue(4).getValue();
                    icResponse.setResCode(BankCardParse.SUCCESS);
                    if (type == UnionUtil.PAY_TYPE_BANK_IC) {
                        //银联卡返回2域
                        icResponse.setMainCardNo(message0810.getValue(2).getValue());
                    }
                    icResponse.setMsg("扣款成功\n扣款金额" + yuan2Fen(amount) + "元");
                    icResponse.setLastTime(SystemClock.elapsedRealtime());
                    unique.setPayFee(pay_fee);

                    SoundPoolUtil.play(SCAN_SUCCESS);
                    RecordUpload.upUnionRecord();
                    SLog.d("UnionPay(success.java:104)修改成功");
                    break;
                case "A0":
                    //重新签到
                    icResponse.setResCode(BankCardParse.ERROR_RE_SIGN);
                    BusToast.showToast(
                            (type == UnionUtil.PAY_TYPE_BANK_IC ? "刷卡" : "扫码") + "失败\n正在重新签到", false);
                    BusllPosManage.getPosManager().setTradeSeq();
                    Iso8583Message message = SignIn.getInstance().message(BusllPosManage.getPosManager().getTradeSeq());
                    UnionPay.getInstance().exeSSL(UnionConfig.SIGN, message.getBytes(), true);
                    break;
                case "94"://重复交易（流水号重复）
                    icResponse.setResCode(-94);
                    icResponse.setMsg("流水号重复\n请重刷");
                    break;
                case "51"://余额不足
                    icResponse.setResCode(-51);
                    icResponse.setMsg("余额不足");
                    SoundPoolUtil.play(VoiceConfig.EC_BALANCE);
                    break;
                case "54"://卡过期
                    icResponse.setResCode(-54);
                    icResponse.setMsg("卡过期");
                    SoundPoolUtil.play(VoiceConfig.IC_INVALID);
                    break;
                default:
                    icResponse.setResCode(BankCardParse.ERROR_ELSE);
                    icResponse.setMsg((type == UnionUtil.PAY_TYPE_BANK_IC ? "刷卡" : "扫码") + "失败[" + UnionUtil.unionPayStatus(resCode) + "]");
                    break;
            }

            //记录异步修改
            //TODO 修改数据库保存数据
        }
    }


}
