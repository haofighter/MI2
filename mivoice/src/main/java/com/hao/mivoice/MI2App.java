package com.hao.mivoice;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class MI2App extends Application {
    static MI2App app;

    public static MI2App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

    }

    //记录最后一次点击事件的时间
    private long beforClickTime = 0;

    //判断是否在1秒之内重复点击
    public boolean checkFastClick() {
        long time = System.currentTimeMillis() - beforClickTime;
        beforClickTime = System.currentTimeMillis();
        return time < 1000;
    }


    public void gotoSetting() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(intent);
    }
}
