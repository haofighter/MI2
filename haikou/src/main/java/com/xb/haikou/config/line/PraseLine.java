package com.xb.haikou.config.line;

import android.util.Log;
import com.google.gson.Gson;
import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.Type;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.util.BusToast;
import com.xb.haikou.voice.SoundPoolUtil;
import com.xb.haikou.voice.VoiceConfig;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.arraycopy;

/**
 * 解析线路文件
 */
public class PraseLine {

    //解析线路文件基本结构
    public static void praseLineFile(byte[] line) {
        //防止重复保存票价扣费规则  每次解析线路时对票价进行清空
        DBManager.clearPayrule();

        int i = 0;

        //文件标识，定为 XLJF，ASCII 码
        byte[] FileTag = new byte[4];
        arraycopy(line, i, FileTag, 0, FileTag.length);
        i += FileTag.length;
        String tag = (String) FileUtils.byte2Parm(FileTag, Type.ASCLL);


        //后续数据长度, Bigendian
        byte[] DateLen = new byte[4];
        arraycopy(line, i, DateLen, 0, FileTag.length);
        i += DateLen.length;
        int dateLen = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(DateLen, Type.HEX));

        byte[] fileDate = new byte[dateLen];
        arraycopy(line, i, fileDate, 0, fileDate.length);

