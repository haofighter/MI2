package com.hao.show.moudle.main.novel.Entity;

public class NovelDetail {
    private String title;
    private String url;
    private String auther;
    private String newChapter;
    private String newChapter_utl;

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

    public NovelDetail(String title, String url, String auther, String newChapter, String newChapter_utl) {
        this.title = title;
        this.url = url;
        this.auther = auther;
        this.newChapter = newChapter;
        this.newChapter_utl = newChapter_utl;
    }
}
