package com.hao.show.moudle.main.novel;

import android.os.Bundle;
import com.hao.lib.base.Rx.Rx;
import com.hao.lib.base.Rx.RxMessage;
import com.hao.lib.view.ScorllTextView;
import com.hao.show.R;
import com.hao.show.base.BaseActivity;
import com.hao.show.db.manage.DBManager;
import com.hao.show.moudle.main.novel.Entity.HistroryReadEntity;
import com.hao.show.moudle.main.novel.Entity.NovelChapter;
import com.hao.show.spider.SpiderUtils;

public class NovelLooksActivity extends BaseActivity {
    ScorllTextView novel_looks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_looks);
        init();
    }

    @Override
    protected void findView() {
        novel_looks = findViewById(R.id.novel_looks);
    }

    private void init() {
        NovelChapter novelChapter = (NovelChapter) getIntent().getSerializableExtra("chapter");
        HistroryReadEntity histroryReadEntity = DBManager.checkHistroy(novelChapter.getNid());
        if (novelChapter.getChapterContent() == null || novelChapter.getChapterContent().equals("")) {
            getDetailHtml(novelChapter.getChapterUrl());
        } else {
            novel_looks.setText(novelChapter.getChapterContent());
            if (histroryReadEntity != null) {
                novel_looks.setPage(histroryReadEntity.getNoverPage());
            }
        }
    }

    //通过网址获取网页抓取数据
    private void getDetailHtml(String url) {
        Rx.getInstance().addRxMessage(new RxMessage() {
            @Override
            public void rxDo(Object tag, final Object content) {
                if (tag instanceof String) {
                    try {
                        novel_looks.setText((String) content);
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                novel_looks.setText((String) content);
                            }
                        });
                    }
                }
            }

        });
        SpiderUtils.getHtml(url, "detail");
    }
}