        Log.i("线路解析  数据域", " dateLen=" + dateLen);
        //截取文件信息
        praseFileDate(fileDate);
    }

    //文件信息
    private static void praseFileDate(byte[] fileDate) {
        try {
            int i = 0;
            while (i < fileDate.length) {
                i = checkPrase(i, fileDate, null);
            }
            BusToast.showToast("线路设置成功", true);
            SoundPoolUtil.play(VoiceConfig.IC_BASE);
        } catch (Exception e) {
            e.getMessage();
        }

    }

    //单票种计费规则方案数据结构 SingleCardFarePlan
    //针对各种方式的计费规则进行解析
    //TODO 暂未处理
    private static int prasePayTicketContent(byte[] cardTicketContent, PayRuleInfo payRuleInfo) {
        int i = 0;
        byte[] CardTypeNum = new byte[1];
        arraycopy(cardTicketContent, i, CardTypeNum, 0, CardTypeNum.length);
        int cardTypeNum = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(CardTypeNum, Type.HEX));
        i += CardTypeNum.length;
        payRuleInfo.setCardTypeNum(cardTypeNum);

        for (int k = 0; k < cardTypeNum; k++) {
            PayRuleInfo newPayRuleInfo = new PayRuleInfo();
            //高字节代表主类型，低字节代表子类型
            byte[] CardType = new byte[1];
            arraycopy(cardTicketContent, i, CardType, 0, CardType.length);
            String cardType = (String) FileUtils.byte2Parm(CardType, Type.HEX);
            i += CardType.length;
            newPayRuleInfo.setCardType(cardType);

            byte[] ChildCardType = new byte[1];
            arraycopy(cardTicketContent, i, ChildCardType, 0, ChildCardType.length);
            String childCardType = (String) FileUtils.byte2Parm(ChildCardType, Type.HEX);
            i += ChildCardType.length;
            newPayRuleInfo.setChildCardType(childCardType);

            //号段数量，当该字段为 0 时，CardNoSection 不存在
            byte[] CardNoSectionNum = new byte[1];
            arraycopy(cardTicketContent, i, CardNoSectionNum, 0, CardNoSectionNum.length);
            int cardNoSectionNum = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(CardNoSectionNum, Type.HEX));
            i += CardNoSectionNum.length;
            newPayRuleInfo.setCardNoSectionNum(cardNoSectionNum);

            List<String> sardNoSections = new ArrayList<>();
            //10byte 起始卡号+10byte 终止卡号 有些时候会有虚拟卡种，从卡里面读出的卡类型一致， 但通过号段区分不同的票价，如果没有区分就 20 个字 节的全 F
            for (int j = 0; j < cardNoSectionNum; j++) {
                byte[] CardNoSection = new byte[20];
                arraycopy(cardTicketContent, i, CardNoSection, 0, CardNoSection.length);
                String cardNoSection = (String) FileUtils.byte2Parm(CardNoSection, Type.HEX);
                sardNoSections.add(cardNoSection);
                i += CardNoSection.length;
            }
            newPayRuleInfo.setSardNoSections(sardNoSections);

            //票值类型 0:储值类型 1:计次类型 2:定期类型 其他:预留
            byte[] ValueType = new byte[1];
            arraycopy(cardTicketContent, i, ValueType, 0, ValueType.length);
            String valueType = (String) FileUtils.byte2Parm(ValueType, Type.HEX);
            i += ValueType.length;
            newPayRuleInfo.setValueType(valueType);

            //卡片余额上限， 仅对ValueType为0和1时有效 ValueType 为 0 时，以分为单位 ValueType 为 1 时，以次为单位
            byte[] ValueUpperLimit = new byte[4];
            arraycopy(cardTicketContent, i, ValueUpperLimit, 0, ValueUpperLimit.length);
            String valueUpperLimit = (String) FileUtils.byte2Parm(ValueUpperLimit, Type.HEX);
            i += ValueUpperLimit.length;
            newPayRuleInfo.setValueUpperLimit(valueUpperLimit);

            //卡片余额下限， 仅对ValueType为0和1时有效 ValueType 为 0 时，以分为单位 ValueType 为 1 时，以次为单位
            byte[] ValueLowerLimit = new byte[4];
            arraycopy(cardTicketContent, i, ValueLowerLimit, 0, ValueLowerLimit.length);
            String valueLowerLimit = (String) FileUtils.byte2Parm(ValueLowerLimit, Type.HEX);
            i += ValueLowerLimit.length;
            newPayRuleInfo.setValueLowerLimit(valueLowerLimit);

            //有效期检查标志 0:不验证 1:验证
            byte[] ValidDateCheckFlag = new byte[1];
            arraycopy(cardTicketContent, i, ValidDateCheckFlag, 0, ValidDateCheckFlag.length);
            String validDateCheckFlag = (String) FileUtils.byte2Parm(ValidDateCheckFlag, Type.HEX);
            i += ValidDateCheckFlag.length;
            newPayRuleInfo.setValidDateCheckFlag(validDateCheckFlag);

            //0 表示按折扣优惠，
            //1 表示按固定票价优惠，后续固定票价单位为分/次(与
            byte[] DiscountType = new byte[1];
            arraycopy(cardTicketContent, i, DiscountType, 0, DiscountType.length);
            String discountType = (String) FileUtils.byte2Parm(DiscountType, Type.HEX);
            i += DiscountType.length;
            newPayRuleInfo.setDiscountType(discountType);

            //折扣的规则数量
            byte[] DiscountNum = new byte[1];
            arraycopy(cardTicketContent, i, DiscountNum, 0, DiscountNum.length);
            int discountNum = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(DiscountNum, Type.HEX));
            i += DiscountNum.length;
            newPayRuleInfo.setDiscountNum(discountNum);

            List<DiscountRule> discountRules = new ArrayList<>();
            for (int j = 0; j < discountNum; j++) {
                DiscountRule discountRule = new DiscountRule();

                byte[] StartTime = new byte[2];
                arraycopy(cardTicketContent, i, StartTime, 0, StartTime.length);
                String startTime = (String) FileUtils.byte2Parm(StartTime, Type.BCDB);
                i += StartTime.length;
                discountRule.setStartTime(startTime);

                byte[] EndTime = new byte[2];
                arraycopy(cardTicketContent, i, EndTime, 0, EndTime.length);
                String endTime = (String) FileUtils.byte2Parm(EndTime, Type.BCDB);
                i += EndTime.length;
                discountRule.setEndTime(endTime);

                byte[] Discount = new byte[2];
                arraycopy(cardTicketContent, i, Discount, 0, Discount.length);
                int discount = Integer.parseInt((String) FileUtils.byte2Parm(Discount, Type.BCDB));
                i += Discount.length;
                discountRule.setDiscount(discount);
                discountRules.add(discountRule);
            }
            newPayRuleInfo.setDiscountRules(discountRules);

            //同一张卡刷卡间隔时间，例如 00 为不判断间隔，05 则 为两次刷卡需间隔 5 分钟
            byte[] TransIntervalTime = new byte[1];
            arraycopy(cardTicketContent, i, TransIntervalTime, 0, TransIntervalTime.length);
            int transIntervalTime = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(TransIntervalTime, Type.HEX));
            i += TransIntervalTime.length;
            newPayRuleInfo.setTransIntervalTime(transIntervalTime);

            //语音播报
            //第 1byte 标志是否需要语音播报
            //0:不需语音播报
            //1:需要语音播报;
            //第 2~11byte 语音播报内容 中文语音播报，比如说需要播报“老年卡”，此处填老 年卡，由设备实现语音操作
            byte[] VoiceBroadcast = new byte[11];
            arraycopy(cardTicketContent, i, VoiceBroadcast, 0, VoiceBroadcast.length);
            String voiceBroadcast = (String) FileUtils.byte2Parm(VoiceBroadcast, Type.HEX);
            i += VoiceBroadcast.length;
            newPayRuleInfo.setVoiceBroadcast(voiceBroadcast);

            //特殊优惠规则:
            //第 1byte 表示优惠周期类型 00:无优惠周期，即该票种没有特殊优惠规则，后续字 节无意义01:日   02:月    03:年;
            byte[] SpecialDiscountRuleFlag = new byte[1];
            arraycopy(cardTicketContent, i, SpecialDiscountRuleFlag, 0, SpecialDiscountRuleFlag.length);
            String specialDiscountRuleFlag = (String) FileUtils.byte2Parm(SpecialDiscountRuleFlag, Type.BCDB);
            i += SpecialDiscountRuleFlag.length;
            newPayRuleInfo.setSpecialDiscountRuleFlag(specialDiscountRuleFlag);

            //第 2~3byte 表示周期内累计优惠次数上限;
            byte[] SpecialDiscountRuleCount = new byte[2];
            arraycopy(cardTicketContent, i, SpecialDiscountRuleCount, 0, SpecialDiscountRuleCount.length);
            String specialDiscountRuleCount = (String) FileUtils.byte2Parm(SpecialDiscountRuleCount, Type.BCDB);
            i += SpecialDiscountRuleCount.length;
            newPayRuleInfo.setSpecialDiscountRuleCount(specialDiscountRuleCount);

            //第 4byte 表示优惠类型:
            byte[] SpecialDiscountRuleType = new byte[1];
            arraycopy(cardTicketContent, i, SpecialDiscountRuleType, 0, SpecialDiscountRuleType.length);
            String specialDiscountRuleType = (String) FileUtils.byte2Parm(SpecialDiscountRuleType, Type.BCDB);
            i += SpecialDiscountRuleType.length;
            newPayRuleInfo.setSpecialDiscountRuleType(specialDiscountRuleType);

            //第5byte表示单日优惠规则个数；
            byte[] SpecialDiscountRuleNum = new byte[1];
            arraycopy(cardTicketContent, i, SpecialDiscountRuleNum, 0, SpecialDiscountRuleNum.length);
            int specialDiscountRuleNum = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(SpecialDiscountRuleNum, Type.BCDB));
            i += SpecialDiscountRuleNum.length;
            newPayRuleInfo.setSpecialDiscountRuleNum(specialDiscountRuleNum);

            List<SpecialDiscountRule> specialDiscountRules = new ArrayList<>();
            for (int j = 0; j < specialDiscountRuleNum; j++) {
                SpecialDiscountRule specialDiscountRule = new SpecialDiscountRule();
                //开始时间；
                byte[] SpecialDiscountRuleStartTime = new byte[2];
                arraycopy(cardTicketContent, i, SpecialDiscountRuleStartTime, 0, SpecialDiscountRuleStartTime.length);
                String specialDiscountRuleStartTime = (String) FileUtils.byte2Parm(SpecialDiscountRuleStartTime, Type.BCDB);
                i += SpecialDiscountRuleStartTime.length;
                specialDiscountRule.setStartTime(specialDiscountRuleStartTime);

                //结束时间；
                byte[] SpecialDiscountRuleEndTime = new byte[2];
                arraycopy(cardTicketContent, i, SpecialDiscountRuleEndTime, 0, SpecialDiscountRuleEndTime.length);
                String specialDiscountRuleEndTime = (String) FileUtils.byte2Parm(SpecialDiscountRuleEndTime, Type.BCDB);
                i += SpecialDiscountRuleEndTime.length;
                specialDiscountRule.setEndTime(specialDiscountRuleEndTime);

                //规则；
                byte[] SpecialDiscountRuleDiscount = new byte[2];
                arraycopy(cardTicketContent, i, SpecialDiscountRuleDiscount, 0, SpecialDiscountRuleDiscount.length);
                int specialDiscountRuleDiscount = Integer.parseInt((String) FileUtils.byte2Parm(SpecialDiscountRuleDiscount, Type.BCDB));
                i += SpecialDiscountRuleDiscount.length;
                specialDiscountRule.setDiscount(specialDiscountRuleDiscount);

                specialDiscountRules.add(specialDiscountRule);
            }
            newPayRuleInfo.setSpecialDiscountRule(new Gson().toJson(specialDiscountRules));


            // 0:支持换乘优惠
            //  1:不支持换乘优惠，无后续字段 ;
            byte[] ChangeDiscountType = new byte[1];
            arraycopy(cardTicketContent, i, ChangeDiscountType, 0, ChangeDiscountType.length);
            String changeDiscountType = (String) FileUtils.byte2Parm(ChangeDiscountType, Type.HEX);
            i += ChangeDiscountType.length;
            newPayRuleInfo.setChangeDiscountType(changeDiscountType);

            if (changeDiscountType.equals("00")) {
                //规定换乘时间，单位分;
                byte[] ChangeDiscountTime = new byte[2];
                arraycopy(cardTicketContent, i, ChangeDiscountTime, 0, ChangeDiscountTime.length);
                String changeDiscountTime = (String) FileUtils.byte2Parm(ChangeDiscountTime, Type.HEX);
                i += ChangeDiscountTime.length;
                newPayRuleInfo.setChangeDiscountTime(changeDiscountTime);

                //最大允许换乘次数 N;
                byte[] ChangeDiscountMAX = new byte[1];
                arraycopy(cardTicketContent, i, ChangeDiscountMAX, 0, ChangeDiscountMAX.length);
                int changeDiscountMAX = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(ChangeDiscountMAX, Type.HEX));
                i += ChangeDiscountMAX.length;
                newPayRuleInfo.setChangeDiscountMAX(changeDiscountMAX);

                //每次换乘最高优惠金额，金额以分为 单位;
                List<Integer> changeDiscountPays = new ArrayList<>();
                for (int j = 0; j < changeDiscountMAX; j++) {
                    //最大允许换乘次数 N;
                    byte[] ChangeDiscountPay = new byte[2];
                    arraycopy(cardTicketContent, i, ChangeDiscountPay, 0, ChangeDiscountPay.length);
                    int changeDiscountPay = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(ChangeDiscountPay, Type.HEX));
                    i += ChangeDiscountPay.length;
                    changeDiscountPays.add(changeDiscountPay);
                }
                newPayRuleInfo.setChangeDiscountPays(changeDiscountPays);
                Log.i("线路解析  计费规则", payRuleInfo.toString());
            }

            byte[] SpecialProcessRuleFlag = new byte[1];
            arraycopy(cardTicketContent, i, SpecialProcessRuleFlag, 0, SpecialProcessRuleFlag.length);
            String specialProcessRuleFlag = (String) FileUtils.byte2Parm(SpecialProcessRuleFlag, Type.HEX);
            i += SpecialProcessRuleFlag.length;
            newPayRuleInfo.setSpecialProcessRuleFlag(specialProcessRuleFlag);

            if (specialProcessRuleFlag.equals("01")) {
                checkPrase(i, cardTicketContent, null);
                //TODO 用于特殊线路规则解析 目前不定
            }

            byte[] Reserved = new byte[20];
            arraycopy(cardTicketContent, i, Reserved, 0, Reserved.length);
            String reserved = (String) FileUtils.byte2Parm(Reserved, Type.HEX);
            i += Reserved.length;
            newPayRuleInfo.setReserved(reserved);
            Log.i("线路解析  票价信息", "票价信息：" + payRuleInfo.toString());
            newPayRuleInfo.setTag(payRuleInfo.getTag());

            DBManager.upDatePayRule(newPayRuleInfo);
        }
        return payRuleInfo.start;
    }


    private static MoreTicketInfo praseMoreTicketContent(byte[] moreTicketContent, StationListInfo stationListInfo, MoreTicketInfo moreTicketInfos) {
        int i = 0;
        List<MoreTicketInfo.MoreTicetDetail> moreTicetDetails = new ArrayList<>();
        //获取站点信息区域的数据 单独进行解析
        for (int n = 0; n < stationListInfo.getStationNumUp(); n++) {
            for (int j = 0; j < stationListInfo.getStationNumUp(); j++) {
                MoreTicketInfo.MoreTicetDetail moreTicketInfo = new MoreTicketInfo.MoreTicetDetail();
                moreTicketInfo.setUp(n);
                moreTicketInfo.setDown(j);
                moreTicketInfo.setDiraction("0");
                byte[] moreTicketPrice = new byte[2];
                arraycopy(moreTicketContent, i, moreTicketPrice, 0, moreTicketPrice.length);
                moreTicketInfo.setPrice((String) FileUtils.byte2Parm(moreTicketPrice, Type.HEX));
                i += moreTicketPrice.length;
                moreTicetDetails.add(moreTicketInfo);
            }
        }

        //获取站点信息区域的数据 单独进行解析
        for (int n = 0; n < stationListInfo.getStationNumDn(); n++) {
            for (int j = 0; j < stationListInfo.getStationNumDn(); j++) {
                MoreTicketInfo.MoreTicetDetail moreTicketInfo = new MoreTicketInfo.MoreTicetDetail();
                moreTicketInfo.setUp(n);
                moreTicketInfo.setDown(j);
                moreTicketInfo.setDiraction("1");
                byte[] moreTicketPrice = new byte[2];
                arraycopy(moreTicketContent, i, moreTicketPrice, 0, moreTicketPrice.length);
                moreTicketInfo.setPrice((String) FileUtils.byte2Parm(moreTicketPrice, Type.HEX));
                i += moreTicketPrice.length;
                moreTicetDetails.add(moreTicketInfo);
            }
        }

        moreTicketInfos.setMoreTicetDetails(moreTicetDetails);
        return moreTicketInfos;
    }

    //解析单票制票价信息
    private static SingleTicktInfo praseSingleTicketContent(byte[] ticketContent, SingleTicktInfo singleTicktInfo) {
        int i = 0;

        //线路基本票价，单位为分
        byte[] PriceBasic = new byte[2];
        arraycopy(ticketContent, i, PriceBasic, 0, PriceBasic.length);
        i += PriceBasic.length;
        int priceBasic = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(PriceBasic, Type.HEX));
        singleTicktInfo.setPriceBasic(priceBasic);


        //支持连刷标志，1:支持连刷，其他:不支持连刷
        byte[] ContinuousSwipFlag = new byte[1];
        arraycopy(ticketContent, i, ContinuousSwipFlag, 0, ContinuousSwipFlag.length);
        i += ContinuousSwipFlag.length;
        String continuousSwipFlag = (String) FileUtils.byte2Parm(ContinuousSwipFlag, Type.HEX);
        singleTicktInfo.setContinuousSwipFlag(continuousSwipFlag);


        //步长金额，单位为分
        // 例:在支持连刷的情况下，基础票价为 1 元，
        // 步长金 额为2元时，乘客刷1次码票价1元，
        // 刷第2次码票 价增加2元，刷第3次码票价再增加2元，
        // 对3次刷码做合并连刷处理后，做一次 5 元交易上送。
        byte[] StepPrice = new byte[2];
        arraycopy(ticketContent, i, StepPrice, 0, StepPrice.length);
        i += StepPrice.length;
        int stepPrice = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(StepPrice, Type.HEX));
        singleTicktInfo.setStepPrice(stepPrice);
        return singleTicktInfo;
    }

    //解析详细的线路信息
    private static LineInfo praseLineContent(byte[] lineDateContent, LineInfo lineInfoEntity) {
        int i = 0;
        //线路编号
        byte[] LineID = new byte[6];
        arraycopy(lineDateContent, i, LineID, 0, LineID.length);
        i += LineID.length;
        String lineID = (String) FileUtils.byte2Parm(LineID, Type.BCDB);
        lineInfoEntity.setLineID(lineID);

        //线路名称，不足时末尾补字节 0
        byte[] LineName = new byte[32];
        arraycopy(lineDateContent, i, LineName, 0, LineName.length);
        i += LineName.length;
        String lineName = (String) FileUtils.byte2Parm(LineName, Type.LINECHAR);
        lineInfoEntity.setLineName(lineName);


        //运营方编码，用于二维码相关接口，用于标识二维码所属 运营方。
        byte[] OperatorCode = new byte[2];
        arraycopy(lineDateContent, i, OperatorCode, 0, OperatorCode.length);
        i += OperatorCode.length;
        String operatorCode = (String) FileUtils.byte2Parm(OperatorCode, Type.BCDB);
        lineInfoEntity.setOperatorCode(operatorCode);

        //收单方标识。
        byte[] Acquirer = new byte[4];
        arraycopy(lineDateContent, i, Acquirer, 0, Acquirer.length);
        i += Acquirer.length;
        String acquirer = (String) FileUtils.byte2Parm(Acquirer, Type.HEX);
        lineInfoEntity.setAcquirer(acquirer);

        //住建部定义城市代码，用于住建部卡片交易是否异地卡处 理。
        byte[] ZJBCityCode = new byte[2];
        arraycopy(lineDateContent, i, ZJBCityCode, 0, ZJBCityCode.length);
        i += ZJBCityCode.length;
        String zJBCityCode = (String) FileUtils.byte2Parm(ZJBCityCode, Type.BCDB);
        lineInfoEntity.setzJBCityCode(zJBCityCode);

        //交通部定义发卡机构代码，用于设备判断交通部刷卡交易 是否为异地卡交易
        byte[] JTBIssuerCode = new byte[4];
        arraycopy(lineDateContent, i, JTBIssuerCode, 0, JTBIssuerCode.length);
        i += JTBIssuerCode.length;
        String jTBIssuerCode = (String) FileUtils.byte2Parm(JTBIssuerCode, Type.BCDB);
        lineInfoEntity.setjTBIssuerCode(jTBIssuerCode);

        //交通部定义收单方机构代码，用于交通部卡交易上送接 口。
        byte[] JTBAcquirerCode = new byte[4];
        arraycopy(lineDateContent, i, JTBAcquirerCode, 0, JTBAcquirerCode.length);
        i += JTBAcquirerCode.length;
        String jTBAcquirerCode = (String) FileUtils.byte2Parm(JTBAcquirerCode, Type.BCDB);
        lineInfoEntity.setjTBAcquirerCode(jTBAcquirerCode);

        //分段计费标志 1:分段计费，分段计费票价信息存在并有效 0:不支持分段计费
        byte[] DistanceStepFlag = new byte[1];
        arraycopy(lineDateContent, i, DistanceStepFlag, 0, DistanceStepFlag.length);
        i += DistanceStepFlag.length;
        String distanceStepFlag = (String) FileUtils.byte2Parm(DistanceStepFlag, Type.HEX);
        lineInfoEntity.setDistanceStepFlag(distanceStepFlag);

        //环形线路标志，该字段用于判断分段计费时二维票价表的 有效数据域
        //1:环形，0:折返型
        byte[] CircleLineFlag = new byte[1];
        arraycopy(lineDateContent, i, CircleLineFlag, 0, CircleLineFlag.length);
        i += CircleLineFlag.length;
        String circleLineFlag = (String) FileUtils.byte2Parm(CircleLineFlag, Type.HEX);
        lineInfoEntity.setCircleLineFlag(circleLineFlag);

        //线路支持的支付方式，详见注 1，根据此字段值，定义多 个针对不同支付方式的计费规则。
        byte[] PaymentTypeFlag = new byte[4];
        arraycopy(lineDateContent, i, PaymentTypeFlag, 0, PaymentTypeFlag.length);
        i += PaymentTypeFlag.length;
        String paymentTypeFlag = (String) FileUtils.byte2Parm(PaymentTypeFlag, Type.HEX);
        lineInfoEntity.setPaymentTypeFlag(paymentTypeFlag);

        //渠道个数
        //当为 0 时，14 域 ChannelIDList 不存在，不需进行渠道编 码判断
        //当不为 0 时，14 域 ChannelIDList 存在，需对二维码验码 返回的渠道编码进行判断
        byte[] ChannelNum = new byte[1];
        arraycopy(lineDateContent, i, ChannelNum, 0, ChannelNum.length);
        i += ChannelNum.length;
        int channelNum = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(ChannelNum, Type.HEX));
        lineInfoEntity.setChannelNum(channelNum);

        //渠道编码列表
        List<String> ChannelIDList = new ArrayList<>();
        for (int j = 0; j < channelNum; j++) {
            byte[] ChannelID = new byte[2];
            arraycopy(lineDateContent, i, ChannelID, 0, ChannelID.length);
            i += ChannelID.length;
            String channelID = (String) FileUtils.byte2Parm(ChannelID, Type.HEX);
            ChannelIDList.add(channelID);
            Log.i("线路解析  线路信息", "渠道编码列表：" + channelID);
        }
        lineInfoEntity.setChannelIDList(new Gson().toJson(ChannelIDList));

        //线路支持的支付方式，详见注 1，根据此字段值，定义多 个针对不同支付方式的计费规则。
        byte[] Reserved = new byte[10];
        arraycopy(lineDateContent, i, Reserved, 0, Reserved.length);
        i += Reserved.length;
        String reserved = (String) FileUtils.byte2Parm(Reserved, Type.HEX);
        lineInfoEntity.setReserved(reserved);

        return lineInfoEntity;
    }

    //解析文件信息 包含版本号和生效日期
    private static LineFileInfo praseFileContent(byte[] fileDateContent, LineFileInfo lineFileInfo) {
        int i = 0;
        //文件版本号，与文件格式相关，初始版本为 0x01
        byte[] FileVerson = new byte[1];
        arraycopy(fileDateContent, i, FileVerson, 0, FileVerson.length);
        i += FileVerson.length;
        String fileVerson = (String) FileUtils.byte2Parm(FileVerson, Type.HEX);
        lineFileInfo.setFileVerson(fileVerson);

        //文件生效日期,YYYYMMDD
        byte[] DateEnable = new byte[4];
        arraycopy(fileDateContent, i, DateEnable, 0, DateEnable.length);
        i += DateEnable.length;
        String dateEnable = (String) FileUtils.byte2Parm(DateEnable, Type.BCDB);
        lineFileInfo.setDateEnable(dateEnable);

        Log.i("线路解析  文件信息", lineFileInfo.toString());
        return lineFileInfo;
    }

    //解析站点列表
    private static StationListInfo ParaseStaion(byte[] fileDate, StationListInfo stationListInfo) {
        int i = 0;
        // //上行方向(环线外环)站点数量 m
        byte[] StationNumUp = new byte[1];
        arraycopy(fileDate, i, StationNumUp, 0, StationNumUp.length);
        i += StationNumUp.length;
        int stationNumUp = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(StationNumUp, Type.HEX));
        stationListInfo.setStationNumDn(stationNumUp);

        //上行站点 按序排列的上行站点信息列表，站点信息结构见注 2
        List<StationInfoEntity> stationsList = new ArrayList<>();
        for (int j = 0; j < stationNumUp; j++) {
            byte[] StationDetailInfo = new byte[48];
            arraycopy(fileDate, i, StationDetailInfo, 0, StationDetailInfo.length);
            i += StationDetailInfo.length;
            stationsList.add(parseStaionDetailInfo(StationDetailInfo, "0"));
        }

        //下行方向(环线内环)站点数量 n
        byte[] StationNumDn = new byte[1];
        arraycopy(fileDate, i, StationNumDn, 0, StationNumDn.length);
        i += StationNumDn.length;
        int stationNumDn = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(StationNumDn, Type.HEX));
        stationListInfo.setStationNumDn(stationNumDn);
        //下行站点 按序排列的下行站点信息列表，站点信息结构见注 2
        for (int j = 0; j < stationNumDn; j++) {
            byte[] StationDetailInfo = new byte[48];
            arraycopy(fileDate, i, StationDetailInfo, 0, StationDetailInfo.length);
            i += StationDetailInfo.length;
            stationsList.add(parseStaionDetailInfo(StationDetailInfo, "1"));
        }
        stationListInfo.setStationsListUp(new Gson().toJson(stationsList));
        return stationListInfo;
    }

    //解析单个站点的信息
    private static StationInfoEntity parseStaionDetailInfo(byte[] stationContent, String diraction) {
        int i = 0;
        StationInfoEntity stationInfoEntity = new StationInfoEntity();
        //站点编号,作为站点索引
        byte[] StationID = new byte[1];
        arraycopy(stationContent, i, StationID, 0, StationID.length);
        i += StationID.length;
        String stationID = (String) FileUtils.byte2Parm(StationID, Type.HEX);
        stationInfoEntity.setStationID(stationID);


        //站点名称
        byte[] StationName = new byte[32];
        arraycopy(stationContent, i, StationName, 0, StationName.length);
        i += StationName.length;
        String stationName = ((String) FileUtils.byte2Parm(StationName, Type.LINECHAR)).replace("", "");
        stationInfoEntity.setStationName(stationName);

        //站点 GPS 信息，经度纬度各四个字节，经度在前，纬度 在后
        //经度和纬度转换成 hex 的规则
        //(1)不论哪种 GPS 坐标形式，统一转成度表示，比如: 108.891343° (2)四字节组成规则:小数点前面的整数为1个字节hex+ 小数点后面小数为 3 字节 hex
        byte[] StationLocation = new byte[8];
        arraycopy(stationContent, i, StationLocation, 0, StationLocation.length);
        i += StationLocation.length;
        String stationLocation = (String) FileUtils.byte2Parm(StationLocation, Type.HEX);
        stationInfoEntity.setLoaction(StationLocation);

        //站点半径
        byte[] StationRadius = new byte[2];
        arraycopy(stationContent, i, StationRadius, 0, StationRadius.length);
        i += StationRadius.length;
        int stationRadius = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(StationRadius, Type.HEX));
        stationInfoEntity.setStationRadius(stationRadius);

        //RFU
        byte[] Reserved = new byte[5];
        arraycopy(stationContent, i, Reserved, 0, Reserved.length);
        i += Reserved.length;
        String reserved = (String) FileUtils.byte2Parm(Reserved, Type.HEX);
        stationInfoEntity.setReserved(reserved);

        stationInfoEntity.setDiraction(diraction);

        return stationInfoEntity;
    }

    /**
     * @param i        确定数据区域的起点位置
     * @param fileDate 整个数据区域的所有数据
     * @param o        不定时需要的参数 扩展
     * @return 解析完成的数据对象
     */
    private static int checkPrase(int i, byte[] fileDate, Object o) {
        try {
            //线路信息数据段 Tag:XL，ASCII 码
            byte[] Tag = new byte[2];
            arraycopy(fileDate, i, Tag, 0, Tag.length);
            i += Tag.length;
            String tag = (String) FileUtils.byte2Parm(Tag, Type.ASCLL);

            //线路信息数据段 Tag:XL，ASCII 码
            byte[] Len = new byte[2];
            arraycopy(fileDate, i, Len, 0, Len.length);
            i += Len.length;
            int len = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(Len, Type.HEX));

            byte[] content = new byte[len];
            arraycopy(fileDate, i, content, 0, content.length);
            i += content.length;

            Log.i("线路解析", "tag：" + tag + "        len=" + len);

            switch (tag) {
                case "WJ"://获取文件信息区域的数据 单独进行解析
                    LineFileInfo lineFileInfo = new LineFileInfo(i, tag, len);
                    praseFileContent(content, lineFileInfo);
                    Log.i("线路解析", "线路文件：" + lineFileInfo.toString());
                    return lineFileInfo.start;
                case "XL"://获取线路信息区域的数据 单独进行解析
                    LineInfo lineInfo = new LineInfo(i, tag, len);
                    praseLineContent(content, lineInfo);
                    DBManager.insertLineInfo(lineInfo);
                    Log.i("线路解析", "线路信息：" + lineInfo.toString());
                    return lineInfo.start;
                case "ZD"://获取站点信息区域的数据 单独进行解析
                    StationListInfo stationListInfo = new StationListInfo(i, tag, len);
                    ParaseStaion(content, stationListInfo);
                    Log.i("线路解析", "站点信息：" + stationListInfo.toString());
                    return stationListInfo.start;
                case "PJ"://获取基础票价信息区域的数据 单独进行解析
                    SingleTicktInfo singleTicktInfo = new SingleTicktInfo(i, tag, len);
                    praseSingleTicketContent(content, singleTicktInfo);
                    DBManager.insertSinglePrice(singleTicktInfo);
                    Log.i("线路解析", "票价信息：" + singleTicktInfo.toString());
                    return singleTicktInfo.start;
                case "FD"://分段计费票价信息
                    MoreTicketInfo moreTicketInfo = new MoreTicketInfo(i, tag, len);
                    praseMoreTicketContent(content, (StationListInfo) o, moreTicketInfo);
                    Log.i("线路解析", "分段票价信息：" + moreTicketInfo.toString());
                    return moreTicketInfo.start;
                case "PK"://分段计费票价信息
                case "ZM"://自有二维码计费规则信息数据段 Tag:ZM，ASCII 码
                case "TX"://腾讯乘车码计费规则信息数据段 Tag:TX，ASCII 码
                case "AL"://支付宝乘车码计费规则信息数据段 Tag:AL，ASCII 码
                case "YL"://银联计费规则信息数据段 Tag:YL，ASCII 码
                case "JT"://交通部二维码计费规则信息数据段 Tag:JT，ASCII 码
                case "RC"://RCC-SIM 卡计费规则信息数据段 Tag:RC，ASCII 码
                    PayRuleInfo payRuleInfo = new PayRuleInfo(i, tag, len);
                    return prasePayTicketContent(content, payRuleInfo);
                default:
                    return i;
            }
        } catch (Exception e) {
            Log.i("线路解析", "解析异常" + e.toString());
            return i;
        }
    }


}
