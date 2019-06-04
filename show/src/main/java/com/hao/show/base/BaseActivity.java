package com.hao.show.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.hao.lib.Util.ToastUtils;
import com.hao.lib.base.MI2Activity;
import com.hao.lib.base.Rx.Rx;
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
        checkPromission();
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
    protected void onPause() {
        super.onPause();
        Rx.getInstance().removeAll();
    }

    public void checkPromission() {
        String[] promissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
        initPromission(promissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == -1) {
                if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE) || permissions.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ToastUtils.INSTANCE.showMessage("手机读写权限开启失败，部分功能受限");
                } else if (permissions[i].equals(Manifest.permission.CAMERA)) {
                    ToastUtils.INSTANCE.showMessage("手机相机权限开启失败，部分功能受限");
                } else if (permissions[i].equals(Manifest.permission.ACCESS_WIFI_STATE)) {
                    ToastUtils.INSTANCE.showMessage("手机wifi权限开启失败，部分功能受限");
                }

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
