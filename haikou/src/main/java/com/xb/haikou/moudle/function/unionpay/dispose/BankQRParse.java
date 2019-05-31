package com.xb.haikou.moudle.function.unionpay.dispose;


import com.hao.lib.Util.FileUtils;
import com.hao.lib.base.Rx.Rx;
import com.szxb.java8583.core.Iso8583Message;
import com.szxb.java8583.module.BankScanPay;
import com.szxb.java8583.module.manager.BusllPosManage;
import com.xb.haikou.base.AppRunParam;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.moudle.function.unionpay.UnionUtil;
import com.xb.haikou.moudle.function.unionpay.entity.UnionPayEntity;

import static com.xb.haikou.db.manage.DBCore.getDaoSession;
import static com.xb.haikou.util.DateUtil.getCurrentDate;


/**
 * 作者：Tangren on 2018-09-11
 * 包名：com.szxb.unionpay.dispose
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class BankQRParse {
    synchronized public BankResponse parseResponse(int amount, String qrCode) {

        Rx.getInstance().sendMessage("", "银联");
        BusllPosManage.getPosManager().setTradeSeq();
        //同步保存记录
        saveQRUnionPayEntity(amount, qrCode);
        SyncSSLRequest syncSSLRequest = new SyncSSLRequest();
        Iso8583Message iso8583Message = BankScanPay.getInstance()
                .qrPayMessage(qrCode, amount, BusllPosManage.getPosManager().getTradeSeq(), BusllPosManage.getPosManager().getMacKey());
        BankResponse response = syncSSLRequest.request(UnionUtil.PAY_TYPE_BANK_QR, iso8583Message.getBytes());

        return response;
    }

    private void saveQRUnionPayEntity(int amount, String qrCode) {
        UnionPayEntity payEntity = new UnionPayEntity();
        payEntity.setCreattime(System.currentTimeMillis());
        payEntity.setMchId(BusllPosManage.getPosManager().getMchId());
        payEntity.setUnionPosSn(BusllPosManage.getPosManager().getPosSn());
        payEntity.setPosSn(AppRunParam.getInstance().getDriverNo());
        payEntity.setBusNo(AppRunParam.getInstance().getBusNo());
        payEntity.setTotalFee(String.valueOf(amount));
        //注:支付金额记录存储需根据交易返回为准,未防止交易失败导致金额错误
        payEntity.setPayFee("0");
        payEntity.setTime(getCurrentDate());
        payEntity.setTradeSeq(String.format("%06d", BusllPosManage.getPosManager().getTradeSeq()));
        payEntity.setMainCardNo(qrCode);
        payEntity.setReserve_1(qrCode);
        payEntity.setBatchNum(BusllPosManage.getPosManager().getBatchNum());
        payEntity.setBus_line_name(AppRunParam.getInstance().getLineName());
        payEntity.setBus_line_no(FileUtils.deleteCover(AppRunParam.getInstance().getLineId()));
        payEntity.setDriverNum(AppRunParam.getInstance().getDriverNo());
        payEntity.setUnitno(AppRunParam.getInstance().getUnitno());
        payEntity.setUniqueFlag(String.format("%06d", BusllPosManage.getPosManager().getTradeSeq()) + BusllPosManage.getPosManager().getBatchNum());
        payEntity.setReserve_2("QR");
        payEntity.setTranType("2");
        payEntity.setBiztype("06");
        payEntity.setAcquirer(DBManager.checkLineInfo().getAcquirer().replaceFirst("0", ""));
        payEntity.setConductorid(AppRunParam.getInstance().getConductorid());
        payEntity.setCurrency("156");
        payEntity.setTransdata(qrCode);
        payEntity.setUpStatus(0);
        //记录也同步保存
        getDaoSession().getUnionPayEntityDao().insertOrReplaceInTx(payEntity);
    }
}
