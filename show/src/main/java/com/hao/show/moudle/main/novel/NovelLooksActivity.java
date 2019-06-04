package com.hao.show.moudle.main.novel;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import com.hao.lib.base.Rx.Rx;
import com.hao.lib.base.Rx.RxMessage;
import com.hao.lib.view.ScorllTextView;
import com.hao.show.R;
import com.hao.show.base.BaseActivity;
import com.hao.show.db.manage.DBManager;
import com.hao.show.moudle.main.novel.Entity.HistroryReadEntity;
import com.hao.show.moudle.main.novel.Entity.NovelChapter;
import com.hao.show.spider.SpiderNovelFromBiQu;
import com.hao.show.spider.SpiderUtils;

import java.util.List;

public class NovelLooksActivity extends BaseActivity {
    ScorllTextView novel_looks;
    NovelChapter novelChapter;
    long novelContentId;
    List<NovelChapter> novelChapters;


    @Override
    public int initViewID() {
        return R.layout.activity_novel_looks;
    }

    @Override
    protected void findView() {
        novelChapter = (NovelChapter) getIntent().getSerializableExtra("chapter");
        novelChapters = DBManager.selectNovelChapter(novelChapter.getNid());
        novelContentId = novelChapter.getCid();
        novel_looks = findViewById(R.id.novel_looks);
        novel_looks.setTextChangetListener(new ScorllTextView.ScorllTextChangeListener() {
            @Override
            public void pageChange(int page) {

            }

            @Override
            public void textEnd() {
                if (novelContentId != novelChapters.size()) {
                    novelContentId++;
                }
                showLoading();
                getDetailHtml(novelChapters.get((int) novelContentId));
            }

            @Override
            public void textStart() {
                if (novelContentId != 0) {
                    novelContentId--;
                }
                showLoading();
                getDetailHtml(novelChapters.get((int) novelContentId));
            }
        });
        init();
    }

    private void init() {
        dismisLoading();
        HistroryReadEntity histroryReadEntity = DBManager.checkHistroy(novelChapter.getNid());
        if (novelChapter.getChapterContent() == null || novelChapter.getChapterContent().equals("")) {
            getDetailHtml(novelChapter);
        } else {
            novel_looks.setText(novelChapter.getChapterContent());
            if (histroryReadEntity != null) {
                novel_looks.setPage(histroryReadEntity.getNoverPage());
            }
        }
    }

    //通过网址获取网页抓取数据
    private void getDetailHtml(NovelChapter no) {
        Rx.getInstance().addRxMessage(new RxMessage() {
            @Override
            public void rxDo(final Object tag, final Object content) {
                if (tag instanceof NovelChapter) {
                    try {
                        setViewDate((NovelChapter) tag, content);
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setViewDate((NovelChapter) tag, content);
                            }
                        });
                    }
                }
            }
        });

        SpiderUtils.getHtml(no.getChapterUrl(), no);
    }


    //解析数据
    private void setViewDate(NovelChapter no, Object o) {
        dismisLoading();
        SpiderNovelFromBiQu.getNovelContent((String) o, no);
        novel_looks.setText(SpiderNovelFromBiQu.getNovelContent((String) o, no).getChapterContent());
    }
}
