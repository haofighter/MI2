package com.hao.show.moudle.main.novel.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NovelContent {
    @Id(autoincrement = true)
    Long nID;
    String chapterName;
    String chapterContent;
    String nextChapterUrl;
    String beforChapterUrl;

    @Generated(hash = 974800950)
    public NovelContent(Long nID, String chapterName, String chapterContent,
            String nextChapterUrl, String beforChapterUrl) {
        this.nID = nID;
        this.chapterName = chapterName;
        this.chapterContent = chapterContent;
        this.nextChapterUrl = nextChapterUrl;
        this.beforChapterUrl = beforChapterUrl;
    }

    @Generated(hash = 1356956392)
    public NovelContent() {
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    public String getNextChapterUrl() {
        return nextChapterUrl;
    }

    public void setNextChapterUrl(String nextChapterUrl) {
        this.nextChapterUrl = nextChapterUrl;
    }

    public String getBeforChapterUrl() {
        return beforChapterUrl;
    }

    public void setBeforChapterUrl(String beforChapterUrl) {
        this.beforChapterUrl = beforChapterUrl;
    }

    public Long getNID() {
        return this.nID;
    }

    public void setNID(Long nID) {
        this.nID = nID;
    }
}
