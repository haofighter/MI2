package com.hao.show.moudle.main.novel.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class HistroryReadEntity {
    @Unique
    String novelTitle;
    @Unique
    long novelId;
    String noverChapterUrl;//章节连接地址
    String noverChapter;//章节连接
    long noverChapterId;//章节对应的数据库id
    int noverPage;//阅读页数

    @Generated(hash = 1560118513)
    public HistroryReadEntity(String novelTitle, long novelId,
            String noverChapterUrl, String noverChapter, long noverChapterId,
            int noverPage) {
        this.novelTitle = novelTitle;
        this.novelId = novelId;
        this.noverChapterUrl = noverChapterUrl;
        this.noverChapter = noverChapter;
        this.noverChapterId = noverChapterId;
        this.noverPage = noverPage;
    }

    @Generated(hash = 858925925)
    public HistroryReadEntity() {
    }

    public String getNovelTitle() {
        return this.novelTitle;
    }

    public void setNovelTitle(String novelTitle) {
        this.novelTitle = novelTitle;
    }

    public String getNoverChapterUrl() {
        return this.noverChapterUrl;
    }

    public void setNoverChapterUrl(String noverChapterUrl) {
        this.noverChapterUrl = noverChapterUrl;
    }

    public long getNoverChapterId() {
        return this.noverChapterId;
    }

    public void setNoverChapterId(long noverChapterId) {
        this.noverChapterId = noverChapterId;
    }

    public int getNoverPage() {
        return this.noverPage;
    }

    public void setNoverPage(int noverPage) {
        this.noverPage = noverPage;
    }

    public long getNovelId() {
        return this.novelId;
    }

    public void setNovelId(long novelId) {
        this.novelId = novelId;
    }

    public String getNoverChapter() {
        return this.noverChapter;
    }

    public void setNoverChapter(String noverChapter) {
        this.noverChapter = noverChapter;
    }
}
