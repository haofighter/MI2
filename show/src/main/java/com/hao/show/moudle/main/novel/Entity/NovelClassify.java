package com.hao.show.moudle.main.novel.Entity;

public class NovelClassify {
    private String title;
    private String url;

    public String getUrl() {
        return url;
    }


    public String getTitle() {

        return title;
    }


    public NovelClassify(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
