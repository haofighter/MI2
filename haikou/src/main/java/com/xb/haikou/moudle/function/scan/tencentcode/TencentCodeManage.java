package com.xb.haikou.moudle.function.scan.tencentcode;

import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hao.lib.Util.DataUtils;
import com.hao.lib.Util.FileUtils;
import com.tencent.wlxsdk.WlxSdk;
import com.xb.haikou.base.AppRunParam;
import com.xb.haikou.config.InitConfig;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.moudle.function.scan.QRCode;
import com.xb.haikou.record.RecordUpload;
import com.xb.haikou.record.ScanRecordEntity;
import com.xb.haikou.runTool.RunSettiing;
import com.xb.haikou.util.AppUtil;
import com.xb.haikou.util.BusToast;
import com.xb.haikou.voice.SoundPoolUtil;
import com.xb.haikou.voice.VoiceConfig;

import java.util.Calendar;

import static com.xb.haikou.moudle.function.scan.QRCode.QR_ERROR;
import static com.xb.haikou.voice.VoiceConfig.SCAN_SUCCESS;

public class TencentCodeManage {
    private WlxSdk wxSdk;

    private TencentCodeManage() {
        wxSdk = new WlxSdk();
    }

    private static class TencenCodeManageHelp {
        public static final TencentCodeManage tcm = new TencentCodeManage();
    }

    public static TencentCodeManage getInstance() {
        return TencenCodeManageHelp.tcm;
    }

    public void posScan(String qrcode) {
        if (wxSdk == null) wxSdk = new WlxSdk();
        int init = wxSdk.init(qrcode);
        int key_id = wxSdk.get_key_id();
        String open_id = wxSdk.get_open_id();
        String mac_root_id = wxSdk.get_mac_root_id();

        Log.i("腾讯二维码", qrcode + "      init=" + init + "      key_id=" + key_id + "         open_id=" + open_id + "       mac_root_id=" + mac_root_id);
        if (init == 0 && key_id > 0) {
            int verify = wxSdk.verify(open_id
                    , DBManager.getPublicKey(String.valueOf(key_id))
                    , AppRunParam.getInstance().getOtherPayfee("TX")//金额
                    , (byte) 1
                    , (byte) 1
                    , AppRunParam.getInstance().getPosSn()
                    , AppUtil.Random(8)
                    , DBManager.getMac(mac_root_id));
            String record = wxSdk.get_record();
            Log.i("腾讯二维码", "verify=" + verify + "\n公钥" + DBManager.getPublicKey(String.valueOf(key_id))
                    + "\n私钥" + DBManager.getMac(mac_root_id) + "      record=" + record);

            ScanRecordEntity scanRecord = new ScanRecordEntity();
            scanRecord.setQrCode(qrcode);
            scanRecord.setRecord(record);
            scanRecord.setQrcodeType("0");
            scanRecord.setMac_root_id(mac_root_id);
            scanRecord.setKey_id(key_id);
            scanRecord.setOpen_id(open_id);
            scanRecord.setUpState("0");
            scanRecord.setCreatTime(System.currentTimeMillis());
            saveTXScanRecord(scanRecord);

            request(verify, scanRecord);
        } else {
            BusToast.showToast("二维码配置出错", false);
        }
    }

