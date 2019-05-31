package com.xb.haikou.db.manage;


import android.util.Log;
import com.xb.haikou.config.AppRunConfigEntity;
import com.xb.haikou.config.line.LineInfo;
import com.xb.haikou.config.line.PayRuleInfo;
import com.xb.haikou.config.line.SingleTicktInfo;
import com.xb.haikou.config.line.StationListInfo;
import com.xb.haikou.config.param.AliPublicKeyEntity;
import com.xb.haikou.config.param.BuildConfigParam;
import com.xb.haikou.config.param.TencentMacKeyEntity;
import com.xb.haikou.config.param.TencentPublicKeyEntity;
import com.xb.haikou.db.dao.*;
import com.xb.haikou.moudle.function.unionpay.entity.UnionPayEntity;
import com.xb.haikou.record.*;
import com.xb.haikou.util.DateUtil;
import com.xb.haikou.voice.SoundPoolUtil;
import com.xb.haikou.voice.VoiceConfig;

import java.util.List;

import static com.xb.haikou.db.manage.DBCore.getDaoSession;

public class DBManager {

    //更新当前配置
    public static void updateConfig(AppRunConfigEntity config) {
        getDaoSession().getAppRunConfigEntityDao().insertOrReplace(config);
    }

    //获取当前运行配置
    public static AppRunConfigEntity checkConfig() {
        AppRunConfigEntity appRunConfig = getDaoSession().getAppRunConfigEntityDao().queryBuilder().orderDesc(AppRunConfigEntityDao.Properties.UpdateTime).limit(1).unique();
        if (appRunConfig == null) {
            appRunConfig = new AppRunConfigEntity();
        }
        return appRunConfig;
    }


    /***
     * 获取公钥

     * @param keyId 公钥ID
     * @return
     */
    public static String getPublicKey(String keyId) {
        TencentPublicKeyEntityDao dao = getDaoSession().getTencentPublicKeyEntityDao();
        TencentPublicKeyEntity unique = dao.queryBuilder().where(TencentPublicKeyEntityDao.Properties.Key_id.eq(keyId)).build().unique();
        if (unique != null)
            return unique.getPub_key();
        return "";
    }

    /**
     * 获取mac秘钥
     *
     * @param keyId .
     * @return .
     */
    public static String getMac(String keyId) {
        TencentMacKeyEntityDao dao = getDaoSession().getTencentMacKeyEntityDao();
        TencentMacKeyEntity unique = dao.queryBuilder().where(TencentMacKeyEntityDao.Properties.Key_id.eq(keyId)).unique();
        if (unique != null)
            return unique.getMac_key();
        return "";
    }

    /**
     * 插入单票票价
     *
     * @param singleTicktInfo
     */
    public static void insertSinglePrice(SingleTicktInfo singleTicktInfo) {
        SingleTicktInfoDao singleTicktInfoDao = getDaoSession().getSingleTicktInfoDao();
        singleTicktInfoDao.insertOrReplace(singleTicktInfo);
    }

    /**
     * 查询数据
     *
     * @return
     */
    public static SingleTicktInfo checkSinglePrice() {
        SingleTicktInfoDao singleTicktInfoDao = getDaoSession().getSingleTicktInfoDao();
        Log.i("tag", "单票票价数据：" + singleTicktInfoDao.queryBuilder().list().size() + "");
        return singleTicktInfoDao.queryBuilder().limit(1).unique();
    }

    /**
     * 修改票价数据
     *
     * @return
     */
    public static void updateSinglePrice(SingleTicktInfo singleTicktInfo) {
        SingleTicktInfoDao singleTicktInfoDao = getDaoSession().getSingleTicktInfoDao();
        singleTicktInfoDao.insertOrReplace(singleTicktInfo);
    }


    /**
     * 插入二维码数据
     *
     * @return
     */
    public static void insertScanRecord(ScanRecordEntity scanRecord) {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        scanRecordDao.insertOrReplace(scanRecord);
    }

