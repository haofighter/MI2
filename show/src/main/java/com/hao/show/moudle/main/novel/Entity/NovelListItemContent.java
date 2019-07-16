package com.hao.show.moudle.main.novel.Entity;

import com.hao.show.base.App;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * 小说列表Item信息
 */
@Entity
public class NovelListItemContent implements Serializable {
    private static final long serialVersionUID = App.serialVersionUID;
    @Id(autoincrement = true)
    Long nID;//小说的唯一标识
    private String title;
    private String url;
    private String auther;
    private String newChapter;
    private String newChapter_utl;
    private String novelImage = "";
    private String novelType = "";


    public String getNovelType() {
        return novelType;
    }

    public void setNovelType(String novelType) {
        this.novelType = novelType;
    }

    public String getNovelImage() {
        return novelImage;
    }

    public NovelListItemContent setNovelImage(String novelImage) {
        this.novelImage = novelImage;
        return this;
    }

    public String getNewChapter_utl() {
        return newChapter_utl;
    }


    public String getUrl() {
        return url;
    }


    public String getTitle() {

        return title;
    }

    public String getAuther() {
        return auther;
    }

    public String getNewChapter() {
        return newChapter;
    }


    public Long getNID() {
        return this.nID;
    }


    public NovelListItemContent setNID(Long nID) {
        this.nID = nID;
        return this;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public void setAuther(String auther) {
        this.auther = auther;
    }


    public void setNewChapter(String newChapter) {
        this.newChapter = newChapter;
    }


    public void setNewChapter_utl(String newChapter_utl) {
        this.newChapter_utl = newChapter_utl;
    }

    public NovelListItemContent(String title, String url, String auther, String newChapter, String newChapter_utl) {
        this.title = title;
        this.url = url;
        this.auther = auther;
        this.newChapter = newChapter;
        this.newChapter_utl = newChapter_utl;
    }


    @Generated(hash = 900250585)
    public NovelListItemContent(Long nID, String title, String url, String auther, String newChapter, String newChapter_utl,
            String novelImage, String novelType) {
        this.nID = nID;
        this.title = title;
        this.url = url;
        this.auther = auther;
        this.newChapter = newChapter;
        this.newChapter_utl = newChapter_utl;
        this.novelImage = novelImage;
        this.novelType = novelType;
    }

    @Generated(hash = 56679149)
    public NovelListItemContent() {
    }

}
