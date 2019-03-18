package com.hao.show.moudle.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.hao.mi2.base.MI2Activity;
import com.hao.mi2.view.NavigationBar;
import com.hao.show.R;
import com.hao.show.moudle.view.BottomView;
import com.hao.show.moudle.view.adapter.BottomDate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MI2Activity {

    private LinearLayout main;
    private ViewPager vp_main;
    private BottomView bottomView;
    private NavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initView();
        initContent();
    }

    private void initView() {
        vp_main.setAdapter(new MainVPAdapter());
    }

    private void findView() {
        main = findViewById(R.id.main);
        navigationBar = findViewById(R.id.navigationBar);
        navigationBar.setVisibility(View.GONE);
        vp_main = findViewById(R.id.vp_main);
        bottomView = ((BottomView) findViewById(R.id.bottom_view)).setBottomClickListener(new BottomView.BottomViewClickListener() {
            @Override
            public void click(int item, View v) {

            }
        }).setDate(initBottomViewDate()).setViewBackground(R.color.blue);
//        main.addView(bottomView);
    }

    private void initContent() {
        MainVPAdapter adapter = new MainVPAdapter();
        List<View> views = new ArrayList<>();
        views.add(LayoutInflater.from(this).inflate(R.layout.content_first, null));
        adapter.setViews(views);
        vp_main.setAdapter(adapter);
    }

    private List<BottomDate> initBottomViewDate() {
        List<BottomDate> bottomDateList = new ArrayList<>();
//        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.icon_loading_footbar_0).setUncheckColor(R.color.colorPrimary).setCheckColor(R.color.colorAccent).setTitle("菜单1"));
//        bottomDateList.add(new BottomDate());
//        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back));
//        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.back));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.icon_loading_footbar_0).setUncheckColor(R.color.colorPrimary));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.icon_loading_footbar_0).setUncheckColor(R.color.colorPrimary).setTipNum(3));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.icon_loading_footbar_0).setUncheckColor(R.color.colorPrimary).setTipNum(3));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.icon_loading_footbar_0).setUncheckColor(R.color.colorPrimary).setTipNum(3));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.icon_loading_footbar_0).setUncheckColor(R.color.colorPrimary).setTipNum(3));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.icon_loading_footbar_0).setUncheckColor(R.color.colorPrimary).setTipNum(3));
        return bottomDateList;
    }

}
