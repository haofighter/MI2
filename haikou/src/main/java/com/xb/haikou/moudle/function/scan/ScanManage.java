package com.xb.haikou.moudle.function.scan;

import android.util.Log;
import com.bluering.sdk.qrcode.jtb.JTBQRCodeSDK;
import com.bluering.sdk.qrcode.jtb.VerifyCodeResult;
import com.hao.lib.Util.FileUtils;
import com.xb.haikou.base.AppRunParam;
import com.xb.haikou.moudle.function.scan.alicode.AliCodeManage;
import com.xb.haikou.moudle.function.scan.freecode.FreeCodeManage;
import com.xb.haikou.moudle.function.scan.freecode.VerifyUtil;
import com.xb.haikou.moudle.function.scan.tencentcode.TencentCodeManage;
import com.xb.haikou.moudle.function.unionpay.dispose.BankQRParse;
import com.xb.haikou.moudle.function.unionpay.dispose.BankResponse;
import com.xb.haikou.util.BusToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScanManage {
    private ScanManage() {
    }

    public static ScanManage getInstance() {
        return ScanManageHelp.scanManage;
    }

    static class ScanManageHelp {
        public static final ScanManage scanManage = new ScanManage();
    }


    String oldScan = "";

    public void scanRe(byte[] qrCode) {
        String result = new String(qrCode);
        if (oldScan.equals(result)) {
            return;
        }
        Log.i("二维码", "result=" + result);
        Log.i("二维码", "result=" + FileUtils.bytesToHexString(qrCode));
        if (isMyQRcode(result)) {//小兵二维码

        } else if (isTenQRcode(result)) {//腾讯二维码
            if (AppRunParam.getInstance().getDriverNo().equals("")) {
                BusToast.showToast("请刷司机卡", false);
                return;
            } else if (AppRunParam.getInstance().getLineId().equals("")) {
                BusToast.showToast("请设置线路", false);
                return;
            }
            TencentCodeManage.getInstance().posScan(result);
        } else if (result.contains("BlueRing")) {
            FreeCodeManage.getInstance().posScan(result);
        } else if (isAllNum(result)) {
            if (AppRunParam.getInstance().getDriverNo().equals("")) {
                BusToast.showToast("请刷司机卡", false);
                return;
            } else if (AppRunParam.getInstance().getLineId().equals("")) {
                BusToast.showToast("请设置线路", false);
                return;
            }
            BankQRParse qrParse = new BankQRParse();
            BankResponse response = qrParse.parseResponse(AppRunParam.getInstance().getOtherPayfee("AL"), result);
            if (response.getResCode() > 0) {
                BusToast.showToast(response.getMsg(), true);
            } else {
                BusToast.showToast(response.getMsg() + "[" + response.getResCode() + "]", false);
            }
        } else {//交通部二维码
            if (AppRunParam.getInstance().getDriverNo().equals("")) {
                BusToast.showToast("请刷司机卡", false);
                return;
            } else if (AppRunParam.getInstance().getLineId().equals("")) {
                BusToast.showToast("请设置线路", false);
                return;
            }
            VerifyCodeResult code = JTBQRCodeSDK.verifyCode(qrCode, 0, AppRunParam.getInstance().getPosSn().getBytes(), (int) (System.currentTimeMillis() / 1000));
            Log.i("验证二维码", "code=" + code);
            Log.i("交通部二维码", "QRCodeData=" + FileUtils.bytesToHexString(code.getQRCodeData()));
            Log.i("交通部二维码", "BizData=" + FileUtils.bytesToHexString(code.getBizData()));

            if (code.getResult() == 0) {
                FreeCodeManage.getInstance().praseJTBScan(FileUtils.bytesToHexString(qrCode), code);
            } else {
                AliCodeManage.getInstance().posScan(qrCode);//阿里二维码验证
            }
        }
        oldScan = result;
    }


    private boolean isMyQRcode(String qrcode) {
        return qrcode != null && qrcode.indexOf("szxb") == 0;
    }


    public static boolean isTenQRcode(String qrcode) {
        return qrcode != null && qrcode.indexOf("TX") == 0;
    }


    /**
     * @param var .
     * @return 是否是全数字
     */
    public static boolean isAllNum(String var) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(var);
        return matcher.matches();
    }


    private boolean VerifyCode(int verifyCode) {
        switch (verifyCode) {
            case VerifyUtil.PUBLIC_KEY_FAIL:
                BusToast.showToast("公钥获取失败", false);
                return false;
            case VerifyUtil.ISSUE_PUBLIC_CERT_INVALID:
                BusToast.showToast("发卡机构公钥证书无效", false);
                return false;
            case VerifyUtil.ISSUE_SIGN_ERROR:
                BusToast.showToast("发卡机构授权数据签名错误", false);
                return false;
            case VerifyUtil.USER_SIGN_ERROR:
                BusToast.showToast("用户签名错误", false);
                return false;
            case VerifyUtil.QR_TIME_OUT:
                BusToast.showToast("二维码过期", false);
                return false;
            case VerifyUtil.EXCEPTION:
                BusToast.showToast("验码失败", false);
                return false;
            case VerifyUtil.SUCCESS:
                return true;
            default:
                return false;
        }
    }
}
