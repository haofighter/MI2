package com.hao.show.moudle.main.novel.Entity;

import java.util.ArrayList;
import java.util.List;

public class NovelPage {
    private String nextPageUrl;
    private String beforPageUrl;
    private String fristPageUrl;
    private String lastPageUrl;
    private List<NovelListItemContent> novelListItemContentList;

    public String getBeforPageUrl() {
        return beforPageUrl;
    }

    public void setBeforPageUrl(String beforPageUrl) {
        this.beforPageUrl = beforPageUrl;
    }

    public NovelPage() {
        this.novelListItemContentList = new ArrayList<>();
    }

    public String getFristPageUrl() {
        return fristPageUrl;
    }

    public void setFristPageUrl(String fristPageUrl) {
        this.fristPageUrl = fristPageUrl;
    }

    public String getLastPageUrl() {
        return lastPageUrl;
    }

    public void setLastPageUrl(String lastPageUrl) {
        this.lastPageUrl = lastPageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public void setNovelListItemContentList(List<NovelListItemContent> novelListItemContentList) {
        this.novelListItemContentList = novelListItemContentList;
    }

    public List<NovelListItemContent> getNovelListItemContentList() {
        return novelListItemContentList;
    }
}
