package com.xb.haikou.moudle.function.card;

import android.util.Log;
import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.Type;
import com.hao.lib.base.Rx.Rx;
import com.xb.haikou.BuildConfig;
import com.xb.haikou.base.AppRunParam;
import com.xb.haikou.cmd.devCmd;
import com.xb.haikou.config.line.PayRuleInfo;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.record.CardRecordEntity;
import com.xb.haikou.record.RecordUpload;
import com.xb.haikou.util.AppUtil;
import com.xb.haikou.util.BusToast;
import com.xb.haikou.util.DateUtil;
import com.xb.haikou.voice.SoundPoolUtil;
import com.xb.haikou.voice.VoiceConfig;
import org.jetbrains.annotations.Nullable;

import static java.lang.System.arraycopy;

public class PraseCard {
    public void praseCardDate(byte[] bytes) {
        try {
            //卡解析
            CardInfoEntity cardInfoEntity = new CardInfoEntity();
            cardInfoEntity.putDate(bytes);

            if (cardInfoEntity.status.equals("00") && !cardInfoEntity.selete_aid.equals("00")) {
                CardRecordEntity cardRecordEntity = DBManager.checkPayErrHistory(cardInfoEntity.card_id);
                if (cardRecordEntity != null && Math.abs(cardRecordEntity.getTime() - System.currentTimeMillis()) < 60000) {
                    //检测到有mac校验失败的数据
                    Rx.getInstance().sendMessage("setRight", FileUtils.hex2byte(cardInfoEntity.file18InfoEntity.getTransaction_number_18()));//发送命令校准记录
                    return;
                }

                if (AppRunParam.getInstance().getDriverNo().equals("")) {
                    BusToast.showToast("请刷司机卡", false);
                } else if (AppRunParam.getInstance().getLineId().equals("")) {
                    BusToast.showToast("请设置线路", false);
                } else {
                    packagePayCmd(cardInfoEntity);
                }
            }
        } catch (Exception e) {
            Log.i("错误", "报错了 " + e.getMessage());
        }

    }

    //组装消费命令
    private void packagePayCmd(CardInfoEntity cardInfoEntity) throws Exception {
        int i = 0;
        byte[] pay = new byte[122];

        int tag = 0x10 | FileUtils.hex2byte(cardInfoEntity.selete_aid)[0];
        pay[0] = FileUtils.int2byte(tag)[0];
        i++;

        byte[] uid = FileUtils.hex2byte(cardInfoEntity.uid);
        arraycopy(uid, 0, pay, i, uid.length);
        i += uid.length;

        if (cardInfoEntity.selete_aid.equals("02")) {
            package1EFile(cardInfoEntity).getHexString();
            //19文件中 刷卡优惠次数对扣费有影响
            package19File(cardInfoEntity).getHexString();


            byte[] file1E = FileUtils.hex2byte(cardInfoEntity.file1EInfoEntity.getHexString());
            arraycopy(file1E, 0, pay, i, file1E.length);
            i += file1E.length;

            //19文件目前只针对爱心卡和老年卡有用
            Log.i("长度", "i=" + i + "              file1e=" + file1E.length);
            pay[i] = 0x01;//19文件跟新判断标识（0x00为不更新，0x01为更新）
            i++;

            byte[] file19 = FileUtils.hex2byte(cardInfoEntity.file19InfoEntity.getHexString());
            arraycopy(file19, 0, pay, i, file19.length);
            i += file1E.length;
        } else {//老宝岛卡必须更新19文件
            if (cardInfoEntity.fileLocal17InfoEntity.card_type.toUpperCase().equals("F1") || cardInfoEntity.fileLocal17InfoEntity.card_type.toUpperCase().equals("FF")) {
                return;
            }
            //管理卡 不走消费流程
            File1EInfoEntity file1EInfoEntity = new File1EInfoEntity();
            cardInfoEntity.file1EInfoEntity = file1EInfoEntity;
            cardInfoEntity.file1EInfoEntity = package1EFile(cardInfoEntity);


            //组件宝岛卡 19文件包
            cardInfoEntity.fileLocal19InfoEntity = packageold19File(cardInfoEntity);


            /********************刷卡数据组装**************************/
            byte[] file1E = FileUtils.hex2byte(cardInfoEntity.file1EInfoEntity.getHexString());
            arraycopy(file1E, 0, pay, i, file1E.length);
            i += file1E.length;

            //19文件目前只针对爱心卡和老年卡有用
            Log.i("长度", "i=" + i + "              file1e=" + file1E.length);
            pay[i] = 0x01;//19文件跟新判断标识（0x00为不更新，0x01为更新）
            i++;

            byte[] file19 = FileUtils.hex2byte(cardInfoEntity.fileLocal19InfoEntity.getHexString());
            arraycopy(file19, 0, pay, i, file19.length);
            i += file1E.length;
        }
        Log.i("消费命令020641", "paycmd=" + FileUtils.bytesToHexString(pay));
        Rx.getInstance().sendMessage(pay, cardInfoEntity);
    }

