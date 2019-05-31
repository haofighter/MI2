package com.xb.haikou.moudle.function.scan.alicode;

import android.util.Log;
import com.hao.lib.Util.DataUtils;
import com.hao.lib.Util.FileUtils;
import com.szxb.jni.Alipay;
import com.xb.haikou.base.AppRunParam;
import com.xb.haikou.config.line.PayRuleInfo;
import com.xb.haikou.config.param.AliPublicKeyEntity;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.moudle.function.unionpay.unionutil.HexUtil;
import com.xb.haikou.record.RecordUpload;
import com.xb.haikou.record.ScanRecordEntity;
import com.xb.haikou.util.AppUtil;
import com.xb.haikou.util.BusToast;
import com.xb.haikou.voice.SoundPoolUtil;
import com.xb.haikou.voice.VoiceConfig;

import java.util.List;

import static com.szxb.jni.Alipay.init_pos_verify;
import static com.szxb.jni.Alipay.verify_qrcode_v2;


public class AliCodeManage {
    private AliCodeManage() {
    }

    private static class AliCodeManageHelp {
        private static AliCodeManage fcm = new AliCodeManage();
    }

    public static AliCodeManage getInstance() {
        return AliCodeManage.AliCodeManageHelp.fcm;
    }


    public void posScan(byte[] scan) {
        String cardList = "[\"T0460100\"]";
        List<AliPublicKeyEntity> aliPublicKeyEntityList = DBManager.checkAliPublicKey();
        /**************************************************/
        String aliKey = "[";
        for (int i = 0; i < aliPublicKeyEntityList.size(); i++) {
            aliKey += "{\"key_id\":" + aliPublicKeyEntityList.get(i).getKey_id() + ",\"public_key\":\"" + aliPublicKeyEntityList.get(i).getPublic_key() + "\"}";
            if (i != aliPublicKeyEntityList.size() - 1) {
                aliKey += ",";
            }
        }
        aliKey += "]";
        String keyList = "[{\"key_id\":0,\"public_key\":\"02AB2FDB0AB23506C48012642E1A572FC46B5D8B8EE3B92A602CC1109921F84B0E\"}, " +
                "{\"key_id\":1,\"public_key\":\"03F04C74CAD7EC0F2AE93AC51EABCE95D3D3E6F32F71816D7D838308578425F873\"}, " +
                "{\"key_id\":2,\"public_key\":\"0307F46E2C7F40B7B89F11A85C11B0C9374C886132073FAE0BD54A940FAEB21907\"}, " +
                "{\"key_id\":3,\"public_key\":\"034532FF3BEBE61DAC18B7F0D7A201A5080D49F2EA9B256669728A4E8AC9D0DC76\"}, " +
                "{\"key_id\":4,\"public_key\":\"0362650BA9947153AB38D6C1F63D3AFFDF1CBD199A7E3EFDD4F61BC6F33F0F8F76\"}, " +
                "{\"key_id\":5,\"public_key\":\"0268A244029140F693BCA395E5BFA79D6B7B7F1E3D232AFDE2955BAE9A2DBB8E2B\"}, " +
                "{\"key_id\":6,\"public_key\":\"028B90D7C281AD21B4BA9492C522E39B655890CC41420EB8E8826B28C6DF2D467A\"}, " +
                "{\"key_id\":7,\"public_key\":\"03B31505F0622E381E3C53211CD44EB4A3FB35DA7094E5F09FA89D91DCC7165929\"}, " +
                "{\"key_id\":8,\"public_key\":\"021ADB0F0E05A7B9BE0C11153E4D0BC3859F5F73EAE9BEADF04160DE9E4BBF9C74\"}, " +
                "{\"key_id\":9,\"public_key\":\"0255668F6A4CCE5272A7FC77A29902D06E14D939CB50DDC071ACB86B54FB3FCD91\"}, " +
                "{\"key_id\":10,\"public_key\":\"02E740AB065F7976C836B32C324FEC665DF034EE2CBEB3C311C14EA59E4494F5AC\"}, " +
                "{\"key_id\":11,\"public_key\":\"02EF5B9310CC93ADC130D71F605474FDBEF284A1AE5AF7493A3A982AE1031BAAC2\"}, " +
                "{\"key_id\":12,\"public_key\":\"031E0F70A8A5460365F91A7D66620BD9424EAAEBD9CFF54E84CB4257C5B32324D9\"}, " +
                "{\"key_id\":13,\"public_key\":\"02D1BEB9BAC72681B9CECBE7EF369663A04D4487EE20CC10673E12920A01FD4189\"}, " +
                "{\"key_id\":14,\"public_key\":\"02393BF79609919DDDE78FBCD0579C4995B04FA84E74D9802D78BC025B777114B9\"}, " +
                "{\"key_id\":15,\"public_key\":\"03C2A0C0D0163CA194FA6F9DCF71C02154D1ECD9CEB77C297F32CF47AA2C9DF22A\"}, " +
                "{\"key_id\":16,\"public_key\":\"02F27F0CFF4D56D513F427D4274B862C4998B3F03FBC59275E7E0E37EA5E492311\"}, " +
                "{\"key_id\":17,\"public_key\":\"03ECADBF719F9C6539534F376A210486888A89B952E8A421A10D25E4601C667C74\"}, " +
                "{\"key_id\":18,\"public_key\":\"0296BD24657AB0E5D4B7A6A8A0D41E5E722CA3AD791B74C8EC2BBFDBD9D9A6F571\"}, " +
                "{\"key_id\":19,\"public_key\":\"0394657638D9FD20B463AF15F761E0F42E94346D35D8F494445C89AAE7A1FA6838\"}, " +
                "{\"key_id\":20,\"public_key\":\"0366050BC5FFA296753607EEC07B811C2A59FB93F2ED41D30D081797267B8CB191\"}, " +
                "{\"key_id\":21,\"public_key\":\"02878B02CF937EED619B85BAADAC24E408BF84E3C428F7AF4CA8D28628DDE2E36C\"}, " +
                "{\"key_id\":22,\"public_key\":\"02A2E791A0AC515A11AA2AEF7207A91AF9A74A1C22297F107DCDFB8CAB75E65CCA\"}, " +
                "{\"key_id\":23,\"public_key\":\"0375D631050A21B80C3B2443D93804067309D5AD7D22CE1D6771D540BDCD499521\"}, " +
                "{\"key_id\":24,\"public_key\":\"02CCA330425FEEF7B4070BC6F605D5703BE2444BAEE16415484CC0D905A2203B12\"}, " +
                "{\"key_id\":25,\"public_key\":\"02F48FB87407A799198350F83586595C5F10F1978DDD944360D16A8086FBC03368\"}, " +
                "{\"key_id\":26,\"public_key\":\"021964E5C29653C261136F43437C31D2C6A2AC1849CB79829731868E9D2947CE85\"}, " +
                "{\"key_id\":27,\"public_key\":\"0318B0F354FB34B0F024BDB689F647DB5BAADA2D91BCAA66DC514059908DAE3C6B\"}, " +
                "{\"key_id\":28,\"public_key\":\"023A391EE698ACB20AD40D42E3D2A546DC0D998D5BEFB626DEC874CDABADD9963F\"}, " +
                "{\"key_id\":29,\"public_key\":\"025D296816661C34CA0C1E528339B01434A98C6CB149BD99C6AA6F37B65E6086B0\"}, " +
                "{\"key_id\":30,\"public_key\":\"0200238F19D827135A2C194D2B52AD681FF1156196EE654C38BB49A9CB8EBA9F7C\"}, " +
                "{\"key_id\":31,\"public_key\":\"02EA31FB09DD181E6645D5E1827CDE07FC7D23BCD9DFB1C3DECDF6534F0B4713B9\"}]";

        /************************************************/
        Log.i("alikey", "秘钥" + "\n" + keyList + "\n" + aliKey);

        int nRet = init_pos_verify(aliKey, cardList);
        Log.i("支付宝二维码", "nRet=" + nRet);
        String par = "{\"pos_id\":\"sh001\",\"type\":\"SINGLE\",\"subject\":\"bus192\",\"record_id\":\"sh001_20160514140218_000001\"}";
        Alipay tmp = new Alipay();
        Alipay.VERIFY_REQUEST_V2 req = tmp.new VERIFY_REQUEST_V2(scan, scan.length, par, 10);
        Alipay.VERIFY_RESPONSE_V2 res = verify_qrcode_v2(req);

        Log.i("支付宝二维码", "res.getnRet()=" + res.getnRet() + "res=" + FileUtils.bytesToHexString(res.getUid()));

        if (res.getnRet() == 1) {
            SoundPoolUtil.play(VoiceConfig.SCAN_SUCCESS);
            BusToast.showToast("支付宝扫码成功", true);
            ScanRecordEntity scanRecordEntity = new ScanRecordEntity();
            scanRecordEntity.setCardType(new String(res.getCard_type()));//TODO 以码信息card_type为准
            PayRuleInfo payRuleInfo = DBManager.checkPayRuleInfo("AL");
            if (payRuleInfo != null) {
                scanRecordEntity.setDiscountType(payRuleInfo.getDiscountType());//TODO 优惠类型
                scanRecordEntity.setDiscountDesc("");//TODO 优惠描述
            } else {
                scanRecordEntity.setDiscountType("");//TODO 优惠类型
                scanRecordEntity.setDiscountDesc("");//TODO 优惠描述
            }

            Log.i("支付宝UID", "" + HexUtil.bytesToHexString(res.getUid()) + "        " + new String(res.getUid()) + "               " + FileUtils.byteToString(res.getUid()));
            scanRecordEntity.setUserId(new String(res.getUid()));
            scanRecordEntity.setCardId(new String(res.getCard_no()));
            scanRecordEntity.setRecord(new String(res.getRecord()));
            scanRecordEntity.setQrCode(FileUtils.bytesToHexString(scan));
            saveALScanRecord(scanRecordEntity);
            RecordUpload.upLoadALScanRecord();
        } else {
            SoundPoolUtil.play(VoiceConfig.IC_RE);
            BusToast.showToast("支付宝扫码失败", true);
        }
    }


