package com.hao.show.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.hao.mi2.base.MI2Activity;
import com.hao.show.R;

public abstract class BaseActivity extends MI2Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initViewID() == -1 && initView() == null) {
            Log.w("布局填充", "可使用initViewID()或initView()来填充布局，也可使用setContentView()");
        } else if (initViewID() != -1) {
            setContentView(initViewID());
        } else {
            setContentView(initView());
        }
        addLoading(LayoutInflater.from(this).inflate(R.layout.view_loading_layout, null));
        findView();
    }


    @Override
    public View addLoading(View v) {
        return super.addLoading(v);
    }

    protected abstract void findView();

    @Override
    protected void initDrawView(View view) {

    }

    public int initViewID() {
        return -1;
    }

    public View initView() {
        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
