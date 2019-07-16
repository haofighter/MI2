package com.hao.show.moudle.main;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.hao.lib.Util.DataUtils;
import com.hao.lib.Util.ImageUtils;
import com.hao.show.R;
import com.hao.show.base.App;
import com.hao.show.moudle.main.mainview.MainView;

import java.util.List;

public class MainVPAdapter extends PagerAdapter {
    List<MainView> views;

    public void setViews(List<MainView> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public MainView getView(int position) {
        return views.get(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(views.get(position).getMainView());
        return views.get(position).getMainView();
    }

}