    //消费返回数据
    public static void payResponse(devCmd devCmd, CardInfoEntity cardInfoEntity) throws Exception {
        try {
            byte[] payResult = new byte[devCmd.getnRecvLen()];
            arraycopy(devCmd.getDataBuf(), 0, payResult, 0, payResult.length);

            int i = 0;
            //用于存储错误码
            byte[] Status = new byte[1];
            arraycopy(payResult, i, Status, 0, Status.length);
            i += Status.length;
            String status = (String) FileUtils.byte2Parm(Status, Type.HEX);


            if (status.equals("00") || status.equals("30") || status.equals("23")) {//00 正常  30，23标示mac2错误 需要校验
                //每次操作后的sw值，用于配合错误码上报错误
                byte[] Sw = new byte[2];
                arraycopy(payResult, i, Sw, 0, Sw.length);
                i += Sw.length;
                String sw = (String) FileUtils.byte2Parm(Sw, Type.HEX);


                //卡片余额
                byte[] Card_remain = new byte[4];
                arraycopy(payResult, i, Card_remain, 0, Card_remain.length);
                i += Card_remain.length;
                int card_remain = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(Card_remain, Type.HEX));

                //交易序号
                byte[] Transaction_number = new byte[2];
                arraycopy(payResult, i, Transaction_number, 0, Transaction_number.length);
                i += Transaction_number.length;
                String transaction_number = (String) FileUtils.byte2Parm(Transaction_number, Type.HEX);

                //透支限额
                byte[] Overdrawn_account = new byte[3];
                arraycopy(payResult, i, Overdrawn_account, 0, Overdrawn_account.length);
                i += Overdrawn_account.length;
                String overdrawn_account = (String) FileUtils.byte2Parm(Overdrawn_account, Type.HEX);

                //密钥版本号
                byte[] Key_version = new byte[1];
                arraycopy(payResult, i, Key_version, 0, Key_version.length);
                i += Key_version.length;
                String key_version = (String) FileUtils.byte2Parm(Key_version, Type.HEX);


                //密钥标示
                byte[] Key_flag = new byte[1];
                arraycopy(payResult, i, Key_flag, 0, Key_flag.length);
                i += Key_flag.length;
                String key_flag = (String) FileUtils.byte2Parm(Key_flag, Type.HEX);


                //伪随机数
                byte[] Random_number = new byte[4];
                arraycopy(payResult, i, Random_number, 0, Random_number.length);
                i += Random_number.length;
                String random_number = (String) FileUtils.byte2Parm(Random_number, Type.HEX);


                //终端交易序号
                byte[] Sam_transaction_number = new byte[4];
                arraycopy(payResult, i, Sam_transaction_number, 0, Sam_transaction_number.length);
                i += Sam_transaction_number.length;
                String sam_transaction_number = (String) FileUtils.byte2Parm(Sam_transaction_number, Type.HEX);

                //MAC1
                byte[] MAC1 = new byte[4];
                arraycopy(payResult, i, MAC1, 0, MAC1.length);
                i += MAC1.length;
                String mAC1 = (String) FileUtils.byte2Parm(MAC1, Type.HEX);

                //TAC
                byte[] TAC = new byte[4];
                arraycopy(payResult, i, TAC, 0, TAC.length);
                i += TAC.length;
                String tAC = (String) FileUtils.byte2Parm(TAC, Type.HEX);

                //MAC1
                byte[] MAC2 = new byte[4];
                arraycopy(payResult, i, MAC2, 0, MAC2.length);
                i += MAC2.length;
                String mAC2 = (String) FileUtils.byte2Parm(MAC2, Type.HEX);

                //余额
                byte[] Balance = new byte[4];
                arraycopy(payResult, i, Balance, 0, Balance.length);
                i += Balance.length;
                int balance = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(Balance, Type.HEX));


                byte[] Uid = new byte[4];
                arraycopy(payResult, i, Uid, 0, Uid.length);
                i += Uid.length;
                String uid = (String) FileUtils.byte2Parm(Uid, Type.HEX);

                byte[] Card_id = new byte[8];
                arraycopy(payResult, i, Card_id, 0, Card_id.length);
                i += Card_id.length;
                String card_id = (String) FileUtils.byte2Parm(Card_id, Type.HEX);


                byte[] Card_type = new byte[1];
                arraycopy(payResult, i, Card_type, 0, Card_type.length);
                i += Card_type.length;
                String card_type = (String) FileUtils.byte2Parm(Card_type, Type.HEX);

                byte[] Pay_acount = new byte[2];
                arraycopy(payResult, i, Pay_acount, 0, Pay_acount.length);
                i += Pay_acount.length;
                int pay_acount = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(Pay_acount, Type.HEX));

