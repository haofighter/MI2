package com.hao.mi2.base;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

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


    private List<MI2Activity> activities = new ArrayList<>();
    private MI2Activity activitie = null;

    public MI2Activity getNowActivitie() {
        return activitie;
    }

    public void addActivity(MI2Activity baseActivity) {
        activities.add(baseActivity);
        activitie = baseActivity;
    }

    public void removeActivity(MI2Activity baseActivity) {
        activities.add(baseActivity);
    }


    //finish所有的存于APP的activity
    public void finishAll() {
        finishActivitys(null);
    }

    //finish掉带有同一tag的activity
    public void finishActivitys(String tag) {
        for (int i = 0; i < activities.size(); i++) {
            if (tag == null && tag.equals("")) {
                activities.get(i).finish();
            } else {
                if (activities.get(i).getMI2TAG().equals(tag)) {
                    activities.get(i).finish();
                }
            }
        }
    }


    //记录最后一次点击事件的时间
    private long beforClickTime = 0;

    //判断是否在1秒之内重复点击
    public boolean checkFastClick() {
        long time = System.currentTimeMillis() - beforClickTime;
        beforClickTime = System.currentTimeMillis();
        return time < 1000;
    }
}