    /**
     * private String terseno;//终端的流水号
     * private String terminalId;//机具设备终端编号
     * private String driverId;//司机卡Id
     * private String transTime;//交易时间
     * private String transCityCode;//交易发生地城市代码，遵循银联
     * private String lineId;//线路ID
     * private String lineName;//线路名
     * private String station;//站点
     * private String stationName;//站点名
     * private String chargeType;//计费类型:0-一次性扫码计费，1-分段计
     * private String totalFee;//总金额，单位分
     * private String payFee;//总金额，单位分
     * private String userId;//传入用户的支付宝user_id
     * private String appPosition;//事件发生lbs 经纬度使用英文逗号分隔.可不传
     * private String vehicleType;//交通工具类型(公交固定取值:BUS）
     * private String cardType;//以码信息card_type为准
     * private String discountType;//优惠类型
     * private String discountDesc;//优惠描述
     * private String busno;//车牌号
     *
     * @param scanRecord
     */
    private void saveALScanRecord(ScanRecordEntity scanRecord) {
        scanRecord.setTerseno(AppUtil.Random(10) + DataUtils.getStringDateM());
        scanRecord.setTerminalId(AppRunParam.getInstance().getPosSn());
        scanRecord.setDriverId(AppRunParam.getInstance().getDriverNo().substring(1, 20));
        scanRecord.setTransTime(System.currentTimeMillis() / 1000 + "");
        scanRecord.setTransCityCode(AppRunParam.getInstance().getCityCode());//TODO 交易发生地城市代码，遵循银联
        scanRecord.setLineId(FileUtils.deleteCover(AppRunParam.getInstance().getLineId()));
        scanRecord.setLineName(AppRunParam.getInstance().getLineName());
        scanRecord.setStation("1");
        scanRecord.setStationName(AppRunParam.getInstance().getLineName());
        scanRecord.setCharge_type("0");
        scanRecord.setTotal_fee(AppRunParam.getInstance().getBasePrice() + "");
        scanRecord.setPay_fee(AppRunParam.getInstance().getOtherPayfee("AL") + "");
        scanRecord.setVehicleType("BUS");
        scanRecord.setBusno(AppRunParam.getInstance().getBusNo());
        scanRecord.setUpState("0");
        scanRecord.setQrcodeType("1");
        scanRecord.setBiztype("09");
        scanRecord.setCurrency("156");
        scanRecord.setAcquirer(DBManager.checkLineInfo().getAcquirer().replaceFirst("0", ""));
        DBManager.insertScanRecord(scanRecord);
    }
}
