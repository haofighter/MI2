package com.hao.mi2.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.hao.mi2.R;
import com.hao.mi2.Util.StatusBarUtil;
import com.hao.mi2.help.DrawerHelper;

public class MI2Activity extends AppCompatActivity implements DrawerHelper {
    protected String MI2TAG = "MI2Activity";
    public static final String PERMISSION_MI = "com.hao.MI";

    public String getMI2TAG() {
        return MI2TAG;
    }

    /**
     * 添加loading布局
     */
    public View addLoading(View v) {

        if (v != null)
            addLoading(v);
        return loading;
    }

    /**
     * 显示加载布局
     */
    public void showLoading() {
        if (loading == null) {
            new NullPointerException("还未加载布局,请在布局加载完成后使用");
            return;
        }
        loading.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏加载布局
     */
    public void dismisLoading() {
        if (loading == null) {
            new NullPointerException("还未加载布局,请在布局加载完成后使用");
            return;
        }
        loading.setVisibility(View.GONE);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    View loading;
    DrawerLayout drawer;
    RelativeLayout drawer_content;

    @Override
    public void setContentView(View view) {
        drawer = (DrawerLayout) LayoutInflater.from(this).inflate(R.layout.mi2_activity, null);
        loading = drawer.findViewById(R.id.loading);
        drawer_content = drawer.findViewById(R.id.drawer_content);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((RelativeLayout) drawer.findViewById(R.id.content)).addView(view);
        super.setContentView(drawer);
    }

    //LOCK_MODE_UNLOCKED = 0; 可拖拽
    //LOCK_MODE_LOCKED_CLOSED = 1; 关闭并不可手势滑动,可调用方法
    //LOCK_MODE_LOCKED_OPEN = 2; 打开不可手势滑动,可调用方法
    //LOCK_MODE_UNDEFINED = 3;初始状态
    public void setDrawerLockMode(int drawerType) {
        drawer.setDrawerLockMode(drawerType);
    }

    public void closeDrawer() {
        drawer.closeDrawers();
    }

    @Override
    public void setDrawerContent(View view) {
        view.measure(0, 0);
        drawer_content.measure(0, 0);
        Log.i("布局的宽", "view.getMeasuredWidth()=" + view.getMeasuredWidth());
        drawer_content.removeAllViews();
        drawer_content.addView(view);
        DrawerLayout.LayoutParams dLayoutParams = new DrawerLayout.LayoutParams(view.getMeasuredWidth(), DrawerLayout.LayoutParams.MATCH_PARENT);
        dLayoutParams.gravity = Gravity.LEFT;
        drawer_content.setLayoutParams(dLayoutParams);
    }

    public void openDrawer() {
        drawer.openDrawer(Gravity.LEFT);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MI2App.getInstance().removeActivity(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (checkCallingOrSelfPermission(PERMISSION_MI) != PackageManager.PERMISSION_GRANTED) {
            throw new SecurityException("继承此activity需要权限");
        }
        StatusBarUtil.setTranslucent(this);
        super.onCreate(savedInstanceState);
        MI2App.getInstance().addActivity(this);
    }

    /**
     * 便捷无参数跳转
     *
     * @param a 需要跳转的activityclass
     */
    public void startActivity(Class<? extends Activity> a) {
        startActivity(new Intent(this, a));
    }


}
