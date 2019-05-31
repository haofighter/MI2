package com.xb.haikou.record;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ScanRecordEntity {
    @Id(autoincrement = true)
    private Long id;
    private String qrCode;//二维码原始信息
    private String qrcodeType;//0 腾讯 1阿里 2自有码 3其他
    private int key_id;
    private String open_id;//账户id
    private String mac_root_id;
    private String upState;//是否上传  0 未上传  1 已上传  2 上传失败
    private String city_code;//城市ID
    private String mch_trx_id;//商户订单号  后台去重的条件
    private Long order_time;//订单时间
    private String order_desc;//线路名称
    private String total_fee;//总金额，单位分
    private String pay_fee;//实际扣款金额，单位分
    private String exp_type;//订单异常类型 0 正常1 单边账(入站)   2 单边账(出站)
    private String charge_type;//计费类型:0 一次性扫码计费 1 双段扫码计费
    private String ext;//乘车站点信息:{ "in_station_id":123456789,"in_station_name":"A地铁站","out_station_id":12345678     0,     "out_station_name":"B地铁站
    private String record;//交易记录列表[ 交易记录1hex， 交易记录2hex， ...]
    private String bus_no;//车牌号
    private String pos_no;//机具编号
    private String bus_line_name;//线路
    private String driveno;//司机编号
    private String unitno;//公司编号
    private String acquirer;//收款方标识
    private String conductorId;//售票员
    private String currency;//币种156为rmb
    private String cardId;//用于联合发码，卡Id   阿里：卡票号，来自二维码 card_no
    private String biztype;//腾讯的固定为08  支付宝乘车码交易09
    private Long creatTime;//储存记录的时间

    /************************************支付宝**********************************/
    private String terseno;//终端的流水号
    private String terminalId;//机具设备终端编号
    private String driverId;//司机卡Id
    private String transTime;//交易时间
    private String transCityCode;//交易发生地城市代码，遵循银联
    private String lineId;//线路ID
    private String lineName;//线路名
    private String station;//站点
    private String stationName;//站点名
    //    private String chargeType;//计费类型:0-一次性扫码计费，1-分段计
//    private String totalFee;//总金额，单位分
//    private String payFee;//总金额，单位分
    private String userId;//传入用户的支付宝user_id
    private String appPosition;//事件发生lbs 经纬度使用英文逗号分隔.可不传
    private String vehicleType;//交通工具类型(公交固定取值:BUS）
    private String cardType;//以码信息card_type为准
    private String discountType;//优惠类型
    private String discountDesc;//优惠描述
    private String busno;//优惠描述


    @Generated(hash = 78491298)
    public ScanRecordEntity(Long id, String qrCode, String qrcodeType, int key_id, String open_id, String mac_root_id, String upState, String city_code,
                            String mch_trx_id, Long order_time, String order_desc, String total_fee, String pay_fee, String exp_type, String charge_type, String ext,
                            String record, String bus_no, String pos_no, String bus_line_name, String driveno, String unitno, String acquirer, String conductorId,
                            String currency, String cardId, String biztype, Long creatTime, String terseno, String terminalId, String driverId, String transTime,
                            String transCityCode, String lineId, String lineName, String station, String stationName, String userId, String appPosition,
                            String vehicleType, String cardType, String discountType, String discountDesc, String busno) {
        this.id = id;
        this.qrCode = qrCode;
        this.qrcodeType = qrcodeType;
        this.key_id = key_id;
        this.open_id = open_id;
        this.mac_root_id = mac_root_id;
        this.upState = upState;
        this.city_code = city_code;
        this.mch_trx_id = mch_trx_id;
        this.order_time = order_time;
        this.order_desc = order_desc;
        this.total_fee = total_fee;
        this.pay_fee = pay_fee;
        this.exp_type = exp_type;
        this.charge_type = charge_type;
        this.ext = ext;
        this.record = record;
        this.bus_no = bus_no;
        this.pos_no = pos_no;
        this.bus_line_name = bus_line_name;
        this.driveno = driveno;
        this.unitno = unitno;
        this.acquirer = acquirer;
        this.conductorId = conductorId;
        this.currency = currency;
        this.cardId = cardId;
        this.biztype = biztype;
        this.creatTime = creatTime;
        this.terseno = terseno;
        this.terminalId = terminalId;
        this.driverId = driverId;
        this.transTime = transTime;
        this.transCityCode = transCityCode;
        this.lineId = lineId;
        this.lineName = lineName;
        this.station = station;
        this.stationName = stationName;
        this.userId = userId;
        this.appPosition = appPosition;
        this.vehicleType = vehicleType;
        this.cardType = cardType;
        this.discountType = discountType;
        this.discountDesc = discountDesc;
        this.busno = busno;
    }

    @Generated(hash = 113634506)
    public ScanRecordEntity() {
    }


    public String getTerseno() {
        return terseno;
    }

    public void setTerseno(String terseno) {
        this.terseno = terseno;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getTransCityCode() {
        return transCityCode;
    }

    public void setTransCityCode(String transCityCode) {
        this.transCityCode = transCityCode;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppPosition() {
        return appPosition;
    }

    public void setAppPosition(String appPosition) {
        this.appPosition = appPosition;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDiscountDesc() {
        return discountDesc;
    }

    public void setDiscountDesc(String discountDesc) {
        this.discountDesc = discountDesc;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrcodeType() {
        return qrcodeType;
    }

    public void setQrcodeType(String qrcodeType) {
        this.qrcodeType = qrcodeType;
    }

    public int getKey_id() {
        return key_id;
    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getMac_root_id() {
        return mac_root_id;
    }

    public void setMac_root_id(String mac_root_id) {
        this.mac_root_id = mac_root_id;
    }

    public String getUpState() {
        return upState;
    }

    public void setUpState(String upState) {
        this.upState = upState;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getMch_trx_id() {
        return mch_trx_id;
    }

    public void setMch_trx_id(String mch_trx_id) {
        this.mch_trx_id = mch_trx_id;
    }

    public Long getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Long order_time) {
        this.order_time = order_time;
    }

    public String getOrder_desc() {
        return order_desc;
    }

    public void setOrder_desc(String order_desc) {
        this.order_desc = order_desc;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getPay_fee() {
        return pay_fee;
    }

    public void setPay_fee(String pay_fee) {
        this.pay_fee = pay_fee;
    }

    public String getExp_type() {
        return exp_type;
    }

    public void setExp_type(String exp_type) {
        this.exp_type = exp_type;
    }

//    public String getCharge_type() {
//        return charge_type;
//    }
//
//    public void setCharge_type(String charge_type) {
//        this.charge_type = charge_type;
//    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getBus_no() {
        return bus_no;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }

    public String getPos_no() {
        return pos_no;
    }

    public void setPos_no(String pos_no) {
        this.pos_no = pos_no;
    }

    public String getBus_line_name() {
        return bus_line_name;
    }

    public void setBus_line_name(String bus_line_name) {
        this.bus_line_name = bus_line_name;
    }

    public String getDriveno() {
        return driveno;
    }

    public void setDriveno(String driveno) {
        this.driveno = driveno;
    }

    public String getUnitno() {
        return unitno;
    }

    public void setUnitno(String unitno) {
        this.unitno = unitno;
    }

    public String getAcquirer() {
        return acquirer;
    }

    public void setAcquirer(String acquirer) {
        this.acquirer = acquirer;
    }

    public String getConductorId() {
        return conductorId;
    }

    public void setConductorId(String conductorId) {
        this.conductorId = conductorId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBiztype() {
        return biztype;
    }

    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }

    public Long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Long creatTime) {
        this.creatTime = creatTime;
    }

    public String getCharge_type() {
        return this.charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }
}
