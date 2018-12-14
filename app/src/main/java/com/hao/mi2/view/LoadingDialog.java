package com.hao.mi2.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.hao.mi2.R;
import com.hao.mi2.base.MI2App;


/**
 * @author sushuo
 * @date 2015年6月10日
 */
public class LoadingDialog {
    /**
     * 旋转动画的时间
     */
    static final int ROTATION_ANIMATION_DURATION = 1200;
    /**
     * 动画插值
     */
    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
    Window window;
    Dialog d;
    private ImageView imageView;
    //	private Animation animation;
    private TextView textView;

    private static class LoadingHelper {
        private final static LoadingDialog load = new LoadingDialog();
    }

    public static LoadingDialog getInstance() {
        return LoadingHelper.load;
    }


    //对Dailog进行初始化
    private void initDailog() {
        if (d == null) {
            View view = View.inflate(MI2App.getInstance().getNowActivitie(), R.layout.common_loadingdialog, null);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.load_text);
//		textView.setText(str);
//        animation = AnimationUtils.loadAnimation(context, R.anim.loading);
//        imageView.startAnimation(animation);

//        		float pivotValue = 0.5f; // SUPPRESS CHECKSTYLE
//        		float toDegree = 720.0f; // SUPPRESS CHECKSTYLE
//        		RotateAnimation mRotateAnimation =
//        			new RotateAnimation(0.0f, toDegree, Animation.RELATIVE_TO_SELF, pivotValue, Animation.RELATIVE_TO_SELF, pivotValue);
//        		mRotateAnimation.setFillAfter(true);
//        		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
//        		mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
//        		mRotateAnimation.setRepeatCount(Animation.INFINITE);
//        		mRotateAnimation.setRepeatMode(Animation.RESTART);
//        		imageView.startAnimation(mRotateAnimation);
            AnimationDrawable ad = (AnimationDrawable) MI2App.getInstance().getNowActivitie().getResources().getDrawable(R.drawable.anim_loading_progress_round);
            imageView.setBackgroundDrawable(ad);
            ad.start();

            d = new Dialog(MI2App.getInstance().getNowActivitie(), R.style.dialog);// 加入样式
            d.setCanceledOnTouchOutside(false);
            window = d.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    public LoadingDialog show() {
        if (null != d && !d.isShowing()) {
            d.show();
        }
        return this;
    }

    public LoadingDialog show(String content) {
        initDailog();
        if (null != d && !d.isShowing()) {
            textView.setText(content);
            d.show();
        }
        return this;
    }

    public LoadingDialog dismiss() {
        initDailog();
        if (d != null) {
            d.dismiss();
        }
        return this;
    }

    public LoadingDialog setCancel() {
        if (d != null) {
            d.setCancelable(false);
        }
        return this;
    }

}
