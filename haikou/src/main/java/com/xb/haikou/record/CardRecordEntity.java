package com.xb.haikou.record;

import android.util.Log;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class CardRecordEntity {
    @Id(autoincrement = true)
    Long id;
    String bizType;//10 一卡通默认
    String status;//10 一卡通默认
    String cardType;//卡类型
    String tradeType;//交易类型
    String psamTradeCount;//PSAM 序列号
    String cardNo;//卡号
    String cardAmt;//交易后余额
    int fareAmt;//交易金额
    String tradeTime;//交易时间(yyyy-MM-dd HH:mm:ss)
    String cardTradeCount;//用户卡内交易序号
    String tACCode;//交易 TAC 码
    String companyNo;//公司号
    String lineNo;//线路号
    String busNo;//车号
    String driverNo;//司机号
    String pasmNumber;//PSAM 卡号
    String direction;//行驶方向
    String currenStation;//当前站点号
    String ticketMore;//是否补票
    String currentLineNo;//当前线路号
    String currentBusNo;//当前汽车号
    String currentDriverNo;//当前司机号
    String backup;//备用
    String termid;//终端编号
    String termseq;// 终端流水号
    String mchid;// 商户号
    String keytype;// 密钥类型
    String citycode;// 城市代码
    String internalcode;// 行业代码
    String keyver;// 密钥版本号
    String keyindex;// 密钥索引
    String cardissuercitycode;// 卡所属地城市代码(卡片中读取）
    String cardmaintype;// 主卡类型
    String lasttranstype;// 上一笔交易的交易类型
    String lastterminalid;// 前次PSAM终端号
    String lasttranstime;// 前次交易时间
    String lastpayfee;// 前次交易金额
    String lastcardtransseqid;// 上一笔交易卡片计数器
    String cardversion;//应用类型，前4位表示交易特性:F-正常交易，后四位表示业务类型:D-灰色交易F-测试交易，D-有效交易
    String acquirer;//交易收单方标识
    String cardIssuerId;//发卡机构标识
    String transStatus;//交易状态00-正常消费，01-进站交易，02-出站交易
    String totalFee;//交易总金额
    String currency;//货币类型
    String uid;//uid
    String isUploade;//0 未上传 1 已上传
    Long time;//保存的时间
    String mrchid;//商户id
    String applytype;
    String conductorId;
    String transKeyVersion;
    String cardVersion;
    String transSeqId;
    String cardissuercitycode2;
    String algTag;
    String batchNo;
    String cardCsn;
    String psamTerminalId;
    String isContinuous;//是否连刷  0 不是  1是


    @Generated(hash = 583140105)
    public CardRecordEntity(Long id, String bizType, String status, String cardType, String tradeType,
            String psamTradeCount, String cardNo, String cardAmt, int fareAmt, String tradeTime,
            String cardTradeCount, String tACCode, String companyNo, String lineNo, String busNo,
            String driverNo, String pasmNumber, String direction, String currenStation,
            String ticketMore, String currentLineNo, String currentBusNo, String currentDriverNo,
            String backup, String termid, String termseq, String mchid, String keytype, String citycode,
            String internalcode, String keyver, String keyindex, String cardissuercitycode,
            String cardmaintype, String lasttranstype, String lastterminalid, String lasttranstime,
            String lastpayfee, String lastcardtransseqid, String cardversion, String acquirer,
            String cardIssuerId, String transStatus, String totalFee, String currency, String uid,
            String isUploade, Long time, String mrchid, String applytype, String conductorId,
            String transKeyVersion, String cardVersion, String transSeqId, String cardissuercitycode2,
            String algTag, String batchNo, String cardCsn, String psamTerminalId, String isContinuous) {
        this.id = id;
        this.bizType = bizType;
        this.status = status;
        this.cardType = cardType;
        this.tradeType = tradeType;
        this.psamTradeCount = psamTradeCount;
        this.cardNo = cardNo;
        this.cardAmt = cardAmt;
        this.fareAmt = fareAmt;
        this.tradeTime = tradeTime;
        this.cardTradeCount = cardTradeCount;
        this.tACCode = tACCode;
        this.companyNo = companyNo;
        this.lineNo = lineNo;
        this.busNo = busNo;
        this.driverNo = driverNo;
        this.pasmNumber = pasmNumber;
        this.direction = direction;
        this.currenStation = currenStation;
        this.ticketMore = ticketMore;
        this.currentLineNo = currentLineNo;
        this.currentBusNo = currentBusNo;
        this.currentDriverNo = currentDriverNo;
        this.backup = backup;
        this.termid = termid;
        this.termseq = termseq;
        this.mchid = mchid;
        this.keytype = keytype;
        this.citycode = citycode;
        this.internalcode = internalcode;
        this.keyver = keyver;
        this.keyindex = keyindex;
        this.cardissuercitycode = cardissuercitycode;
        this.cardmaintype = cardmaintype;
        this.lasttranstype = lasttranstype;
        this.lastterminalid = lastterminalid;
        this.lasttranstime = lasttranstime;
        this.lastpayfee = lastpayfee;
        this.lastcardtransseqid = lastcardtransseqid;
        this.cardversion = cardversion;
        this.acquirer = acquirer;
        this.cardIssuerId = cardIssuerId;
        this.transStatus = transStatus;
        this.totalFee = totalFee;
        this.currency = currency;
        this.uid = uid;
        this.isUploade = isUploade;
        this.time = time;
        this.mrchid = mrchid;
        this.applytype = applytype;
        this.conductorId = conductorId;
        this.transKeyVersion = transKeyVersion;
        this.cardVersion = cardVersion;
        this.transSeqId = transSeqId;
        this.cardissuercitycode2 = cardissuercitycode2;
        this.algTag = algTag;
        this.batchNo = batchNo;
        this.cardCsn = cardCsn;
        this.psamTerminalId = psamTerminalId;
        this.isContinuous = isContinuous;
    }

    @Generated(hash = 930673595)
    public CardRecordEntity() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPsamTradeCount() {
        return this.psamTradeCount;
    }

    public void setPsamTradeCount(String psamTradeCount) {
        this.psamTradeCount = psamTradeCount;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardAmt() {
        return this.cardAmt;
    }

    public void setCardAmt(String cardAmt) {
        this.cardAmt = cardAmt;
    }

    public int getFareAmt() {
        return this.fareAmt;
    }

    public void setFareAmt(int fareAmt) {
        this.fareAmt = fareAmt;
    }

    public String getTradeTime() {
        return this.tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getCardTradeCount() {
        return this.cardTradeCount;
    }

    public void setCardTradeCount(String cardTradeCount) {
        this.cardTradeCount = cardTradeCount;
    }

    public String getTACCode() {
        return this.tACCode;
    }

    public void setTACCode(String tACCode) {
        this.tACCode = tACCode;
    }

    public String getCompanyNo() {
        return this.companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getLineNo() {
        return this.lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getBusNo() {
        return this.busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getDriverNo() {
        return this.driverNo;
    }

    public void setDriverNo(String driverNo) {
        this.driverNo = driverNo;
    }

    public String getPasmNumber() {
        return this.pasmNumber;
    }

    public void setPasmNumber(String pasmNumber) {
        this.pasmNumber = pasmNumber;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCurrenStation() {
        return this.currenStation;
    }

    public void setCurrenStation(String currenStation) {
        this.currenStation = currenStation;
    }

    public String getTicketMore() {
        return this.ticketMore;
    }

    public void setTicketMore(String ticketMore) {
        this.ticketMore = ticketMore;
    }

    public String getCurrentLineNo() {
        return this.currentLineNo;
    }

    public void setCurrentLineNo(String currentLineNo) {
        this.currentLineNo = currentLineNo;
    }

    public String getCurrentBusNo() {
        return this.currentBusNo;
    }

    public void setCurrentBusNo(String currentBusNo) {
        this.currentBusNo = currentBusNo;
    }

    public String getCurrentDriverNo() {
        return this.currentDriverNo;
    }

    public void setCurrentDriverNo(String currentDriverNo) {
        this.currentDriverNo = currentDriverNo;
    }

    public String getBackup() {
        return this.backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getTermid() {
        return this.termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public String getTermseq() {
        return this.termseq;
    }

    public void setTermseq(String termseq) {
        this.termseq = termseq;
    }

    public String getMchid() {
        return this.mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getKeytype() {
        return this.keytype;
    }

    public void setKeytype(String keytype) {
        this.keytype = keytype;
    }

    public String getCitycode() {
        return this.citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getInternalcode() {
        return this.internalcode;
    }

    public void setInternalcode(String internalcode) {
        this.internalcode = internalcode;
    }

    public String getKeyver() {
        return this.keyver;
    }

    public void setKeyver(String keyver) {
        this.keyver = keyver;
    }

    public String getKeyindex() {
        return this.keyindex;
    }

    public void setKeyindex(String keyindex) {
        this.keyindex = keyindex;
    }

    public String getCardissuercitycode() {
        return this.cardissuercitycode;
    }

    public void setCardissuercitycode(String cardissuercitycode) {
        this.cardissuercitycode = cardissuercitycode;
    }

    public String getCardmaintype() {
        return this.cardmaintype;
    }

    public void setCardmaintype(String cardmaintype) {
        this.cardmaintype = cardmaintype;
    }

    public String getLasttranstype() {
        return this.lasttranstype;
    }

    public void setLasttranstype(String lasttranstype) {
        this.lasttranstype = lasttranstype;
    }

    public String getLastterminalid() {
        return this.lastterminalid;
    }

    public void setLastterminalid(String lastterminalid) {
        this.lastterminalid = lastterminalid;
    }

    public String getLasttranstime() {
        return this.lasttranstime;
    }

    public void setLasttranstime(String lasttranstime) {
        this.lasttranstime = lasttranstime;
    }

    public String getLastpayfee() {
        return this.lastpayfee;
    }

    public void setLastpayfee(String lastpayfee) {
        this.lastpayfee = lastpayfee;
    }

    public String getLastcardtransseqid() {
        return this.lastcardtransseqid;
    }

    public void setLastcardtransseqid(String lastcardtransseqid) {
        this.lastcardtransseqid = lastcardtransseqid;
    }

    public String getCardversion() {
        return this.cardversion;
    }

    public void setCardversion(String cardversion) {
        this.cardversion = cardversion;
    }

    public String getAcquirer() {
        return this.acquirer;
    }

    public void setAcquirer(String acquirer) {
        this.acquirer = acquirer;
    }

    public String getCardIssuerId() {
        return this.cardIssuerId;
    }

    public void setCardIssuerId(String cardIssuerId) {
        this.cardIssuerId = cardIssuerId;
    }

    public String getTransStatus() {
        return this.transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getTotalFee() {
        return this.totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIsUploade() {
        return this.isUploade;
    }

    public void setIsUploade(String isUploade) {
        this.isUploade = isUploade;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMrchid() {
        return this.mrchid;
    }

    public void setMrchid(String mrchid) {
        this.mrchid = mrchid;
    }

    public String getApplytype() {
        return this.applytype;
    }

    public void setApplytype(String applytype) {
        this.applytype = applytype;
    }

    public String getConductorId() {
        return this.conductorId;
    }

    public void setConductorId(String conductorId) {
        this.conductorId = conductorId;
    }

    public String getTransKeyVersion() {
        return this.transKeyVersion;
    }

    public void setTransKeyVersion(String transKeyVersion) {
        this.transKeyVersion = transKeyVersion;
    }

    public String getCardVersion() {
        return this.cardVersion;
    }

    public void setCardVersion(String cardVersion) {
        this.cardVersion = cardVersion;
    }

    public String getTransSeqId() {
        return this.transSeqId;
    }

    public void setTransSeqId(String transSeqId) {
        this.transSeqId = transSeqId;
    }

    public String getCardissuercitycode2() {
        return this.cardissuercitycode2;
    }

    public void setCardissuercitycode2(String cardissuercitycode2) {
        this.cardissuercitycode2 = cardissuercitycode2;
    }

    public String getAlgTag() {
        return this.algTag;
    }

    public void setAlgTag(String algTag) {
        this.algTag = algTag;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCardCsn() {
        return this.cardCsn;
    }

    public void setCardCsn(String cardCsn) {
        this.cardCsn = cardCsn;
    }

    public String getPsamTerminalId() {
        return this.psamTerminalId;
    }

    public void setPsamTerminalId(String psamTerminalId) {
        this.psamTerminalId = psamTerminalId;
    }

    public String getIsContinuous() {
        return this.isContinuous;
    }

    public void setIsContinuous(String isContinuous) {
        this.isContinuous = isContinuous;
    }

}