    /**
     * 查询未同步的TX二维码数据
     *
     * @return
     */
    public static List<ScanRecordEntity> checkTXScanRecord(boolean isAll) {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        if (isAll) {
            return scanRecordDao.queryBuilder().where(ScanRecordEntityDao.Properties.QrcodeType.eq("0")).orderDesc(ScanRecordEntityDao.Properties.CreatTime).list();
        } else {
            return scanRecordDao.queryBuilder().where(ScanRecordEntityDao.Properties.UpState.notEq("1"), ScanRecordEntityDao.Properties.QrcodeType.eq("0")).limit(25).list();
        }
    }

    /**
     * 查询未同步的TX二维码数据
     *
     * @return
     */
    public static long checkTXScanRecordNum(boolean isAll) {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        if (isAll) {
            return scanRecordDao.queryBuilder().where(ScanRecordEntityDao.Properties.QrcodeType.eq("0")).orderDesc(ScanRecordEntityDao.Properties.CreatTime).count();
        } else {
            return scanRecordDao.queryBuilder().where(ScanRecordEntityDao.Properties.UpState.notEq("1"), ScanRecordEntityDao.Properties.QrcodeType.eq("0")).count();
        }
    }

    /**
     * 修改二维码记录的上传状态
     *
     * @return
     */
    public static void updateTXScanRecord(String mch_trx_id) {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        ScanRecordEntity scanRecord = scanRecordDao.queryBuilder()
                .where(ScanRecordEntityDao.Properties.UpState.notEq("1")
                        , ScanRecordEntityDao.Properties.Mch_trx_id.eq(mch_trx_id)).limit(1).unique();
        scanRecord.setUpState("1");
        scanRecordDao.insertOrReplace(scanRecord);
    }

    /**
     * 查询未同步的AL二维码数据
     *
     * @return
     */
    public static List<ScanRecordEntity> checkALScanRecord(boolean isAll) {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        if (isAll) {
            return scanRecordDao.queryBuilder().where(ScanRecordEntityDao.Properties.QrcodeType.eq("1")).orderDesc(ScanRecordEntityDao.Properties.CreatTime).list();
        } else {
            return scanRecordDao.queryBuilder().where(ScanRecordEntityDao.Properties.UpState.notEq("1"), ScanRecordEntityDao.Properties.QrcodeType.eq("1")).list();
        }
    }

    /**
     * 查询未同步的AL二维码数据
     *
     * @return
     */
    public static long checkALScanRecordNum(boolean isAll) {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        if (isAll) {
            return scanRecordDao.queryBuilder().where(ScanRecordEntityDao.Properties.QrcodeType.eq("1")).orderDesc(ScanRecordEntityDao.Properties.CreatTime).count();
        } else {
            return scanRecordDao.queryBuilder().where(ScanRecordEntityDao.Properties.UpState.notEq("1"), ScanRecordEntityDao.Properties.QrcodeType.eq("1")).count();
        }
    }


    /**
     * 查询未同步的ZYM二维码数据
     *
     * @return
     */
    public static List<ScanRecordEntity> checkZYNScanRecord() {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        return scanRecordDao.queryBuilder().where(ScanRecordEntityDao.Properties.UpState.notEq("1"), ScanRecordEntityDao.Properties.QrcodeType.eq("2")).limit(25).list();
    }


    /**
     * 查询未同步的other二维码数据
     *
     * @return
     */
    public static List<ScanRecordEntity> checkScanRecordEntity() {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        return scanRecordDao.queryBuilder().where(ScanRecordEntityDao.Properties.UpState.notEq("1"), ScanRecordEntityDao.Properties.QrcodeType.eq("2")).limit(25).list();
    }

