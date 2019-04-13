package com.hao.show.db.manage;


import com.hao.show.db.dao.NovelChapterDao;
import com.hao.show.db.dao.NovelListItemContentDao;
import com.hao.show.moudle.main.novel.Entity.NovelChapter;
import com.hao.show.moudle.main.novel.Entity.NovelListItemContent;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    /**
     * 添加或更新数据库小说
     * 如果本地为记录本小说 则进行添加
     * 如果记录过 若最新章节有更新 进行更新
     * 否则返回查询结果
     *
     * @param novelListItemContent 小说条目信息
     * @return
     */
    public static NovelListItemContent addNovel(NovelListItemContent novelListItemContent) {
        NovelListItemContent old = checkNovel(novelListItemContent);
        if (old == null) {
            DBCore.getDaoSession().getNovelListItemContentDao().insertOrReplace(novelListItemContent);
        } else {
            if (!old.getNewChapter().equals(novelListItemContent.getNewChapter())) {
                novelListItemContent.setNID(old.getNID());
                DBCore.getDaoSession().getNovelListItemContentDao().insertOrReplace(novelListItemContent);
            } else {
                return old;
            }
        }
        return checkNovel(novelListItemContent);
    }

    //通过小说名及作者来判断是小说是否为同一本
    public static NovelListItemContent checkNovel(NovelListItemContent novelListItemContent) {
        return DBCore.getDaoSession().getNovelListItemContentDao().queryBuilder().where(NovelListItemContentDao.Properties.Title.eq(novelListItemContent.getTitle()),
                NovelListItemContentDao.Properties.Auther.eq(novelListItemContent.getAuther())).unique();
    }

    /**
     * 批量添加或修改小说章节目录
     *
     * @param novelChapterList
     */
    public static void addNovelChapter(List<NovelChapter> novelChapterList) {
        DBCore.getDaoSession().getNovelChapterDao().insertOrReplaceInTx(novelChapterList);
    }

    /**
     * 更新或者添加章节
     *
     * @param
     */
    public static void addNovelChapter(NovelChapter novelChapter) {
        List<NovelChapter> novelChapters = new ArrayList<>();
        novelChapters.add(novelChapter);
        addNovelChapter(novelChapters);
    }

    /**
     * 通过小说的id查询小说章节
     * id[0]  小说id
     * id[1]  小说文章id
     */
    public static List<NovelChapter> selectNovelChapter(Long... id) {
        if (id.length == 1) {
            return DBCore.getDaoSession().getNovelChapterDao().queryBuilder().where(NovelChapterDao.Properties.Nid.eq(id[0])).list();
        } else {
            return DBCore.getDaoSession().getNovelChapterDao().queryBuilder().where(NovelChapterDao.Properties.Nid.eq(id[0]), NovelChapterDao.Properties.Cid.eq(id[1])).list();
        }
    }


}