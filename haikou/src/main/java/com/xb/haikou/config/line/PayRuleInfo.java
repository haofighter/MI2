package com.xb.haikou.config.line;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

@Entity
public class PayRuleInfo {

    @Id(autoincrement = true)
    Long id;
    int start;
    String tag;
    int len;


    public PayRuleInfo(int start, String tag, int len) {
        this.start = start;
        this.tag = tag;
        this.len = len;
    }

    @Generated(hash = 2019616785)
    public PayRuleInfo(Long id, int start, String tag, int len, int cardTypeNum, String cardType, String childCardType, int cardNoSectionNum, String sardNoSections, String valueType, String valueUpperLimit, String valueLowerLimit,
            String validDateCheckFlag, String discountType, int discountNum, String discountRules, int transIntervalTime, String voiceBroadcast, String specialDiscountRule, String changeDiscountType, String changeDiscountTime,
            int changeDiscountMAX, String changeDiscountPays, int changeDiscountWhiteNum, String changeDiscountWhites, String specialProcessRuleFlag, String specialDiscountRuleFlag, String specialDiscountRuleCount,
            String specialDiscountRuleType, int specialDiscountRuleNum, String reserved) {
        this.id = id;
        this.start = start;
        this.tag = tag;
        this.len = len;
        this.cardTypeNum = cardTypeNum;
        this.cardType = cardType;
        this.childCardType = childCardType;
        this.cardNoSectionNum = cardNoSectionNum;
        this.sardNoSections = sardNoSections;
        this.valueType = valueType;
        this.valueUpperLimit = valueUpperLimit;
        this.valueLowerLimit = valueLowerLimit;
        this.validDateCheckFlag = validDateCheckFlag;
        this.discountType = discountType;
        this.discountNum = discountNum;
        this.discountRules = discountRules;
        this.transIntervalTime = transIntervalTime;
        this.voiceBroadcast = voiceBroadcast;
        this.specialDiscountRule = specialDiscountRule;
        this.changeDiscountType = changeDiscountType;
        this.changeDiscountTime = changeDiscountTime;
        this.changeDiscountMAX = changeDiscountMAX;
        this.changeDiscountPays = changeDiscountPays;
        this.changeDiscountWhiteNum = changeDiscountWhiteNum;
        this.changeDiscountWhites = changeDiscountWhites;
        this.specialProcessRuleFlag = specialProcessRuleFlag;
        this.specialDiscountRuleFlag = specialDiscountRuleFlag;
        this.specialDiscountRuleCount = specialDiscountRuleCount;
        this.specialDiscountRuleType = specialDiscountRuleType;
        this.specialDiscountRuleNum = specialDiscountRuleNum;
        this.reserved = reserved;
    }

    @Generated(hash = 1825032555)
    public PayRuleInfo() {
    }


    int cardTypeNum;//卡类型数量
    String cardType;//高字节代表主类型，低字节代表子类型
    String childCardType;//高字节代表主类型，低字节代表子类型
    int cardNoSectionNum;//号段数量，当该字段为 0 时，CardNoSection 不存在
    String sardNoSections;////10byte 起始卡号+10byte 终止卡号 有些时候会有虚拟卡种，从卡里面读出的卡类型一致， 但通过号段区分不同的票价，如果没有区分就 20 个字 节的全 F
    String valueType; //票值类型 0:储值类型 1:计次类型 2:定期类型 其他:预留
    String valueUpperLimit; //卡片余额上限
    String valueLowerLimit; //卡片余额下限
    String validDateCheckFlag; //有效期检查标志 0:不验证 1:验证
    String discountType;//0 表示按折扣优惠，1 表示按固定票价优惠，后续固定票价单位为分/次(与
    int discountNum;//折扣的规则数量
    String discountRules;//List<DiscountRule>转换的json字符串   折扣的规则 00 01 0700 0900 0050，第一字节为 00，表示优惠 方式为折扣优惠，第二字节表示 1 个优惠规则，后续字 节表示 7:00-9:00 执行 50%折扣，01 0700 0900 0200，第 一字节为 01，表示优惠方式为固定票价优惠，第二字节 表示 1 个优惠规则，后续字节表示 7:00-9:00 该票种执行 2 元票价
    int transIntervalTime; //同一张卡刷卡间隔时间，例如 00 为不判断间隔，05 则 为两次刷卡需间隔 5 分钟
    String voiceBroadcast; //语音播报
    //第 1byte 标志是否需要语音播报
    //0:不需语音播报
    //1:需要语音播报;
    //第 2~11byte 语音播报内容 中文语音播报，比如说需要播报“老年卡”，此处填老 年卡，由设备实现语音操作

