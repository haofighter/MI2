package com.hao.show.moudle.main.novel.Entity;

import com.hao.show.base.App;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class NovelChapter implements Serializable {
    private static final long serialVersionUID = App.serialVersionUID;
    @Id(autoincrement = true)
    Long Cid;//小说章节的唯一标示
    Long Nid;//小说的id
    private String chapterName;
    private String chapterUrl;
    String chapterContent;
    String nextChapterUrl;
    String beforChapterUrl;



    @Generated(hash = 933010179)
    public NovelChapter(Long Cid, Long Nid, String chapterName, String chapterUrl,
            String chapterContent, String nextChapterUrl, String beforChapterUrl) {
        this.Cid = Cid;
        this.Nid = Nid;
        this.chapterName = chapterName;
        this.chapterUrl = chapterUrl;
        this.chapterContent = chapterContent;
        this.nextChapterUrl = nextChapterUrl;
        this.beforChapterUrl = beforChapterUrl;
    }

    @Generated(hash = 922107813)
    public NovelChapter() {
    }



    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    public Long getCid() {
        return this.Cid;
    }

    public void setCid(Long Cid) {
        this.Cid = Cid;
    }

    public Long getNid() {
        return this.Nid;
    }

    public void setNid(Long Nid) {
        this.Nid = Nid;
    }

    public String getChapterContent() {
        return this.chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    public String getNextChapterUrl() {
        return this.nextChapterUrl;
    }

    public void setNextChapterUrl(String nextChapterUrl) {
        this.nextChapterUrl = nextChapterUrl;
    }

    public String getBeforChapterUrl() {
        return this.beforChapterUrl;
    }

    public void setBeforChapterUrl(String beforChapterUrl) {
        this.beforChapterUrl = beforChapterUrl;
    }
}
