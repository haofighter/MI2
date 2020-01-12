package com.hao.show.moudle.main;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import com.hao.lib.Util.ToastUtils;
import com.hao.lib.view.NavigationBar;
import com.hao.show.R;
import com.hao.show.base.BaseActivity;
import com.hao.show.moudle.face.FaceActivity;
import com.hao.show.moudle.main.mainview.MainFristView;
import com.hao.show.moudle.main.mainview.MainSecondView;
import com.hao.show.moudle.main.mainview.MainThreeView;
import com.hao.show.moudle.main.mainview.MainView;
import com.hao.show.moudle.main.novel.NovelActivity;
import com.hao.show.moudle.view.BottomView;
import com.hao.show.moudle.view.adapter.BottomDate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout main;
    private ViewPager vp_main;
    private BottomView bottomView;
    private NavigationBar navigationBar;
    long beforBackTime;
    MainVPAdapter adapter;
    RadioButton mi_collect;
    RadioButton mi_history;
    RadioButton mi_recommend;

    @Override
    public int initViewID() {
        return R.layout.activity_main;
    }

    protected void findView() {
        main = findViewById(R.id.main);
        mi_collect = findViewById(R.id.mi_collect);
        mi_history = findViewById(R.id.mi_history);
        mi_recommend = findViewById(R.id.mi_recommend);
        navigationBar = findViewById(R.id.navigationBar);
        navigationBar.setVisibility(View.GONE);
        vp_main = findViewById(R.id.vp_main);
        bottomView = ((BottomView) findViewById(R.id.bottom_view));
        initContent();
        initListener();
    }

    private void initContent() {
        adapter = new MainVPAdapter();
        List<MainView> views = new ArrayList<>();
        views.add(new MainFristView(this));
        views.add(new MainSecondView(this));
        views.add(new MainThreeView(this));
        adapter.setViews(views);
        vp_main.setAdapter(adapter);
    }


    public void initListener() {
        bottomView.setBottomClickListener(new BottomView.BottomViewClickListener() {
            @Override
            public void click(int item, View v) {
                Intent intent = new Intent();
                switch (item) {
                    case 0:
                        setBackGround(getResources().getDrawable(R.mipmap.cloud));
                        break;
                    case 1:
                        intent.setClass(MainActivity.this, NovelActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this, FaceActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        }).setDate(initBottomViewDate()).setViewBackground(R.color.white);

        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Log.i("scorllview", "滑动：" + v + "   i=" + i + "       i1=" + i1);
                if (v <= 1) {
                }
            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        mi_collect.setChecked(true);
                        break;
                    case 1:
                        mi_recommend.setChecked(true);
                        break;
                    case 2:
                        mi_history.setChecked(true);
                        break;
                }
                adapter.getView(i).refresh();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mi_collect.setOnClickListener(this);
        mi_recommend.setOnClickListener(this);
        mi_history.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.getView(vp_main.getCurrentItem()).refresh();
    }

    private List<BottomDate> initBottomViewDate() {
        List<BottomDate> bottomDateList = new ArrayList<>();
//        bottomDateList.add(new BottomDate().setTitle("首页").setDefIcon(R.mipmap.home).setCheckIcon(R.mipmap.home_check).setUncheckColor(R.color.colorPrimary));
        bottomDateList.add(new BottomDate().setTitle("搜索").setDefIcon(R.mipmap.search).setCheckIcon(R.mipmap.search).setUncheckColor(R.color.colorPrimary));
        bottomDateList.add(new BottomDate().setTitle("设置").setDefIcon(R.mipmap.setting).setCheckIcon(R.mipmap.setting).setUncheckColor(R.color.colorPrimary));
        return bottomDateList;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - beforBackTime > 3000) {
            beforBackTime = System.currentTimeMillis();
            ToastUtils.INSTANCE.showMessage("重复点击退出程序");
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mi_collect:
                vp_main.setCurrentItem(0);
                break;
            case R.id.mi_recommend:
                vp_main.setCurrentItem(1);
                break;
            case R.id.mi_history:
                vp_main.setCurrentItem(2);
                break;
        }
    }
}
