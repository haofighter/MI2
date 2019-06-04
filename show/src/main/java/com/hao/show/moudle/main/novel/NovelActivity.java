package com.hao.show.moudle.main.novel;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.hao.lib.Util.PopUtils;
import com.hao.lib.base.Rx.Rx;
import com.hao.lib.base.Rx.RxMessage;
import com.hao.lib.view.RecycleView;
import com.hao.show.R;
import com.hao.show.base.App;
import com.hao.show.base.BaseActivity;
import com.hao.show.db.manage.DBManager;
import com.hao.show.moudle.main.novel.Entity.NovelClassify;
import com.hao.show.moudle.main.novel.Entity.NovelListItemContent;
import com.hao.show.moudle.main.novel.Entity.NovelPage;
import com.hao.show.moudle.main.novel.adapter.NovelListAdapter;
import com.hao.show.moudle.main.novel.adapter.TextNovelAdapter;
import com.hao.show.spider.SpiderNovelFromBiQu;
import com.hao.show.spider.SpiderUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

public class NovelActivity extends BaseActivity {
    RecycleView novel_list;
    RecycleView novel_classify_list;
    TwinklingRefreshLayout refresh;
    EditText search_content;
    RecyclerView recyclerView;
    PopupWindow pop;

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
                //保存或者更新当前小说的信息
                NovelListItemContent novelListItemContent = ((NovelListAdapter) novel_list.getAdapter()).getNowDate().getNovelListItemContentList().get(position);
                Intent intent = new Intent(NovelActivity.this, NovelDetailActivity.class);
                intent.putExtra("novel", DBManager.addNovel(novelListItemContent));
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

        search_content = findViewById(R.id.search_content);
        search_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    if (pop != null && pop.isShowing()) {
                        pop.dismiss();
                    }
                    return;
                }
                if (pop != null) {
                    recyclerView.getAdapter().notifyDataSetChanged();
                    ((TextNovelAdapter) recyclerView.getAdapter()).setList(DBManager.selectNovelbyStr(s.toString()));
                    ((RecyclerView) pop.getContentView()).getAdapter().notifyDataSetChanged();
                    if (!pop.isShowing()) {
                        pop.showAsDropDown(search_content);
                    }
                } else {
                    recyclerView = (RecyclerView) LayoutInflater.from(App.getInstance()).inflate(R.layout.recycle_layout, null);
                    recyclerView.setBackgroundResource(R.color.white);
                    recyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance()));
                    recyclerView.addItemDecoration(new DividerItemDecoration(App.getInstance(), DividerItemDecoration.VERTICAL));
                    recyclerView.setAdapter(new TextNovelAdapter(App.getInstance(), DBManager.selectNovelbyStr(s.toString()))
                            .setItemClickLisener(new TextNovelAdapter.OnItemClickListener() {
                                @Override
                                public void itemClick(int position, View view, Object object) {
                                    if (pop != null) {
                                        pop.dismiss();
                                    }
                                    //保存或者更新当前小说的信息
                                    NovelListItemContent novelListItemContent = ((TextNovelAdapter) recyclerView.getAdapter()).getDate().get(position);
                                    Intent intent = new Intent(NovelActivity.this, NovelDetailActivity.class);
                                    intent.putExtra("novel", DBManager.addNovel(novelListItemContent));
                                    startActivity(intent);
                                }
                            }));
                    PopUtils.getInstance().createPop(search_content, recyclerView).showAsViewDown(search_content);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUrlDate();
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
    }

    /**
     * 抓取网页的主题
     */
    private void getUrlDate() {
        Rx.getInstance().addRxMessage(new RxMessage() {
            @Override
            public void rxDo(final Object tag, final Object o) {
                if (tag instanceof String) {
                    setViewDate((String) tag, o);
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
            classifies.remove(0);
            classifies.remove(0);
            classifies.remove(classifies.size() - 1);
            classifies.remove(classifies.size() - 1);
            ((NovelClassifyListAdapter) novel_classify_list.getAdapter()).update(classifies);
        } else if (tag.equals("novel_detail")) {
            refresh.setEnableRefresh(true);
            closeDrawer();
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