    /**
     * APP运行的时候存入数据库中的配置数据
     *
     * @return
     */
    public static AppRunConfigEntity checkRunConfig() {
        AppRunConfigEntityDao appRunConfigDao = getDaoSession().getAppRunConfigEntityDao();
        AppRunConfigEntity appRunConfigEntity = appRunConfigDao.queryBuilder().orderDesc(AppRunConfigEntityDao.Properties.UpdateTime).limit(1).unique();
        if (appRunConfigEntity == null) {
            appRunConfigEntity = new AppRunConfigEntity();
            appRunConfigEntity.setLineNo("");
            appRunConfigEntity.setLineName("");
            appRunConfigEntity.setBusNo("");
            appRunConfigEntity.setPosSn("");
            appRunConfigEntity.setDriverNo("");
            appRunConfigEntity.setConductor("");
            appRunConfigEntity.setBinVer("");
            appRunConfigEntity.setPSAM("");
            appRunConfigEntity.setPSAMID("");
            appRunConfigEntity.setPSAMSY("");
            appRunConfigDao.insertOrReplace(appRunConfigEntity);
            appRunConfigEntity = checkConfig();
        }
        return appRunConfigEntity;
    }

    /**
     * APP构建的时候填入的参数
     *
     * @return
     */
    public static BuildConfigParam checkBuildConfig() {
        BuildConfigParamDao buildConfigParamDao = getDaoSession().getBuildConfigParamDao();
        return buildConfigParamDao.queryBuilder().orderDesc(BuildConfigParamDao.Properties.UpdateTime).limit(1).unique();
    }

    /**
     * 保存刷卡记录
     *
     * @return
     */
    public static void saveCardRecord(CardRecordEntity cardRecordEntity) {
        CardRecordEntityDao cardRecordEntityDao = getDaoSession().getCardRecordEntityDao();
        Long i = cardRecordEntityDao.insertOrReplace(cardRecordEntity);
        Log.i("刷卡记录保存 ", "ℹ=" + i);
    }


    //查询刷卡记录
    public static List<CardRecordEntity> checkCardRecord(boolean isAll) {
        CardRecordEntityDao cardRecordEntityDao = getDaoSession().getCardRecordEntityDao();
        if (isAll) {
            return cardRecordEntityDao.queryBuilder().orderDesc(CardRecordEntityDao.Properties.Time).list();
        } else {
            return cardRecordEntityDao.queryBuilder().where(CardRecordEntityDao.Properties.IsUploade.eq("0")).orderDesc(CardRecordEntityDao.Properties.Time).limit(25).list();
        }
    }

    //查询刷卡记录
    public static long checkCardRecordNum(boolean isAll) {
        CardRecordEntityDao cardRecordEntityDao = getDaoSession().getCardRecordEntityDao();
        if (isAll) {
            return cardRecordEntityDao.queryBuilder().orderDesc(CardRecordEntityDao.Properties.Time).count();
        } else {
            return cardRecordEntityDao.queryBuilder().where(CardRecordEntityDao.Properties.IsUploade.eq("0")).orderDesc(CardRecordEntityDao.Properties.Time).count();
        }
    }


    //上下班
    public static void setDriver(String driver) {
        AppRunConfigEntity appRunConfigEntity = DBManager.checkRunConfig();
        if (appRunConfigEntity.getDriverNo().equals("")) {
            appRunConfigEntity.setDriverNo(driver);
            DBManager.updateConfig(appRunConfigEntity);
            SoundPoolUtil.play(VoiceConfig.LOGIN);
        } else if (appRunConfigEntity.getDriverNo().equals(driver)) {
            appRunConfigEntity.setDriverNo("");
            DBManager.updateConfig(appRunConfigEntity);
            SoundPoolUtil.play(VoiceConfig.OUT_LOGIN);
        }

    }


    //添加各种通道的计费规则
    public static void insertPayRuleInfo(PayRuleInfo rcCcard) {
        PayRuleInfoDao payRuleInfoDao = getDaoSession().getPayRuleInfoDao();
        payRuleInfoDao.insertOrReplace(rcCcard);
    }

