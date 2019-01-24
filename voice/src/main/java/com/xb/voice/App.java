package com.xb.voice;

import android.app.Application;
import com.xb.voice.task.LoopScanTask;
import com.xb.voice.task.ThreadScheduledExecutorUtil;

import java.util.concurrent.TimeUnit;

public class App extends Application {
   static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        ThreadScheduledExecutorUtil.getInstance().getService().scheduleAtFixedRate(new LoopScanTask(),1,1,TimeUnit.SECONDS);
    }

    public static App  getInstance(){
        if(app==null){
            app=new App();
        }
        return app;
    }


}