    String specialDiscountRule;//    //特殊优惠规则:第 1byte 表示优惠周期类型 00:无优惠周期，即该票种没有特殊优惠规则，后续字 节无意义
    //01:日
    //02:月
    //03:年;
    //第 2~3byte 表示周期内累计优惠次数上限;
    //第 4byte 表示优惠类型:
    //0 表示按折扣优惠，
    //1 表示按固定收取票价优惠;
    //2 表示按固定优惠金额优惠;
    //第 5~6byte 表示优惠值，折扣或金额;
    //例如02 0060 00 0025表示一月内累计享受优惠上限为 60 次，每次优惠折扣为二五折


    String changeDiscountType;// 0:支持换乘优惠  1:不支持换乘优惠，无后续字段 ;
    String changeDiscountTime;//规定换乘时间，单位分;
    int changeDiscountMAX; //最大允许换乘次数 N;
    String changeDiscountPays;//List<Integer>转json    每次换乘最高优惠金额，金额以分为 单位;
    int changeDiscountWhiteNum;//换乘线路白名单数量
    String changeDiscountWhites;//List<Integer>  换乘线路白名单列表
    String specialProcessRuleFlag; //特殊处理规则存在标志 1:存在特殊处理规则 其他值:不存在特殊处理规则 当该字段不为 1 时，14 字段域不存在;
    String specialDiscountRuleFlag; //特殊优惠规则：优惠周期类型 00：无优惠周期，即该票种没有特殊优惠规则，后续字节无意义   01：日  02：月  03：年；
    String specialDiscountRuleCount; //特殊优惠规则：周期内累计优惠次数上限
    String specialDiscountRuleType; //特殊优惠规则：优惠类型 0表示按折扣优惠， 1表示按固定收取票价优惠； 2表示按固定优惠金额优惠；
    int specialDiscountRuleNum; //特殊优惠规则：单日优惠规则个数
    String reserved; //预留

    public String getChildCardType() {
        return childCardType;
    }

    public void setChildCardType(String childCardType) {
        this.childCardType = childCardType;
    }

    public String getSpecialDiscountRuleFlag() {
        return specialDiscountRuleFlag;
    }

    public void setSpecialDiscountRuleFlag(String specialDiscountRuleFlag) {
        this.specialDiscountRuleFlag = specialDiscountRuleFlag;
    }

    public String getSpecialDiscountRuleCount() {
        return specialDiscountRuleCount;
    }

    public void setSpecialDiscountRuleCount(String specialDiscountRuleCount) {
        this.specialDiscountRuleCount = specialDiscountRuleCount;
    }

    public String getSpecialDiscountRuleType() {
        return specialDiscountRuleType;
    }

    public void setSpecialDiscountRuleType(String specialDiscountRuleType) {
        this.specialDiscountRuleType = specialDiscountRuleType;
    }

    public int getSpecialDiscountRuleNum() {
        return specialDiscountRuleNum;
    }

    public void setSpecialDiscountRuleNum(int specialDiscountRuleNum) {
        this.specialDiscountRuleNum = specialDiscountRuleNum;
    }

    public List<DiscountRule> getDiscountRule() {
        return new Gson().fromJson(discountRules, new TypeToken<List<DiscountRule>>() {
        }.getType());
    }

    public String getDiscountRules() {
        return discountRules;
    }