    //查询各种通道的计费规则
    public static PayRuleInfo checkPayRuleInfo(String tag) {
        PayRuleInfoDao payRuleInfoDao = getDaoSession().getPayRuleInfoDao();
        return payRuleInfoDao.queryBuilder().where(PayRuleInfoDao.Properties.Tag.eq(tag)).limit(1).unique();
    }

    //查询各种通道的计费规则
    public static PayRuleInfo checkPayRuleInfo(String tag, String cardType) {
        PayRuleInfoDao payRuleInfoDao = getDaoSession().getPayRuleInfoDao();
        return payRuleInfoDao.queryBuilder().where(PayRuleInfoDao.Properties.Tag.eq(tag), PayRuleInfoDao.Properties.ChildCardType.eq(cardType)).limit(1).unique();
    }


    //查询mac校验失败的记录
    public static CardRecordEntity checkPayErrHistory(String card_id) {
        try {
            CardRecordEntityDao cardRecordEntityDao = getDaoSession().getCardRecordEntityDao();
            return cardRecordEntityDao.queryBuilder().where(CardRecordEntityDao.Properties.CardNo.eq(card_id), CardRecordEntityDao.Properties.Status.in("30", "23")).orderDesc(
                    CardRecordEntityDao.Properties.Time
            ).limit(1).unique();
        } catch (Exception e) {
            return null;
        }
    }

    //添加站点信息
    public static void insertStationListInfo(StationListInfo stationListInfo) {
        StationListInfoDao stationListInfoDao = getDaoSession().getStationListInfoDao();
        stationListInfoDao.insertOrReplace(stationListInfo);
    }

    //查询站点信息
    public static StationListInfo checkStationListInfo() {
        StationListInfoDao stationListInfoDao = getDaoSession().getStationListInfoDao();
        return stationListInfoDao.queryBuilder().limit(1).unique();

    }

    //添加线路信息
    public static void insertLineInfo(LineInfo lineInfo) {
        LineInfoDao lineInfoDao = getDaoSession().getLineInfoDao();
        lineInfoDao.insertOrReplace(lineInfo);
    }

    //查询线路信息
    public static LineInfo checkLineInfo() {
        LineInfoDao lineInfoDao = getDaoSession().getLineInfoDao();
        return lineInfoDao.queryBuilder().limit(1).unique();
    }

    //更新记录上传状态
    public static void updateCardRecord(CardUpResponse.DataListBean dataListBean) {
        CardRecordEntityDao cardRecordEntityDao = getDaoSession().getCardRecordEntityDao();
        CardRecordEntity cardRecordEntity = cardRecordEntityDao.queryBuilder().where(CardRecordEntityDao.Properties.CardNo.eq(dataListBean.getCardNo()), CardRecordEntityDao.Properties.Uid.eq(dataListBean.getUid()),
                CardRecordEntityDao.Properties.Termid.eq(dataListBean.getTermid()), CardRecordEntityDao.Properties.IsUploade.eq("0")).limit(1).unique();
        if (cardRecordEntity != null) {
            cardRecordEntity.setIsUploade("1");
            cardRecordEntityDao.insertOrReplace(cardRecordEntity);
        }
    }

    public static List<AliPublicKeyEntity> checkAliPublicKey() {
        AliPublicKeyEntityDao aliPublicKeyEntityDao = getDaoSession().getAliPublicKeyEntityDao();
        return aliPublicKeyEntityDao.queryBuilder().orderAsc(AliPublicKeyEntityDao.Properties.Key_id).list();
    }

    public static void updateALScanRecord(AliScanResponse.DatalistBean datalistBean) {
        ScanRecordEntityDao scanRecordEntityDao = getDaoSession().getScanRecordEntityDao();
        ScanRecordEntity scanRecordEntity = scanRecordEntityDao.queryBuilder().where(ScanRecordEntityDao.Properties.Terseno.eq(datalistBean.getTerseno())
        ).limit(1).unique();
        if (scanRecordEntity != null) {
            scanRecordEntity.setUpState("1");
            scanRecordEntityDao.insertOrReplace(scanRecordEntity);
        }
    }

