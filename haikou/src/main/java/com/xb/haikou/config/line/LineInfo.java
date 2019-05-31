package com.xb.haikou.config.line;

import com.xb.haikou.config.AppRunConfigEntity;
import com.xb.haikou.db.manage.DBManager;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class LineInfo {
    @Id(autoincrement = true)
    Long id;
    int start;
    String tag;
    int len;
    @Unique
    String lineID;//线路编号
    String lineName;//线路名称，不足时末尾补字节 0
    String operatorCode;//运营方编码，用于二维码相关接口，用于标识二维码所属 运营方。
    String acquirer;//收单方标识
    String zJBCityCode;//住建部定义城市代码，用于住建部卡片交易是否异地卡处 理
    String jTBIssuerCode;//交通部定义发卡机构代码，用于设备判断交通部刷卡交易 是否为异地卡交易
    String jTBAcquirerCode;//交通部定义收单方机构代码，用于交通部卡交易上送接 口。
    String distanceStepFlag;//分段计费标志 1:分段计费，分段计费票价信息存在并有效 0:不支持分段计费
    String circleLineFlag;//环形线路标志，该字段用于判断分段计费时二维票价表的 有效数据域1:环形，0:折返型
    String paymentTypeFlag;//线路支持的支付方式，详见注 1，根据此字段值，定义多个针对不同支付方式的计费规则。
    int channelNum;//渠道个数 当为 0 时，14 域 ChannelIDList 不存在，不需进行渠道编 码判断 当不为 0 时，14 域 ChannelIDList 存在，需对二维码验码 返回的渠道编码进行判断
    String ChannelIDList;//渠道编码列表
    String reserved;//RFU


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public LineInfo(int start, String tag, int len) {
        this.start = start;
        this.tag = tag;
        this.len = len;
    }

    @Generated(hash = 1212508534)
    public LineInfo(Long id, int start, String tag, int len, String lineID, String lineName, String operatorCode,
                    String acquirer, String zJBCityCode, String jTBIssuerCode, String jTBAcquirerCode, String distanceStepFlag,
                    String circleLineFlag, String paymentTypeFlag, int channelNum, String ChannelIDList, String reserved) {
        this.id = id;
        this.start = start;
        this.tag = tag;
        this.len = len;
        this.lineID = lineID;
        this.lineName = lineName;
        this.operatorCode = operatorCode;
        this.acquirer = acquirer;
        this.zJBCityCode = zJBCityCode;
        this.jTBIssuerCode = jTBIssuerCode;
        this.jTBAcquirerCode = jTBAcquirerCode;
        this.distanceStepFlag = distanceStepFlag;
        this.circleLineFlag = circleLineFlag;
        this.paymentTypeFlag = paymentTypeFlag;
        this.channelNum = channelNum;
        this.ChannelIDList = ChannelIDList;
        this.reserved = reserved;
    }

    @Generated(hash = 829472146)
    public LineInfo() {
    }


    public String getLineID() {
        return lineID;
    }

    public void setLineID(String lineID) {
        this.lineID = lineID;
        AppRunConfigEntity appRunConfigEntity = DBManager.checkRunConfig();
        appRunConfigEntity.setLineNo(lineID);
        DBManager.updateConfig(appRunConfigEntity);
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
        AppRunConfigEntity appRunConfigEntity = DBManager.checkRunConfig();
        appRunConfigEntity.setLineName(lineName);
        DBManager.updateConfig(appRunConfigEntity);
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getAcquirer() {
        return acquirer;
    }

    public void setAcquirer(String acquirer) {
        this.acquirer = acquirer;
    }

    public String getzJBCityCode() {
        return zJBCityCode;
    }

    public void setzJBCityCode(String zJBCityCode) {
        this.zJBCityCode = zJBCityCode;
    }

    public String getjTBIssuerCode() {
        return jTBIssuerCode;
    }

    public void setjTBIssuerCode(String jTBIssuerCode) {
        this.jTBIssuerCode = jTBIssuerCode;
    }

    public String getjTBAcquirerCode() {
        return jTBAcquirerCode;
    }

    public void setjTBAcquirerCode(String jTBAcquirerCode) {
        this.jTBAcquirerCode = jTBAcquirerCode;
    }

    public String getDistanceStepFlag() {
        return distanceStepFlag;
    }

    public void setDistanceStepFlag(String distanceStepFlag) {
        this.distanceStepFlag = distanceStepFlag;
    }

    public String getCircleLineFlag() {
        return circleLineFlag;
    }

    public void setCircleLineFlag(String circleLineFlag) {
        this.circleLineFlag = circleLineFlag;
    }

    public String getPaymentTypeFlag() {
        return paymentTypeFlag;
    }

    public void setPaymentTypeFlag(String paymentTypeFlag) {
        this.paymentTypeFlag = paymentTypeFlag;
    }

    public int getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(int channelNum) {
        this.channelNum = channelNum;
    }

    public String getChannelIDList() {
        return ChannelIDList;
    }

    public void setChannelIDList(String channelIDList) {
        ChannelIDList = channelIDList;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "LineInfo{" +
                "lineID='" + lineID + '\'' +
                ", lineName='" + lineName + '\'' +
                ", operatorCode='" + operatorCode + '\'' +
                ", acquirer='" + acquirer + '\'' +
                ", zJBCityCode='" + zJBCityCode + '\'' +
                ", jTBIssuerCode='" + jTBIssuerCode + '\'' +
                ", jTBAcquirerCode='" + jTBAcquirerCode + '\'' +
                ", distanceStepFlag='" + distanceStepFlag + '\'' +
                ", circleLineFlag='" + circleLineFlag + '\'' +
                ", paymentTypeFlag='" + paymentTypeFlag + '\'' +
                ", channelNum=" + channelNum +
                ", ChannelIDList='" + ChannelIDList + '\'' +
                ", reserved='" + reserved + '\'' +
                ", start=" + start +
                ", tag='" + tag + '\'' +
                ", len=" + len +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZJBCityCode() {
        return this.zJBCityCode;
    }

    public void setZJBCityCode(String zJBCityCode) {
        this.zJBCityCode = zJBCityCode;
    }

    public String getJTBIssuerCode() {
        return this.jTBIssuerCode;
    }

    public void setJTBIssuerCode(String jTBIssuerCode) {
        this.jTBIssuerCode = jTBIssuerCode;
    }

    public String getJTBAcquirerCode() {
        return this.jTBAcquirerCode;
    }

    public void setJTBAcquirerCode(String jTBAcquirerCode) {
        this.jTBAcquirerCode = jTBAcquirerCode;
    }

}
