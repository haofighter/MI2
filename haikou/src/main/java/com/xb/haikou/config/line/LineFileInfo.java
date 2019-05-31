package com.xb.haikou.config.line;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LineFileInfo {

    @Id(autoincrement = true)
    Long id;
    int start;
    @Unique
    String tag;
    int len;

    String FileVerson;
    String DateEnable;

    public LineFileInfo(int start, String tag, int len) {
        this.start = start;
        this.tag = tag;
        this.len = len;
    }

    @Generated(hash = 646049731)
    public LineFileInfo(Long id, int start, String tag, int len, String FileVerson,
            String DateEnable) {
        this.id = id;
        this.start = start;
        this.tag = tag;
        this.len = len;
        this.FileVerson = FileVerson;
        this.DateEnable = DateEnable;
    }

    @Generated(hash = 1300409271)
    public LineFileInfo() {
    }

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

    public String getFileVerson() {
        return FileVerson;
    }

    public void setFileVerson(String fileVerson) {
        FileVerson = fileVerson;
    }

    public String getDateEnable() {
        return DateEnable;
    }

    public void setDateEnable(String dateEnable) {
        DateEnable = dateEnable;
    }

    @Override
    public String toString() {
        return "LineFileInfo{" +
                "start=" + start +
                ", tag='" + tag + '\'' +
                ", len=" + len +
                ", FileVerson='" + FileVerson + '\'' +
                ", DateEnable='" + DateEnable + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
