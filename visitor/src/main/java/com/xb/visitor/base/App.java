package com.xb.visitor.base;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import com.hao.lib.base.BackCall;
import com.hao.lib.base.MI2App;
import com.squareup.picasso.Picasso;
import com.xb.visitor.Mqtt.GetPushService;
import com.xb.visitor.db.manage.DBCore;
import com.xb.visitor.moudle.MainActivity;

public class App extends MI2App {
    static App app;
    ServiceConnection mConnection;
    GetPushService.MBinder mBinder;

    public GetPushService.MBinder getmBinder() {
        return mBinder;
    }

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        DBCore.init(this);
        //绑定服务，开启服务
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mBinder = (GetPushService.MBinder) iBinder;  //数据绑定
                mBinder.setCallBack(new BackCall<Object>() {
                    @Override
                    public void call(final Object o) {
                        ((MainActivity) getNowActivitie()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((MainActivity) getNowActivitie()).in_image.setImageBitmap((Bitmap) o);
                            }
                        });
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mConnection = null;
            }
        };
        Intent intent = new Intent(this, GetPushService.class);
        startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    public void initPicasso() {

        Picasso picasso = new Picasso.Builder(this).build();
        picasso.setLoggingEnabled(true);
    }

}
