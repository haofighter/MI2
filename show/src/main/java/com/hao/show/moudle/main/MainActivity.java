package com.hao.show.moudle.main;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.hao.lib.view.NavigationBar;
import com.hao.show.R;
import com.hao.show.base.BaseActivity;
import com.hao.show.moudle.face.FaceActivity;
import com.hao.show.moudle.main.mainview.MainSecondView;
import com.hao.show.moudle.main.novel.NovelActivity;
import com.hao.show.moudle.view.BottomView;
import com.hao.show.moudle.view.adapter.BottomDate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private LinearLayout main;
    private ViewPager vp_main;
    private BottomView bottomView;
    private NavigationBar navigationBar;

    @Override
    public int initViewID() {
        return R.layout.activity_main;
    }


    protected void findView() {
        main = findViewById(R.id.main);
        navigationBar = findViewById(R.id.navigationBar);
        navigationBar.setVisibility(View.GONE);
        vp_main = findViewById(R.id.vp_main);
        vp_main.setAdapter(new MainVPAdapter());
        bottomView = ((BottomView) findViewById(R.id.bottom_view)).setBottomClickListener(new BottomView.BottomViewClickListener() {
            @Override
            public void click(int item, View v) {
                if (item == 1) {
                    Intent intent = new Intent(MainActivity.this, NovelActivity.class);
                    startActivity(intent);
                } else if (item == 2) {
                    Intent intent = new Intent(MainActivity.this, FaceActivity.class);
                    startActivity(intent);
                }
            }
        }).setDate(initBottomViewDate()).setViewBackground(R.color.blue);
//        main.addView(bottomView);
        initContent();
    }

    private void initContent() {
        MainVPAdapter adapter = new MainVPAdapter();
        List<View> views = new ArrayList<>();
        views.add(LayoutInflater.from(this).inflate(R.layout.content_first, null));
        views.add(new MainSecondView(this).getMainsecondView());
        adapter.setViews(views);
        vp_main.setAdapter(adapter);
    }

    private List<BottomDate> initBottomViewDate() {
        List<BottomDate> bottomDateList = new ArrayList<>();
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.icon_loading_footbar_0).setUncheckColor(R.color.colorPrimary).setTipNum(3));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.icon_loading_footbar_0).setUncheckColor(R.color.colorPrimary).setTipNum(3));
        bottomDateList.add(new BottomDate().setDefIcon(R.mipmap.back).setCheckIcon(R.mipmap.icon_loading_footbar_0).setUncheckColor(R.color.colorPrimary).setTipNum(3));
        return bottomDateList;
    }

}
