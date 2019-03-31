package com.hao.show.moudle.main.novel;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.hao.mi2.base.Rx.Rx;
import com.hao.mi2.base.Rx.RxMessage;
import com.hao.mi2.view.RecycleView;
import com.hao.show.R;
import com.hao.show.base.BaseActivity;
import com.hao.show.moudle.main.novel.Entity.NovelClassify;
import com.hao.show.moudle.main.novel.Entity.NovelPage;
import com.hao.show.spider.SpiderNovelFromBiQu;
import com.hao.show.spider.SpiderUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

public class NovelActivity extends BaseActivity {
    RecycleView novel_list;
    RecycleView novel_classify_list;
    TwinklingRefreshLayout refresh;

    @Override
    protected void findView() {
        setDrawerContent(LayoutInflater.from(this).inflate(R.layout.menu_layout, null));
        setView();
    }

    @Override
    public int initViewID() {
        return R.layout.activity_novel;
    }

    /**
     * 设置本页面的各种控件的操作
     */
    public void setView() {
        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });
        novel_list = findViewById(R.id.novel_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        novel_list.setLayoutManager(linearLayoutManager);
        novel_list.setAdapter(new NovelListAdapter(this).setItemClickLisener(new NovelListAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position, View view, Object o) {
                Intent intent = new Intent(NovelActivity.this, NovelDetailActivity.class);
                intent.putExtra("detailUrl", ((NovelListAdapter) novel_list.getAdapter()).getNowDate().getNovelListItemContentList().get(position).getUrl());
                startActivity(intent);
            }
        }));
        novel_list.setScrollListener(new RecycleView.RecycleScrollListener() {
            @Override
            public void onScroll(int start, int end) {
                Log.i("滑动", "当前显示的第一项：" + start + "         当前显示的最后一项：" + end);
            }
        });

        refresh = findViewById(R.id.refresh);
        refresh.setEnableRefresh(true);
        refresh.setEnableLoadmore(true);
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                String beforPage = ((NovelListAdapter) novel_list.getAdapter()).getNowDate().getBeforPageUrl();
                if (beforPage == null || beforPage.equals("")) {
                    refresh.finishRefreshing();
                    Toast.makeText(NovelActivity.this, "没有更多内容了", Toast.LENGTH_LONG).show();
                } else {
                    ((NovelListAdapter) novel_list.getAdapter()).clear();
                    setContentList(beforPage);
                }
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                String nextPage = ((NovelListAdapter) novel_list.getAdapter()).getNowDate().getNextPageUrl();
                if (nextPage == null || nextPage.equals("")) {
                    Toast.makeText(NovelActivity.this, "没有更多内容了", Toast.LENGTH_LONG).show();
                } else {
                    setContentList(nextPage);
                }
            }
        });
    }

    @Override
    protected void initDrawView(View view) {
        ImageView imageView = view.findViewById(R.id.logo);
        novel_classify_list = view.findViewById(R.id.novel_classify_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        novel_classify_list.setLayoutManager(linearLayoutManager);
        novel_classify_list.setAdapter(new NovelClassifyListAdapter(this).setItemClickLisener(new NovelClassifyListAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position, View view, Object o) {
                setContentList(((NovelClassify) o).getUrl());
            }
        }));
        imageView.setImageResource(R.mipmap.logo);
        getUrlDate();
    }


    /**
     * 抓取网页的主题
     */
    private void getUrlDate() {
        Rx.getInstance().addRxMessage(new RxMessage() {
            @Override
            protected void rxDo(final String tag, final Object o) {
                Log.i("获取节点  回调", tag + "       " + o);
                try {
                    setViewDate(tag, o);
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setViewDate(tag, o);
                        }
                    });
                }

            }
        });
        if (SpiderNovelFromBiQu.BiQuMainUrl != null) {
            SpiderUtils.getHtml(SpiderNovelFromBiQu.BiQuMainUrl, "html");
        }
    }

    /**
     * 抓取标题栏的数据
     *
     * @param o
     */
    public void setViewDate(String tag, Object o) {
        refresh.finishRefreshing();
        refresh.finishLoadmore();
        if (tag.equals("html")) {
            List<NovelClassify> classifies = SpiderNovelFromBiQu.getClassify((String) o);
            ((NovelClassifyListAdapter) novel_classify_list.getAdapter()).update(classifies);
        } else if (tag.equals("novel_detail")) {
            refresh.setEnableRefresh(true);
            NovelPage novelPage = SpiderNovelFromBiQu.getNovelList((String) o);
            ((NovelListAdapter) novel_list.getAdapter()).add(novelPage);
        }
    }


    /**
     * 抓取当前页的数据
     *
     * @param url 当前页面url
     */
    public void setContentList(String url) {
        SpiderUtils.getHtml(url, "novel_detail");
    }

}
