package com.xb.haikou.base;

import com.hao.lib.base.MI2App;
import com.xb.haikou.config.AppPreload;
import com.xb.haikou.db.manage.DBCore;

public class App extends MI2App {
    App instance;
    public final static AppPreload appPreload = new AppPreload();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DBCore.init(this);
    }
}
