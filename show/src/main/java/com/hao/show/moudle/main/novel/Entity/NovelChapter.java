package com.hao.show.moudle.main.novel.Entity;

public class NovelChapter {
    private String chapterName;
    private String chapterUrl;

    public NovelChapter(String chapterName, String chapterUrl) {
        this.chapterName = chapterName;
        this.chapterUrl = chapterUrl;
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
}
