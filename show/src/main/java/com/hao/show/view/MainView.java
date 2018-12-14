package com.hao.show.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import com.hao.show.R;

public class MainView {
    Activity activity;
    View v;

    public MainView(Activity activity) {
        this.activity = activity;
        init();
    }

    private void init() {
        v = LayoutInflater.from(activity).inflate(R.layout.main_view, null);
    }


}
