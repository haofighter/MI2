package com.xb.haikou.config;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 运行时需要配置的参数
 */
@Entity
public class AppRunConfigEntity {
    @Id(autoincrement = true)
    Long id;
    String lineNo = "";//线路号
    String lineName = "";//线路名
    String busNo = "";//车辆号
    @Unique
    String posSn = "";//机具号
    String driverNo = "";//司机号
    String conductor = "";//售票员
    String binVer = "";//bin的版本号
    Long updateTime;//更新时间
    String PSAM = "";//psam卡号
    String PSAMID = "";//psam终端号
    String PSAMSY = "";//psam索引
    String JTMAC = "";

    @Generated(hash = 654711287)
    public AppRunConfigEntity(Long id, String lineNo, String lineName, String busNo,
                              String posSn, String driverNo, String conductor, String binVer, Long updateTime,
                              String PSAM, String PSAMID, String PSAMSY, String JTMAC) {
        this.id = id;
        this.lineNo = lineNo;
        this.lineName = lineName;
        this.busNo = busNo;
        this.posSn = posSn;
        this.driverNo = driverNo;
        this.conductor = conductor;
        this.binVer = binVer;
        this.updateTime = updateTime;
        this.PSAM = PSAM;
        this.PSAMID = PSAMID;
        this.PSAMSY = PSAMSY;
        this.JTMAC = JTMAC;
    }

    @Generated(hash = 1443834334)
    public AppRunConfigEntity() {
    }

    public String getDriverNo() {
        return driverNo;
    }

    public AppRunConfigEntity setDriverNo(String driverNo) {
        this.driverNo = driverNo;
        return this;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }


    public void setPosSn(String posSn) {
        this.posSn = posSn;
    }

    public String getBinVer() {
        return binVer;
    }

    public void setBinVer(String binVer) {
        this.binVer = binVer;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getPSAM() {
        return PSAM;
    }

    public void setPSAM(String PSAM) {
        this.PSAM = PSAM;
    }

    public String getPSAMID() {
        return PSAMID;
    }

    public void setPSAMID(String PSAMID) {
        this.PSAMID = PSAMID;
    }

    public String getPSAMSY() {
        return PSAMSY;
    }

    public void setPSAMSY(String PSAMSY) {
        this.PSAMSY = PSAMSY;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLineNo() {
        return this.lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getLineName() {
        return this.lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getBusNo() {
        return this.busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getPosSn() {
        return this.posSn;
    }

    public String getJTMAC() {
        return this.JTMAC;
    }

    public AppRunConfigEntity setJTMAC(String JTMAC) {
        this.JTMAC = JTMAC;
        return this;
    }
}
