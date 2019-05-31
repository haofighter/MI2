package com.hao.show.moudle.main.novel.Entity;

import java.util.List;

//单本小说详细内容
public class NovelDetail {
    private String imageUrl;
    private String novel_title;
    private String novel_auther;
    private String novel_type;
    private String novel_length;
    private String novel_introduce;
    private String last_chapter_name;
    private String last_chapter_url;
    private List<NovelChapter> novelChapters;

    public String getLast_chapter_name() {
        return last_chapter_name;
    }

    public void setLast_chapter_name(String last_chapter_name) {
        this.last_chapter_name = last_chapter_name;
    }

    public String getLast_chapter_url() {
        return last_chapter_url;
    }

    public void setLast_chapter_url(String last_chapter_url) {
        this.last_chapter_url = last_chapter_url;
    }

    public String getNovel_introduce() {
        return novel_introduce;
    }

    public void setNovel_introduce(String novel_introduce) {
        this.novel_introduce = novel_introduce;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNovel_title() {
        return novel_title;
    }

    public void setNovel_title(String novel_title) {
        this.novel_title = novel_title;
    }

    public String getNovel_auther() {
        return novel_auther;
    }

    public void setNovel_auther(String novel_auther) {
        this.novel_auther = novel_auther;
    }

    public String getNovel_type() {
        return novel_type;
    }

    public void setNovel_type(String novel_type) {
        this.novel_type = novel_type;
    }

    public String getNovel_length() {
        return novel_length;
    }

    public void setNovel_length(String novel_length) {
        this.novel_length = novel_length;
    }

    public List<NovelChapter> getNovelChapters() {
        return novelChapters;
    }

    public void setNovelChapters(List<NovelChapter> novelChapters) {
        this.novelChapters = novelChapters;
    }

    @Override
    public String toString() {
        return "NovelDetail{" +
                "imageUrl='" + imageUrl + '\'' +
                ", novel_title='" + novel_title + '\'' +
                ", novel_auther='" + novel_auther + '\'' +
                ", novel_type='" + novel_type + '\'' +
                ", novel_length='" + novel_length + '\'' +
                ", novelChapters=" + novelChapters +
                '}';
    }
}
