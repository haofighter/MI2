package com.xb.visitor.entity;

import com.xb.visitor.Mqtt.MqttInfo;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class FaceInfo {
    @Id(autoincrement = true)
    Long faceId;
    private int flag;
    private String outime;
    private String name;
    private String image;
    private String openid;
    private String intime;

    @Generated(hash = 521850416)
    public FaceInfo(Long faceId, int flag, String outime, String name, String image,
                    String openid, String intime) {
        this.faceId = faceId;
        this.flag = flag;
        this.outime = outime;
        this.name = name;
        this.image = image;
        this.openid = openid;
        this.intime = intime;
    }

    @Generated(hash = 1003586454)
    public FaceInfo() {
    }

    public Long getFaceId() {
        return this.faceId;
    }

    public void setFaceId(Long faceId) {
        this.faceId = faceId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getOutime() {
        return this.outime;
    }

    public void setOutime(String outime) {
        this.outime = outime;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIntime() {
        return this.intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }


    public FaceInfo(MqttInfo mqttInfo) {
        this.name = mqttInfo.getName();
        this.flag = mqttInfo.getFlag();
        this.outime = mqttInfo.getOutime();
        this.name = mqttInfo.getName();
        this.image = mqttInfo.getImage();
        this.openid = mqttInfo.getOpenid();
        this.intime = mqttInfo.getIntime();
    }
}