                CardRecordEntity cardRecordEntity = new CardRecordEntity();
                cardRecordEntity.setCardAmt(balance + "");//交易后余额
                cardRecordEntity.setTACCode(tAC);//交易 TAC 码
                cardRecordEntity.setKeyver(key_version);//密钥版本号
                cardRecordEntity.setTransKeyVersion(key_version);
                cardRecordEntity.setStatus(status);
                cardRecordEntity.setAlgTag(key_flag);
                cardRecordEntity.setCardversion(card_type);
                cardRecordEntity.setCardCsn(cardInfoEntity.uid);//8个长度String 卡csn
                cardRecordEntity.setBatchNo("");//6个长度String  批次号
                cardRecordEntity.setTransSeqId(sam_transaction_number);
                cardRecordEntity.setPsamTradeCount(sam_transaction_number);//psam卡交易流水
                cardRecordEntity.setCardVersion(card_type);
                saveRecord(cardInfoEntity, cardRecordEntity);
                if (card_remain - balance == 0) {
                    BusToast.showToast("当前使用次数：" + pay_acount + "\n剩余：" + (120 - pay_acount), true);
                } else {
                    BusToast.showToast("本次扣款：" + FileUtils.fen2Yuan(FileUtils.hexStringToInt(cardInfoEntity.file1EInfoEntity.transaction_amount_1e)) + "元\n余额：" +
                            FileUtils.fen2Yuan(balance) + "元", true);
                }
                SoundPoolUtil.play(VoiceConfig.IC_BASE);
            } else {
                BusToast.showToast("刷卡失败【" + status + "】", false);
                SoundPoolUtil.play(VoiceConfig.IC_ERROR);
            }
        } catch (Exception e) {
            SoundPoolUtil.play(VoiceConfig.IC_ERROR);
            BusToast.showToast("刷卡失败【-1】", false);
        }
    }

    private static void saveRecord(CardInfoEntity cardInfoEntity, CardRecordEntity cardRecordEntity) throws Exception {
        cardRecordEntity.setUid(cardInfoEntity.uid);
        cardRecordEntity.setBizType("10");//10 一卡通默认
        if (cardInfoEntity.selete_aid.equals("02")) {//互联互通
            cardRecordEntity.setCardType(cardInfoEntity.file17InfoEntity.card_type);
            cardRecordEntity.setCardissuercitycode(cardInfoEntity.file17InfoEntity.city_code);// 卡所属地城市代码(卡片中读取）
            cardRecordEntity.setCardissuercitycode2(cardInfoEntity.file17InfoEntity.city_code);// 卡所属地城市代码(卡片中读取）
            cardRecordEntity.setCardmaintype(cardInfoEntity.file17InfoEntity.card_type);// 上一笔交易的交易类型
            cardRecordEntity.setCardIssuerId(cardInfoEntity.file15InfoEntity.card_issuer);//发卡机构标识
            cardRecordEntity.setCardNo(cardInfoEntity.file15InfoEntity.pan);
            cardRecordEntity.setKeyindex("01");// 密钥索引  //TODO 目前取自PSAM卡
        } else {//宝岛卡
            cardRecordEntity.setCardType(cardInfoEntity.fileLocal17InfoEntity.card_type);
            cardRecordEntity.setCardissuercitycode("6410");// 卡所属地城市代码(卡片中读取）
            cardRecordEntity.setCardissuercitycode2("6410");// 卡所属地城市代码(卡片中读取）
            cardRecordEntity.setCardmaintype(cardInfoEntity.fileLocal17InfoEntity.card_type);
            cardRecordEntity.setCardIssuerId(cardInfoEntity.fileLocal17InfoEntity.card_issuer);
            cardRecordEntity.setCardNo(cardInfoEntity.fileLocal17InfoEntity.pan);
            cardRecordEntity.setKeyindex("00");// 密钥索引  //TODO 目前取自PSAM卡
        }
        cardRecordEntity.setTradeType("09");

        cardRecordEntity.setFareAmt(FileUtils.hexStringToInt(cardInfoEntity.file1EInfoEntity.transaction_amount_1e));//交易金额
        cardRecordEntity.setTradeTime(DateUtil.getCurrentDate());//交易时间
        cardRecordEntity.setCardTradeCount(cardInfoEntity.file18InfoEntity.transaction_number_18);//用户卡内交易序号
        cardRecordEntity.setCompanyNo(AppRunParam.getInstance().getUnitno());
        cardRecordEntity.setLineNo(FileUtils.deleteCover(AppRunParam.getInstance().getLineId()));
        cardRecordEntity.setBusNo(AppRunParam.getInstance().getBusNo());
        cardRecordEntity.setDriverNo(AppRunParam.getInstance().getDriverNo().substring(4, 20));
        cardRecordEntity.setPasmNumber(AppRunParam.getInstance().getPSAM(0)); //TODO 有重复
        cardRecordEntity.setDirection("AB");//行驶方向 AB上行 BA 下行
        cardRecordEntity.setDirection(AppRunParam.getInstance().getNowStation());//当前站点号
        cardRecordEntity.setTicketMore("00");//TODO 需要确认值
        cardRecordEntity.setCurrentLineNo(FileUtils.deleteCover(AppRunParam.getInstance().getLineId()));//当前线路号 //TODO 有重复
        cardRecordEntity.setCurrentBusNo(AppRunParam.getInstance().getBusNo());//车号 //TODO 有重复
        cardRecordEntity.setCurrentDriverNo(AppRunParam.getInstance().getDriverNo().substring(1, 20));//司机号 //TODO 有重复
        cardRecordEntity.setBackup("");//备用
        cardRecordEntity.setTermid(AppRunParam.getInstance().getPosSn());
        cardRecordEntity.setTermseq(AppUtil.getTranNum() + "");//TODO 终端的流水号
        cardRecordEntity.setMchid(AppRunParam.getInstance().getMchID());// 商户号
        cardRecordEntity.setMrchid(AppRunParam.getInstance().getMchID());
        cardRecordEntity.setKeytype("");// 密钥类型//TODO 不知道是什么东西
        cardRecordEntity.setCitycode(DBManager.checkBuildConfig().getCity_code());// 城市代码
        cardRecordEntity.setInternalcode("02");// 行业代码//TODO 不知道是什么东西

        cardRecordEntity.setLasttranstype("09");
        cardRecordEntity.setLastterminalid(cardInfoEntity.file18InfoEntity.pose_id);// 前次PSAM终端号
        cardRecordEntity.setLasttranstime(cardInfoEntity.file18InfoEntity.transaction_time);// 前次交易时间
        cardRecordEntity.setLastpayfee(FileUtils.hexStringToInt(cardInfoEntity.file18InfoEntity.transaction_amount) + "");// 前次交易金额
        cardRecordEntity.setLastcardtransseqid(cardInfoEntity.file18InfoEntity.transaction_number_18);// 上一笔交易卡片计数器
        cardRecordEntity.setAcquirer(FileUtils.deleteCover(DBManager.checkLineInfo().getAcquirer()));//交易收单方标识
        cardRecordEntity.setTransStatus("00");//交易状态00-正常消费，01-进站交易，02-出站交易
        cardRecordEntity.setCurrency("156");
        cardRecordEntity.setTotalFee(AppRunParam.getInstance().getBasePrice() + "");//交易总金额
        cardRecordEntity.setIsUploade("0");
        cardRecordEntity.setTime(System.currentTimeMillis());
        cardRecordEntity.setApplytype(BuildConfig.isTest ? "FF" : "FD");
        cardRecordEntity.setConductorId(AppRunParam.getInstance().getConductorid());
        cardRecordEntity.setPsamTerminalId(DBManager.checkRunConfig().getPSAMID());
        DBManager.saveCardRecord(cardRecordEntity);
        RecordUpload.upLoadCardRecord();
    }

    //扣费组包命令  1E文件用于扣费
    private File1EInfoEntity package1EFile(CardInfoEntity cardInfoEntity) throws Exception {
        cardInfoEntity.file1EInfoEntity.transaction_type_1e = "09";
        cardInfoEntity.file1EInfoEntity.terminal_number_1e = FileUtils.formatStringToByteString(8, DBManager.checkConfig().getPSAMID() == null ? "" : DBManager.checkConfig().getPSAMID());
        cardInfoEntity.file1EInfoEntity.industry_code = "02";
        cardInfoEntity.file1EInfoEntity.line_1e = FileUtils.formatStringToByteString(2, AppRunParam.getInstance().getLineId());
        cardInfoEntity.file1EInfoEntity.operation_code_1e = FileUtils.formatStringToByteString(2, DBManager.checkLineInfo().getOperatorCode());
        cardInfoEntity.file1EInfoEntity.sites_1e = "0001";
        cardInfoEntity.file1EInfoEntity.reserve_1e = FileUtils.bytesToHexString(new byte[1]);
        cardInfoEntity.file1EInfoEntity.transaction_time_1e = FileUtils.formatStringToByteString(7, DateUtil.getCurrentDate2());
        cardInfoEntity.file1EInfoEntity.transaction_city_code_1e = FileUtils.formatStringToByteString(2, DBManager.checkLineInfo().getzJBCityCode());
        cardInfoEntity.file1EInfoEntity.institutional_identity_1e = FileUtils.formatStringToByteString(8, DBManager.checkLineInfo().getjTBAcquirerCode());
        cardInfoEntity.file1EInfoEntity.reserve2_1e = FileUtils.bytesToHexString(new byte[6]);
        return cardInfoEntity.file1EInfoEntity;
    }

    //扣费组包命令交通部  19文件用于扣次数
    private File19InfoEntity package19File(CardInfoEntity cardInfoEntity) throws Exception {
        cardInfoEntity.file19InfoEntity.record_id = "270F";                    //记录id 1
        cardInfoEntity.file19InfoEntity.record_length = "7D";                //记录长度 1
        cardInfoEntity.file19InfoEntity.record_enable = "01";                //应用有效标示 1
        cardInfoEntity.file19InfoEntity.boarding_terminal_number = FileUtils.formatStringToByteString(6, DBManager.checkConfig().getPSAMID());//上车终端编号 6
        cardInfoEntity.file19InfoEntity.boarding_time = FileUtils.formatStringToByteString(7, DateUtil.getCurrentDate2());            //上车时间 7
        cardInfoEntity.file19InfoEntity.boarding_vehicle_number = FileUtils.formatStringToByteString(5, AppRunParam.getInstance().getCarNo());  //上车车辆号 5
        cardInfoEntity.file19InfoEntity.boarding_the_site = FileUtils.formatStringToByteString(1, AppRunParam.getInstance().getNowStation());  //上车站点 1
        cardInfoEntity.file19InfoEntity.direction_identity = FileUtils.formatStringToByteString(1, AppRunParam.getInstance().getDirection());  //乘坐方向 1
        cardInfoEntity.file19InfoEntity.alight_terminal_number = FileUtils.formatStringToByteString(6, DBManager.checkConfig().getPSAMID());    //下车终端编号 6
        cardInfoEntity.file19InfoEntity.alight_time = FileUtils.formatStringToByteString(7, DateUtil.getCurrentDate2());                //下车时间 7
        cardInfoEntity.file19InfoEntity.alight_vehicle_number = FileUtils.formatStringToByteString(5, AppRunParam.getInstance().getCarNo());    //下车车辆号 5
        cardInfoEntity.file19InfoEntity.alight_the_site = FileUtils.formatStringToByteString(1, AppRunParam.getInstance().getNowStation());            //下车站点 1
        cardInfoEntity.file19InfoEntity.boarding_line_number = FileUtils.formatStringToByteString(4, AppRunParam.getInstance().getLineId());    //上车线路号 4
        cardInfoEntity.file19InfoEntity.boarding_max_amount = FileUtils.formatStringToByteString(4, FileUtils.int2ByteStr(AppRunParam.getInstance().getCardPayFee(cardInfoEntity.file17InfoEntity.card_type), 4));        //标注金额 4
        cardInfoEntity.file19InfoEntity.reserved3 = FileUtils.bytesToHexString(new byte[4]);//预留空间 4

        int count = getPriceCount(cardInfoEntity.file19InfoEntity.last_trading_time, cardInfoEntity.file19InfoEntity.trading_number, cardInfoEntity.file17InfoEntity.card_type);
        if (count != -1) {
            int price = AppRunParam.getInstance().getCardPayFee(cardInfoEntity.file17InfoEntity.card_type);
            cardInfoEntity.file1EInfoEntity.transaction_amount_1e = FileUtils.int2ByteStr(price, 4);
            int balance = FileUtils.hexStringToInt(cardInfoEntity.balance) - price;
            cardInfoEntity.file1EInfoEntity.transaction_balance_1e = FileUtils.int2ByteStr(balance, 4);
        } else {
            cardInfoEntity.file19InfoEntity.trading_number = FileUtils.int2ByteStr(count, 2);
            cardInfoEntity.file1EInfoEntity.transaction_amount_1e = FileUtils.int2ByteStr(AppRunParam.getInstance().getBasePrice(), 4);
            int balance = FileUtils.hexStringToInt(cardInfoEntity.balance) - AppRunParam.getInstance().getBasePrice();
            cardInfoEntity.file1EInfoEntity.transaction_balance_1e = FileUtils.int2ByteStr(balance, 4);
        }
        return cardInfoEntity.file19InfoEntity;
    }

    //扣费组包命令宝岛卡  19文件用于扣次数
    private FileLocal19InfoEntity packageold19File(CardInfoEntity cardInfoEntity) throws Exception {
//        cardInfoEntity.fileLocal19InfoEntity.record_id;                    //记录id 1
//        cardInfoEntity.fileLocal19InfoEntity.record_length;                //记录长度 1
//        cardInfoEntity.fileLocal19InfoEntity.apply_lock;                    //应用锁定标识 1
        cardInfoEntity.fileLocal19InfoEntity.board_alight_status = "01";        //入出站/上下车状态 1
        cardInfoEntity.fileLocal19InfoEntity.boarding_terminal_number = FileUtils.formatStringToByteString(6, DBManager.checkConfig().getPSAMID());//上车终端编号 6
        cardInfoEntity.fileLocal19InfoEntity.boarding_time = FileUtils.formatStringToByteString(7, DateUtil.getCurrentDate2());            //上车时间 7
        cardInfoEntity.fileLocal19InfoEntity.boarding_vehicle_number = FileUtils.formatStringToByteString(5, AppRunParam.getInstance().getCarNo());  //上车车辆号 5
        cardInfoEntity.fileLocal19InfoEntity.boarding_the_site = FileUtils.formatStringToByteString(1, AppRunParam.getInstance().getNowStation());  //上车站点 1
        cardInfoEntity.fileLocal19InfoEntity.direction_identity = FileUtils.formatStringToByteString(1, AppRunParam.getInstance().getDirection());  //乘坐方向 1
        cardInfoEntity.fileLocal19InfoEntity.alight_terminal_number = FileUtils.formatStringToByteString(6, DBManager.checkConfig().getPSAMID());    //下车终端编号 6
        cardInfoEntity.fileLocal19InfoEntity.alight_time = FileUtils.formatStringToByteString(7, DateUtil.getCurrentDate2());                //下车时间 7
        cardInfoEntity.fileLocal19InfoEntity.alight_vehicle_number = FileUtils.formatStringToByteString(5, AppRunParam.getInstance().getCarNo());    //下车车辆号 5
        cardInfoEntity.fileLocal19InfoEntity.alight_the_site = FileUtils.formatStringToByteString(1, AppRunParam.getInstance().getNowStation());            //下车站点 1
        cardInfoEntity.fileLocal19InfoEntity.boarding_line_number = FileUtils.formatStringToByteString(4, AppRunParam.getInstance().getLineId());    //上车线路号 4
        cardInfoEntity.fileLocal19InfoEntity.boarding_max_amount = FileUtils.formatStringToByteString(4, FileUtils.int2ByteStr(AppRunParam.getInstance().getCardPayFee(cardInfoEntity.fileLocal17InfoEntity.card_type), 4));        //标注金额 4
        cardInfoEntity.fileLocal19InfoEntity.reserved3 = FileUtils.bytesToHexString(new byte[4]);//预留空间 4

        cardInfoEntity.fileLocal19InfoEntity.last_trading_time = FileUtils.formatStringToByteString(7, DateUtil.getCurrentDate2());        //最后一次交易时间 7

        cardInfoEntity.fileLocal19InfoEntity.reserved4 = FileUtils.bytesToHexString(new byte[3]);          //预留   3

        int count = getPriceCount(cardInfoEntity.fileLocal19InfoEntity.last_trading_time, cardInfoEntity.fileLocal19InfoEntity.trading_number, cardInfoEntity.fileLocal17InfoEntity.card_type);

        if (count != -1) {
            cardInfoEntity.fileLocal19InfoEntity.trading_number = FileUtils.int2ByteStr(count, 2);
            int price = AppRunParam.getInstance().getCardPayFee(cardInfoEntity.fileLocal17InfoEntity.card_type);
            cardInfoEntity.file1EInfoEntity.transaction_amount_1e = FileUtils.int2ByteStr(price, 4);
            int balance = FileUtils.hexStringToInt(cardInfoEntity.balance) - price;
            cardInfoEntity.file1EInfoEntity.transaction_balance_1e = FileUtils.int2ByteStr(balance, 4);
        } else {
            cardInfoEntity.file1EInfoEntity.transaction_amount_1e = FileUtils.int2ByteStr(AppRunParam.getInstance().getBasePrice(), 4);
            int balance = FileUtils.hexStringToInt(cardInfoEntity.balance) - AppRunParam.getInstance().getBasePrice();
            cardInfoEntity.file1EInfoEntity.transaction_balance_1e = FileUtils.int2ByteStr(balance, 4);
        }
        return cardInfoEntity.fileLocal19InfoEntity;
    }

    //mac2 错误 进行记录校准
    public static void checkMac(@Nullable devCmd checkMac) {


        int i = 0;
        byte[] result = new byte[checkMac.getnRecvLen()];
        arraycopy(checkMac.getDataBuf(), 0, result, 0, result.length);
        String str = FileUtils.bytesToHexString(result);

        byte[] Status = new byte[1];
        arraycopy(result, 0, Status, i, Status.length);
        String status = FileUtils.bytesToHexString(Status);
        i += Status.length;

        if (status.equals("00")) {
            byte[] Sw = new byte[2];
            arraycopy(result, i, Sw, 0, Sw.length);
            String sw = FileUtils.bytesToHexString(Sw);
            i += Sw.length;

            byte[] Mac2 = new byte[4];
            arraycopy(result, i, Mac2, 0, Mac2.length);
            String mac2 = FileUtils.bytesToHexString(Mac2);
            i += Mac2.length;


            byte[] Tac = new byte[4];
            arraycopy(result, i, Tac, 0, Tac.length);
            String tac = FileUtils.bytesToHexString(Tac);
            i += Tac.length;

            byte[] Transaction_number_18 = new byte[2];
            arraycopy(result, i, Transaction_number_18, 0, Transaction_number_18.length);
            String transaction_number_18 = FileUtils.bytesToHexString(Transaction_number_18);
            i += Transaction_number_18.length;


            byte[] Overdrawn_account = new byte[3];
            arraycopy(result, i, Overdrawn_account, 0, Overdrawn_account.length);
            String overdrawn_account = FileUtils.bytesToHexString(Overdrawn_account);
            i += Overdrawn_account.length;


            byte[] Transaction_amount = new byte[4];
            arraycopy(result, i, Transaction_amount, 0, Transaction_amount.length);
            String transaction_amount = FileUtils.bytesToHexString(Transaction_amount);
            i += Transaction_amount.length;


            byte[] Transaction_type = new byte[1];
            arraycopy(result, i, Transaction_type, 0, Transaction_type.length);
            String transaction_type = FileUtils.bytesToHexString(Transaction_type);
            i += Transaction_type.length;


            byte[] Pose_id = new byte[6];
            arraycopy(result, i, Pose_id, 0, Pose_id.length);
            String pose_id = FileUtils.bytesToHexString(Pose_id);
            i += Pose_id.length;

            byte[] Transaction_time = new byte[7];
            arraycopy(result, i, Transaction_time, 0, Transaction_time.length);
            String transaction_time = FileUtils.bytesToHexString(Transaction_time);
            i += Transaction_time.length;

            byte[] Uid = new byte[4];
            arraycopy(result, i, Uid, 0, Uid.length);
            String uid = FileUtils.bytesToHexString(Uid);
            i += Uid.length;

            byte[] Card_id = new byte[8];
            arraycopy(result, i, Card_id, 0, Card_id.length);
            String card_id = FileUtils.bytesToHexString(Card_id);
            i += Card_id.length;


            CardRecordEntity cardRecordEntity = DBManager.checkPayErrHistory(card_id);
            cardRecordEntity.setIsUploade("0");
            cardRecordEntity.setTACCode(tac);
            DBManager.saveCardRecord(cardRecordEntity);
        }

    }

    /**
     * 优惠次数判断  true 正常取票价  false 取基础票价
     *
     * @param time      最后一次交易时间
     * @param nowCount  当前的优惠次数
     * @param card_type 卡类型
     * @return -1 表示超过优惠次数   else 当前交易的优惠次数
     */
    public int getPriceCount(String time, String nowCount, String card_type) {
        PayRuleInfo payRuleInfo = DBManager.checkPayRuleInfo("PK", card_type);
        if (payRuleInfo == null) {
            return -1;
        }
        if (payRuleInfo.getSpecialDiscountRuleFlag().equals("00")) {//无优惠周期
            return FileUtils.hexStringToInt(nowCount);
        } else if (payRuleInfo.getSpecialDiscountRuleFlag().equals("01")) {//日
            if (time.substring(6, 8).equals(DateUtil.getCurrentDate2().substring(6, 8))) {
                int count = FileUtils.hexStringToInt(nowCount);//当月交易次数 2
                if (count >= FileUtils.hexStringToInt(payRuleInfo.getSpecialDiscountRuleCount())) {
                    return -1;
                } else {
                    return ++count;
                }
            } else {
                return 1;//当月交易次数 2   固定为1
            }
        } else if (payRuleInfo.getSpecialDiscountRuleFlag().equals("02")) {//月
            if (time.substring(4, 6).equals(DateUtil.getCurrentDate2().substring(4, 6))) {
                int count = FileUtils.hexStringToInt(nowCount);//当月交易次数 2
                if (count >= FileUtils.hexStringToInt(payRuleInfo.getSpecialDiscountRuleCount())) {
                    return -1;
                } else {
                    return ++count;
                }
            } else {
                return 1;//当月交易次数 2   固定为1
            }
        } else if (payRuleInfo.getSpecialDiscountRuleFlag().equals("03")) {//年
            if (time.substring(0, 4).equals(DateUtil.getCurrentDate2().substring(0, 4))) {
                int count = FileUtils.hexStringToInt(nowCount);//当年交易次数 2
                if (count >= FileUtils.hexStringToInt(payRuleInfo.getSpecialDiscountRuleCount())) {
                    return -1;
                } else {
                    return ++count;
                }
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }
}
