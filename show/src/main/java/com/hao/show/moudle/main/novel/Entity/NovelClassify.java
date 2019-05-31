package com.hao.show.moudle.main.novel.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 小说的章节信息
 */
@Entity
public class NovelClassify {
    @Id(autoincrement = true)
    Long ncId;//章节ID
    Long ntID;//小说ID
    Long idNum;//章节序号
    private String title;
    private String url;

    public String getUrl() {
        return url;
    }


    public String getTitle() {

        return title;
    }


    public Long getNcId() {
        return this.ncId;
    }


    public void setNcId(Long ncId) {
        this.ncId = ncId;
    }


    public Long getNtID() {
        return this.ntID;
    }


    public void setNtID(Long ntID) {
        this.ntID = ntID;
    }


    public Long getIdNum() {
        return this.idNum;
    }


    public void setIdNum(Long idNum) {
        this.idNum = idNum;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public NovelClassify(String title, String url) {
        this.title = title;
        this.url = url;
    }


    @Generated(hash = 1632454568)
    public NovelClassify(Long ncId, Long ntID, Long idNum, String title,
            String url) {
        this.ncId = ncId;
        this.ntID = ntID;
        this.idNum = idNum;
        this.title = title;
        this.url = url;
    }


    @Generated(hash = 1070182329)
    public NovelClassify() {
    }
}
