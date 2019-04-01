package com.hao.mi2.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;

public class MiClickViewPage extends ViewPager {
    int screenWidth = 0;
    int screenHeight = 0;

    public MiClickViewPage(@NonNull Context context) {
        this(context, null);
    }

    public MiClickViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float downX = 0;
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downX = ev.getX();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            float moveX = ev.getX() - downX;
            if (downX > screenWidth - 100 && moveX < 10) {
                if (getCurrentItem() + 1 < getAdapter().getCount()) {
                    setCurrentItem(getCurrentItem() + 1);
                    return true;
                }
            } else if (downX < 100 && moveX < 10) {
                if (getCurrentItem() > 0) {
                    setCurrentItem(getCurrentItem() - 1);
                    return true;
                } else {

                }
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

}
