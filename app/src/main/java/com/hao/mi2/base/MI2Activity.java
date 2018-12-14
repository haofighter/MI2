package com.hao.mi2.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.hao.mi2.R;
import com.hao.mi2.Util.StatusBarUtil;

public abstract class MI2Activity extends AppCompatActivity {
    protected String MI2TAG = "MI2Activity";

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

    @Override
    public void setContentView(View view) {
        View v = LayoutInflater.from(this).inflate(R.layout.loading, null);
        loading = ((RelativeLayout) v.findViewById(R.id.loading));
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((RelativeLayout) v.findViewById(R.id.content)).addView(view);
        super.setContentView(v);
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
        StatusBarUtil.setTranslucent(this);
        super.onCreate(savedInstanceState);
        MI2App.getInstance().addActivity(this);

    }

    /**
     * 便捷无参数跳转
     * @param a 需要跳转的activityclass
     */
    public void stratActivityNoEl(Class<? extends Activity> a) {
        startActivity(new Intent(this, a));
    }


}
