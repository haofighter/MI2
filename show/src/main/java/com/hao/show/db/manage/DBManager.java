package com.hao.show.db.manage;


import com.hao.show.moudle.main.novel.Entity.NovelListItemContent;

public class DBManager {

    public void addNovel(NovelListItemContent novelListItemContent) {
        DBCore.getDaoSession().getNovelListItemContentDao().insertOrReplace(novelListItemContent);
    }
}