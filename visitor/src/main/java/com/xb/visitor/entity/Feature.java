package com.xb.visitor.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Feature {
    @Id(autoincrement = true)
    public Long FeatureId;
    public String openid;
    public String fvect;
    public int face_x1;
    public int face_y1;
    public int face_x2;
    public int face_y2;

    @Generated(hash = 1543001312)
    public Feature(Long FeatureId, String openid, String fvect, int face_x1,
                   int face_y1, int face_x2, int face_y2) {
        this.FeatureId = FeatureId;
        this.openid = openid;
        this.fvect = fvect;
        this.face_x1 = face_x1;
        this.face_y1 = face_y1;
        this.face_x2 = face_x2;
        this.face_y2 = face_y2;
    }

    @Generated(hash = 829817725)
    public Feature() {
    }

    public Long getFeatureId() {
        return this.FeatureId;
    }

    public void setFeatureId(Long FeatureId) {
        this.FeatureId = FeatureId;
    }

    public String getFvect() {
        return this.fvect;
    }

    public void setFvect(String fvect) {
        this.fvect = fvect;
    }

    public int getFace_x1() {
        return this.face_x1;
    }

    public void setFace_x1(int face_x1) {
        this.face_x1 = face_x1;
    }

    public int getFace_y1() {
        return this.face_y1;
    }

    public void setFace_y1(int face_y1) {
        this.face_y1 = face_y1;
    }

    public int getFace_x2() {
        return this.face_x2;
    }

    public void setFace_x2(int face_x2) {
        this.face_x2 = face_x2;
    }

    public int getFace_y2() {
        return this.face_y2;
    }

    public void setFace_y2(int face_y2) {
        this.face_y2 = face_y2;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }


}
