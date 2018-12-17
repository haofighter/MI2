package com.hao.show.moudle;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import com.hao.mi2.base.MI2Activity;
import com.hao.show.R;
import com.hao.show.moudle.view.BottomView;
import com.hao.show.moudle.view.adapter.BottomDate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MI2Activity implements View.OnClickListener {

    private LinearLayout main;
    private ViewPager vp_main;
    private View bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView() {
        main = findViewById(R.id.main);
        vp_main = findViewById(R.id.vp_main);
        bottomView = new BottomView(this, initBottomViewDate()).setOnClickListener(this).setViewBackground(R.color.blue);
        main.addView(bottomView);
    }

    private List<BottomDate> initBottomViewDate() {
        List<BottomDate> bottomDateList = new ArrayList<>();
        bottomDateList.add(new BottomDate(null, R.color.white, "菜单1", 0,false));
        bottomDateList.add(new BottomDate(null, R.color.white, "菜单2", 0,true));
        bottomDateList.add(new BottomDate(R.mipmap.back,R.mipmap.back,R.color.white,R.color.black,"菜单3",0,true));
        bottomDateList.add(new BottomDate(R.mipmap.back,R.mipmap.back,R.color.white,R.color.black,"菜单4",2,false));
        bottomDateList.add(new BottomDate(null,null,R.color.white,R.color.black,"菜单4",2,false));
        bottomDateList.add(new BottomDate(null,null,R.color.white,R.color.black,"菜单4",2,false));
        return bottomDateList;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