    public static void insertUnionPayRecord(UnionPayEntity payEntity) {
        UnionPayEntityDao unionPayEntityDao = getDaoSession().getUnionPayEntityDao();
        unionPayEntityDao.insertOrReplace(payEntity);
    }

    //添加票价计费规则
    public static void upDatePayRule(PayRuleInfo payRuleInfo) {
        PayRuleInfoDao payRuleInfoDao = getDaoSession().getPayRuleInfoDao();
        payRuleInfoDao.insertOrReplace(payRuleInfo);
    }

    public static void clearPayrule() {
        PayRuleInfoDao payRuleInfoDao = getDaoSession().getPayRuleInfoDao();
        payRuleInfoDao.deleteAll();
    }

    //查询5秒钟之前的数据  防止交易未完成的数据 进行了冲正
    public static List<UnionPayEntity> checkUnionRecord(boolean isAll) {
        UnionPayEntityDao dao = DBCore.getDaoSession().getUnionPayEntityDao();
        if (isAll) {
            return dao.queryBuilder().orderDesc(UnionPayEntityDao.Properties.Creattime).list();
        } else {
            return dao.queryBuilder().where(UnionPayEntityDao.Properties.UpStatus.eq(0))
                    .orderDesc(UnionPayEntityDao.Properties.Id)
                    .where(UnionPayEntityDao.Properties.Time.le(DateUtil.getCurrentDateLastSc(4)))
                    .limit(15).build().list();
        }
    }


    //查询5秒钟之前的数据  防止交易未完成的数据 进行了冲正
    public static long checkUnionRecordNum(boolean isAll) {
        UnionPayEntityDao dao = DBCore.getDaoSession().getUnionPayEntityDao();
        if (isAll) {
            return dao.queryBuilder().orderDesc(UnionPayEntityDao.Properties.Creattime).count();
        } else {
            return dao.queryBuilder().where(UnionPayEntityDao.Properties.UpStatus.eq(0))
                    .orderDesc(UnionPayEntityDao.Properties.Id)
                    .where(UnionPayEntityDao.Properties.Time.le(DateUtil.getCurrentDateLastSc(4)))
                    .count();
        }
    }

    //查询5秒钟之前的数据  防止交易未完成的数据 进行了冲正
    public static void updatekUnionRecordUpStatus(String string) {
        UnionPayEntityDao dao = DBCore.getDaoSession().getUnionPayEntityDao();
        UnionPayEntity unionPayEntity = dao.queryBuilder().where(UnionPayEntityDao.Properties.UniqueFlag.eq(string), UnionPayEntityDao.Properties.UpStatus.eq(0)).
                limit(1).unique();
        if (unionPayEntity != null) {
            unionPayEntity.setUpStatus(1);
            dao.insertOrReplace(unionPayEntity);
        }
    }

    //查询为上传的交通部二维码记录
    public static List<JTBscanRecord> checkJTBScan(Boolean isAll) {
        JTBscanRecordDao jtBscanRecordDao = getDaoSession().getJTBscanRecordDao();
        if (isAll) {
            return jtBscanRecordDao.queryBuilder().orderDesc(JTBscanRecordDao.Properties.Createtime).list();
        } else {
            return jtBscanRecordDao.queryBuilder().where(JTBscanRecordDao.Properties.IsUpload.eq("0")).limit(15).list();
        }
    }

    //查询为上传的交通部二维码记录数量
    public static long checkJTBScanNum(Boolean isAll) {
        JTBscanRecordDao jtBscanRecordDao = getDaoSession().getJTBscanRecordDao();
        if (isAll) {
            return jtBscanRecordDao.queryBuilder().orderDesc(JTBscanRecordDao.Properties.Createtime).count();
        } else {
            return jtBscanRecordDao.queryBuilder().where(JTBscanRecordDao.Properties.IsUpload.eq("0")).count();
        }
    }