    public void request(int tag, ScanRecordEntity scanRecord) {
        switch (tag) {
            case QRCode.EC_SUCCESS:
                SoundPoolUtil.play(SCAN_SUCCESS);
                BusToast.showToast("扫码成功", true);
                DBManager.insertScanRecord(scanRecord);
                RecordUpload.upLoadTXScanRecord();
                break;
            case QRCode.EC_MAC_OR_PUBLIC_ER:
                SoundPoolUtil.play(QR_ERROR);
                new InitConfig().getWXPublicKey().getWxMacKey();
                break;
            case QR_ERROR://非腾讯或者小兵二维码
            case QRCode.EC_CARD_CERT_SIGN_ALG_NOT_SUPPORT://卡证书签名算法不支持
            case QRCode.EC_MAC_ROOT_KEY_DECRYPT_ERR://加密的mac根密钥解密失败
            case QRCode.EC_QRCODE_SIGN_ALG_NOT_SUPPORT://二维码签名算法不支持
            case QRCode.EC_OPEN_ID://输入的openid不符
            case QRCode.EC_CARD_CERT://卡证书签名错误
            case QRCode.EC_FAIL://系统异常
                SoundPoolUtil.play(QR_ERROR);
                BusToast.showToast("二维码有误[" + tag + "]", false);
                break;
            case QRCode.EC_FEE://超出最大金额
                SoundPoolUtil.play(VoiceConfig.EC_FEE);
                BusToast.showToast("超出最大金额[" + QRCode.EC_FEE + "]", false);
                break;
            case QRCode.EC_BALANCE://余额不足
                SoundPoolUtil.play(VoiceConfig.EC_BALANCE);
                BusToast.showToast("余额不足[" + QRCode.EC_BALANCE + "]", false);
                break;
            case QRCode.EC_CODE_TIME://二维码过期
                String noticeStr;
                //检查当前日期是否正常(>=2018)
                if (Calendar.getInstance().get(Calendar.YEAR) < 2018) {
                    noticeStr = "正在校准时间[请重试]";
                    RunSettiing.getInstance().retTime(true);
                } else {
                    noticeStr = "二维码过期[10006]";
                    SoundPoolUtil.play(VoiceConfig.EC_RE_QR_CODE);
                }
                BusToast.showToast(noticeStr, false);
                break;
            case QRCode.REFRESH_QR_CODE://请刷新二维码
            case QRCode.EC_MAC_SIGN_ERR://mac校验失败
            case QRCode.EC_USER_SIGN://二维码签名错误
            case QRCode.EC_FORMAT://二维码格式错误
            case QRCode.EC_USER_PUBLIC_KEY://卡证书用户公钥错误
            case QRCode.EC_CARD_PUBLIC_KEY://卡证书公钥错误
            case QRCode.EC_PARAM_ERR://参数错误
            case QRCode.EC_CARD_CERT_TIME://卡证书过期，提示用户联网刷新二维码
                SoundPoolUtil.play(VoiceConfig.EC_RE_QR_CODE);
                BusToast.showToast("请刷新二维码[" + tag + "]", false);
                break;
            default:

                break;
        }
    }

    private void saveTXScanRecord(ScanRecordEntity scanRecord) {
        scanRecord.setMch_trx_id(AppUtil.Random(10) + DataUtils.getStringDateM());
        scanRecord.setCity_code(AppRunParam.getInstance().getCityCode());
        scanRecord.setCreatTime(System.currentTimeMillis());
        scanRecord.setAcquirer(DBManager.checkLineInfo().getAcquirer().replaceFirst("0", ""));
        scanRecord.setBiztype("08");

        scanRecord.setOrder_time(System.currentTimeMillis() / 1000);
        scanRecord.setTotal_fee(AppRunParam.getInstance().getBasePrice() + "");
        scanRecord.setPay_fee(AppRunParam.getInstance().getOtherPayfee("TX") + "");
        scanRecord.setExp_type("0");
        scanRecord.setCharge_type("0");
        JSONObject ext = new JSONObject();
        ext.put("in_station_id", "1");
        ext.put("in_station_name", AppRunParam.getInstance().getLineName());
        scanRecord.setExt(new Gson().toJson(ext));
        scanRecord.setBus_no(AppRunParam.getInstance().getBusNo());
        scanRecord.setPos_no(AppRunParam.getInstance().getPosSn());
        scanRecord.setBus_line_name(FileUtils.deleteCover(AppRunParam.getInstance().getLineId()));
        scanRecord.setDriveno(AppRunParam.getInstance().getDriverNo().substring(1, 20));
        scanRecord.setUnitno(AppRunParam.getInstance().getUnitno());
        scanRecord.setCurrency("156");
        scanRecord.setCardId("");
        scanRecord.setBiztype("08");
        DBManager.insertScanRecord(scanRecord);
    }
}
