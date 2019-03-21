package com.hao.show.moudle.main.novel;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.hao.mi2.base.Rx.Rx;
import com.hao.mi2.base.Rx.RxMessage;
import com.hao.show.R;
import com.hao.show.base.BaseActivity;
import com.hao.show.moudle.main.novel.Entity.NovelClassify;
import com.hao.show.moudle.main.novel.Entity.NovelDetail;
import com.hao.show.spider.SpiderNovelFromBiQu;
import com.hao.show.spider.SpiderUtils;

import java.util.List;

public class NovelActivity extends BaseActivity {
    RecyclerView novel_list;
    RecyclerView novel_classify_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            }
        }));
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
                setContentList((NovelClassify) o);
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
        SpiderUtils.getHtml(SpiderNovelFromBiQu.BiQuMainUrl, "html");
    }

    /**
     * 抓取标题栏的数据
     *
     * @param o
     */
    public void setViewDate(String tag, Object o) {
        if (tag.equals("html")) {
            List<NovelClassify> classifies = SpiderNovelFromBiQu.getClassify((String) o);
            ((NovelClassifyListAdapter) novel_classify_list.getAdapter()).update(classifies);
        } else if (tag.equals("novel_detail")) {
            List<NovelDetail> novelDetails = SpiderNovelFromBiQu.getNovelDetail((String) o);
            Log.i("查询的数据", novelDetails.size() + "");
            ((NovelListAdapter) novel_list.getAdapter()).update(novelDetails);
        }
    }


    /**
     * 抓取当前页的数据
     *
     * @param novelClassify
     */
    public void setContentList(NovelClassify novelClassify) {
        SpiderUtils.getHtml(novelClassify.getUrl(), "novel_detail");
    }

}