    public void setDiscountRules(List<DiscountRule> discountRules) {
        this.discountRules = new Gson().toJson(discountRules);
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getCardNoSectionNum() {
        return cardNoSectionNum;
    }

    public void setCardNoSectionNum(int cardNoSectionNum) {
        this.cardNoSectionNum = cardNoSectionNum;
    }

    public String getSardNoSections() {
        return sardNoSections;
    }

    public List<String> getSardNoSection() {
        return new Gson().fromJson(sardNoSections, new TypeToken<List<String>>() {
        }.getType());
    }

    public void setSardNoSections(List<String> sardNoSections) {
        this.sardNoSections = new Gson().toJson(sardNoSections);
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValueUpperLimit() {
        return valueUpperLimit;
    }

    public void setValueUpperLimit(String valueUpperLimit) {
        this.valueUpperLimit = valueUpperLimit;
    }

    public String getValueLowerLimit() {
        return valueLowerLimit;
    }

    public void setValueLowerLimit(String valueLowerLimit) {
        this.valueLowerLimit = valueLowerLimit;
    }

    public String getValidDateCheckFlag() {
        return validDateCheckFlag;
    }

    public void setValidDateCheckFlag(String validDateCheckFlag) {
        this.validDateCheckFlag = validDateCheckFlag;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public int getDiscountNum() {
        return discountNum;
    }

    public void setDiscountNum(int discountNuml) {
        this.discountNum = discountNuml;
    }


    public int getTransIntervalTime() {
        return transIntervalTime;
    }

    public void setTransIntervalTime(int transIntervalTime) {
        this.transIntervalTime = transIntervalTime;
    }

    public String getVoiceBroadcast() {
        return voiceBroadcast;
    }

    public void setVoiceBroadcast(String voiceBroadcast) {
        this.voiceBroadcast = voiceBroadcast;
    }

    public String getSpecialDiscountRule() {
        return specialDiscountRule;
    }

    public void setSpecialDiscountRule(String specialDiscountRule) {
        this.specialDiscountRule = specialDiscountRule;
    }

    public List<SpecialDiscountRule> getSpecialDiscountRuleAll() {
        return new Gson().fromJson(specialDiscountRule, new TypeToken<List<SpecialDiscountRule>>() {
        }.getType());
    }

    public String getChangeDiscountType() {
        return changeDiscountType;
    }

    public void setChangeDiscountType(String changeDiscountType) {
        this.changeDiscountType = changeDiscountType;
    }

    public String getChangeDiscountTime() {
        return changeDiscountTime;
    }

    public void setChangeDiscountTime(String changeDiscountTime) {
        this.changeDiscountTime = changeDiscountTime;
    }

    public int getChangeDiscountMAX() {
        return changeDiscountMAX;
    }

    public void setChangeDiscountMAX(int changeDiscountMAX) {
        this.changeDiscountMAX = changeDiscountMAX;
    }

    public String getChangeDiscountPays() {
        return changeDiscountPays;
    }

    public List<Integer> getChangeDiscountPay() {
        return new Gson().fromJson(changeDiscountPays, new TypeToken<List<Integer>>() {
        }.getType());
    }

    public void setChangeDiscountPays(List<Integer> changeDiscountPays) {
        this.changeDiscountPays = new Gson().toJson(changeDiscountPays);
    }

    public int getChangeDiscountWhiteNum() {
        return changeDiscountWhiteNum;
    }

    public void setChangeDiscountWhiteNum(int changeDiscountWhiteNum) {
        this.changeDiscountWhiteNum = changeDiscountWhiteNum;
    }

    public String getChangeDiscountWhites() {
        return changeDiscountWhites;
    }

    public List<String> getChangeDiscountWhite() {
        return new Gson().fromJson(changeDiscountWhites, new TypeToken<List<String>>() {
        }.getType());
    }

    public void setChangeDiscountWhites(List<String> changeDiscountWhites) {
        this.changeDiscountWhites = new Gson().toJson(changeDiscountWhites);
    }

    public String getSpecialProcessRuleFlag() {
        return specialProcessRuleFlag;
    }

    public void setSpecialProcessRuleFlag(String specialProcessRuleFlag) {
        this.specialProcessRuleFlag = specialProcessRuleFlag;
    }


    public void setSardNoSections(String sardNoSections) {
        this.sardNoSections = sardNoSections;
    }

    public void setDiscountRules(String discountRules) {
        this.discountRules = discountRules;
    }

    public void setChangeDiscountPays(String changeDiscountPays) {
        this.changeDiscountPays = changeDiscountPays;
    }

    public void setChangeDiscountWhites(String changeDiscountWhites) {
        this.changeDiscountWhites = changeDiscountWhites;
    }


    public int getCardTypeNum() {
        return this.cardTypeNum;
    }


    public void setCardTypeNum(int cardTypeNum) {
        this.cardTypeNum = cardTypeNum;
    }

    @Override
    public String toString() {
        return " cardTypeNum=" + cardTypeNum +
                ", cardType='" + cardType + '\'' +
                ", cardNoSectionNum=" + cardNoSectionNum +
                ", sardNoSections='" + sardNoSections + '\'' +
                ", valueType='" + valueType + '\'' +
                ", valueUpperLimit='" + valueUpperLimit + '\'' +
                ", valueLowerLimit='" + valueLowerLimit + '\'' +
                ", validDateCheckFlag='" + validDateCheckFlag + '\'' +
                ", discountType='" + discountType + '\'' +
                ", discountNum=" + discountNum +
                ", discountRules='" + discountRules + '\'' +
                ", transIntervalTime=" + transIntervalTime +
                ", voiceBroadcast='" + voiceBroadcast + '\'' +
                ", specialDiscountRule='" + specialDiscountRule + '\'' +
                ", changeDiscountType='" + changeDiscountType + '\'' +
                ", changeDiscountTime='" + changeDiscountTime + '\'' +
                ", changeDiscountMAX=" + changeDiscountMAX +
                ", changeDiscountPays='" + changeDiscountPays + '\'' +
                ", changeDiscountWhiteNum=" + changeDiscountWhiteNum +
                ", changeDiscountWhites='" + changeDiscountWhites + '\'' +
                ", specialProcessRuleFlag='" + specialProcessRuleFlag + '\'' +
                ", specialDiscountRuleFlag='" + specialDiscountRuleFlag + '\'' +
                ", specialDiscountRuleCount='" + specialDiscountRuleCount + '\'' +
                ", specialDiscountRuleType='" + specialDiscountRuleType + '\'' +
                ", specialDiscountRuleNum=" + specialDiscountRuleNum;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLen() {
        return this.len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReserved() {
        return this.reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
}
