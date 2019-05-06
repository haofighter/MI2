package com.xb.haikou.config;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AppConfig {
    @Id(autoincrement = true)
    Long id;
    String lineNo;//线路号
    String busNo;//车辆号
    String posSn;//机具号
    String binVer;//bin的版本号
    Long updateTime;//更新时间
    @Generated(hash = 1920294545)
    public AppConfig(Long id, String lineNo, String busNo, String posSn,
            String binVer, Long updateTime) {
        this.id = id;
        this.lineNo = lineNo;
        this.busNo = busNo;
        this.posSn = posSn;
        this.binVer = binVer;
        this.updateTime = updateTime;
    }
    @Generated(hash = 136961441)
    public AppConfig() {
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
    public String getBusNo() {
        return this.busNo;
    }
    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }
    public String getPosSn() {
        return this.posSn;
    }
    public void setPosSn(String posSn) {
        this.posSn = posSn;
    }
    public String getBinVer() {
        return this.binVer;
    }
    public void setBinVer(String binVer) {
        this.binVer = binVer;
    }
    public Long getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
