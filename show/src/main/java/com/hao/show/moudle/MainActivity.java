package com.hao.show.moudle;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import com.hao.mi2.base.MI2Activity;
import com.hao.mi2.view.NavigationBar;
import com.hao.show.R;
import com.hao.show.moudle.view.BottomView;
import com.hao.show.moudle.view.adapter.BottomDate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MI2Activity implements View.OnClickListener {

    private LinearLayout main;
    private ViewPager vp_main;
    private BottomView bottomView;
    private NavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView() {
        main = findViewById(R.id.main);
        navigationBar = findViewById(R.id.navigationBar);
        navigationBar.setVisibility(View.GONE);
        vp_main = findViewById(R.id.vp_main);
        bottomView = ((BottomView) findViewById(R.id.bottom_view)).setDate(initBottomViewDate()).setBOnClickListener(this).setViewBackground(R.color.blue);
//        main.addView(bottomView);
    }

    private List<BottomDate> initBottomViewDate() {
        List<BottomDate> bottomDateList = new ArrayList<>();
        bottomDateList.add(new BottomDate());
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.back));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.back).setUncheckColor(R.mipmap.ic_launcher));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.back).setUncheckColor(R.mipmap.ic_launcher).setTitle("菜单1"));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.back).setUncheckColor(R.mipmap.ic_launcher).setTipNum(3));
        return bottomDateList;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
