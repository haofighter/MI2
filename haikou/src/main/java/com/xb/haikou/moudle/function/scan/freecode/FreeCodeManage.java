package com.xb.haikou.moudle.function.scan.freecode;

import com.bluering.sdk.qrcode.jtb.VerifyCodeResult;
import com.google.gson.Gson;
import com.hao.lib.Util.FileUtils;
import com.xb.haikou.base.AppRunParam;
import com.xb.haikou.config.AppRunConfigEntity;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.record.JTBscanRecord;
import com.xb.haikou.record.RecordUpload;
import com.xb.haikou.util.AppUtil;
import com.xb.haikou.voice.SoundPoolUtil;
import com.xb.haikou.voice.VoiceConfig;

public class FreeCodeManage {
    private FreeCodeManage() {
    }

    public void posScan(String result) {
        FreeScanEntity freeScanEntity = new Gson().fromJson(result, FreeScanEntity.class);
        AppRunConfigEntity appRunConfigEntity = DBManager.checkRunConfig();
        if (freeScanEntity.getBlueRing().getType().equals("Driver")) {//司机
            appRunConfigEntity.setDriverNo(freeScanEntity.getBlueRing().getValue());
        } else if (freeScanEntity.getBlueRing().getType().equals("Price")) {//票价
            AppRunParam.getInstance().setBasePrice(Integer.parseInt(freeScanEntity.getBlueRing().getValue()));
        } else if (freeScanEntity.getBlueRing().getType().equals("Conductor")) {//售票员
            appRunConfigEntity.setConductor(freeScanEntity.getBlueRing().getValue());
        } else if (freeScanEntity.getBlueRing().getType().equals("Carno")) {//车辆号
            appRunConfigEntity.setBusNo(freeScanEntity.getBlueRing().getValue());
        }
        DBManager.updateConfig(appRunConfigEntity);
    }

    private static class FreeCodeManageHelp {
        private static FreeCodeManage fcm = new FreeCodeManage();
    }

    public static FreeCodeManage getInstance() {
        return FreeCodeManageHelp.fcm;
    }

    public void praseJTBScan(String qrCode, VerifyCodeResult code) {
        JTBscanRecord jtBscanRecord = new JTBscanRecord();
        jtBscanRecord.setBizType("03");
        jtBscanRecord.setAcquirer(DBManager.checkLineInfo().getAcquirer().replaceFirst("0", ""));
        jtBscanRecord.setTerseno(AppUtil.getTranNum() + "");
        jtBscanRecord.setTerminalId(AppRunParam.getInstance().getPosSn());
        jtBscanRecord.setDriverId(AppRunParam.getInstance().getDriverNo().substring(1, 20));
        jtBscanRecord.setConductorId(AppRunParam.getInstance().getConductorid());
        jtBscanRecord.setTransTime(System.currentTimeMillis() / 1000 + "");
        jtBscanRecord.setTransCityCode(AppRunParam.getInstance().getCityCode());
        jtBscanRecord.setLineId(FileUtils.deleteCover(AppRunParam.getInstance().getLineId()));
        jtBscanRecord.setLineName(AppRunParam.getInstance().getLineName());
        jtBscanRecord.setStation("1");
        jtBscanRecord.setStationName(AppRunParam.getInstance().getLineName());
        jtBscanRecord.setCurrency("156");
        jtBscanRecord.setChargeType("0");
        jtBscanRecord.setTotalFee(AppRunParam.getInstance().getBasePrice() + "");
        jtBscanRecord.setPayFee(AppRunParam.getInstance().getOtherPayfee("ZM") + "");
        jtBscanRecord.setQrCode(qrCode);
//        jtBscanRecord.setQrCode(FileUtils.bytesToHexString(code.getQRCodeData()));
        jtBscanRecord.setQrCodeData(FileUtils.bytesToHexString(code.getQRCodeData()));
        jtBscanRecord.setBusno(AppRunParam.getInstance().getBusNo());
        jtBscanRecord.setIsUpload("0");
        DBManager.insertJTBScanRecord(jtBscanRecord);
        RecordUpload.upJTBRecord();
        SoundPoolUtil.play(VoiceConfig.SCAN_SUCCESS);
    }
}