    //插入交通部二维码数据
    public static void insertJTBScanRecord(JTBscanRecord jtBscanRecord) {
        JTBscanRecordDao dao = getDaoSession().getJTBscanRecordDao();
        dao.insertOrReplace(jtBscanRecord);
    }

    //更新交通部数据上传状态
    public static void updateJTBRecord(String terseno) {
        JTBscanRecordDao dao = DBCore.getDaoSession().getJTBscanRecordDao();
        JTBscanRecord jtBscanRecord = dao.queryBuilder().where(JTBscanRecordDao.Properties.Terseno.eq(terseno), JTBscanRecordDao.Properties.IsUpload.eq("0")).
                limit(1).unique();
        if (jtBscanRecord != null) {
            jtBscanRecord.setIsUpload("1");
            dao.insertOrReplace(jtBscanRecord);
        }
    }


    /**
     * 查询数据库中二维码的储存数据
     *
     * @return
     */
    public static Long checkScanRecordNum() {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        return scanRecordDao.queryBuilder().count();
    }

    /**
     * 查询数据库中二维码的储存数据的前3000条数据
     *
     * @return
     */
    public static List<ScanRecordEntity> checkScanRecordList() {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        return scanRecordDao.queryBuilder().orderAsc(ScanRecordEntityDao.Properties.CreatTime).limit(3000).list();
    }


    /**
     * 删除银联记录
     *
     * @return
     */
    public static void deleteScanRecord(List<ScanRecordEntity> scanRecordEntities) {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        scanRecordDao.deleteInTx(scanRecordEntities);
    }

    /**
     * 删除所有记录
     *
     * @return
     */
    public static void deleteScanRecord() {
        ScanRecordEntityDao scanRecordDao = getDaoSession().getScanRecordEntityDao();
        scanRecordDao.deleteAll();
    }


    /**
     * 查询数据库中银联的储存数据的前3000条数据
     *
     * @return
     */
    public static List<UnionPayEntity> checkUnionRecordList() {
        UnionPayEntityDao unionPayEntityDao = getDaoSession().getUnionPayEntityDao();
        return unionPayEntityDao.queryBuilder().orderAsc(UnionPayEntityDao.Properties.Creattime).limit(3000).list();
    }


    /**
     * 删除银联记录
     *
     * @return
     */
    public static void deleteUnionRecord(List<UnionPayEntity> union) {
        UnionPayEntityDao unionPayEntityDao = getDaoSession().getUnionPayEntityDao();
        unionPayEntityDao.deleteInTx(union);
    }


    /**
     * 查询前3000条JTB二维码记录  用于文件保存
     *
     * @return
     */
    public static List<JTBscanRecord> checkJTBScanRecordList() {
        JTBscanRecordDao jtBscanRecordDao = getDaoSession().getJTBscanRecordDao();
        return jtBscanRecordDao.queryBuilder().orderAsc(JTBscanRecordDao.Properties.Createtime).limit(3000).list();
    }

    /**
     * 删除JTB二维码记录
     *
     * @return
     */
    public static void deleteJTBRecord(List<JTBscanRecord> jtBscanRecords) {
        JTBscanRecordDao jTBPayEntityDao = getDaoSession().getJTBscanRecordDao();
        jTBPayEntityDao.deleteInTx(jtBscanRecords);
    }


    //查询刷卡3000条历史记录
    public static List<CardRecordEntity> checkCardRecord() {
        CardRecordEntityDao cardRecordEntityDao = getDaoSession().getCardRecordEntityDao();
        return cardRecordEntityDao.queryBuilder().orderAsc(CardRecordEntityDao.Properties.Time).limit(3000).list();
    }

    //删除刷卡记录
    public static void deleteCardRecord(List<CardRecordEntity> cardRecordEntities) {
        CardRecordEntityDao cardRecordEntityDao = getDaoSession().getCardRecordEntityDao();
        cardRecordEntityDao.deleteInTx(cardRecordEntities);
    }
}