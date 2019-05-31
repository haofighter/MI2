package com.xb.visitor.Mqtt;

import java.util.List;

public class MqttInfo {


    /**
     * flag : 2
     * outime : 2019-04-19 10:09:00
     * name :
     * image : http://.....
     * openid : sdadad
     * intime : 2019-04-18 10:09:00
     * posno : ["11111","2222"]
     */

    private int flag;
    private String outime;
    private String name;
    private String image;
    private String openid;
    private String intime;
    private List<String> posno;
    private boolean isdown;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getOutime() {
        return outime;
    }

    public void setOutime(String outime) {
        this.outime = outime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public List<String> getPosno() {
        return posno;
    }

    public void setPosno(List<String> posno) {
        this.posno = posno;
    }

    public boolean isIsdown() {
        return isdown;
    }

    public void setIsdown(boolean isdown) {
        this.isdown = isdown;
    }
}
