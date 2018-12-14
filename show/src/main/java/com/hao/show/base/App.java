package com.hao.show.base;

import com.hao.mi2.base.MI2App;


public class App extends MI2App {
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
