package com.hao.show.base;


import com.hao.lib.base.MI2App;

public class App extends MI2App {
    //用于控制数据库更新导致序列化 ID出现错误
    public static final long serialVersionUID = 1L;
    static App app;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }


}
