package com.hao.show.moudle.main.mainview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.hao.show.R;

public class MainFristView implements MainView {
    private View mainsecondView;

    public MainFristView(Context context) {
        if (mainsecondView == null) {
            mainsecondView = LayoutInflater.from(context).inflate(R.layout.content_frist, null);
        }
    }


    @Override
    public void refresh() {
    }


    @Override
    public View getMainView() {
        return mainsecondView;
    }


}
