package com.xb.haikou.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.multidex.MultiDex;
import android.util.Log;
import com.bluering.sdk.qrcode.jtb.JTBQRCodeSDK;
import com.hao.lib.base.MI2App;
import com.lilei.tool.tool.IToolInterface;
import com.szxb.java8583.core.Iso8583Message;
import com.szxb.java8583.module.SignIn;
import com.szxb.java8583.module.manager.BusllPosManage;
import com.szxb.mlog.SLog;
import com.xb.haikou.BuildConfig;
import com.xb.haikou.config.AppBuildConfig;
import com.xb.haikou.config.AppPreload;
import com.xb.haikou.db.manage.DBCore;
import com.xb.haikou.moudle.function.unionpay.UnionPay;
import com.xb.haikou.moudle.function.unionpay.config.UnionConfig;
import com.xb.haikou.moudle.function.unionpay.config.UnionPayManager;
import com.xb.haikou.record.RecordUpload;
import com.xb.haikou.voice.SoundPoolUtil;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.URLConnectionNetworkExecutor;

import java.io.File;
import java.io.IOException;

public class App extends MI2App {
    static App instance;
    //服务操作
    private IToolInterface mService;
    public final static AppPreload appPreload = new AppPreload();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DBCore.init(this);
        SoundPoolUtil.init(this);
        initConfig();
        File file = new File("/data/data/com.xb.haikou/files/");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.i("错误", "onCreate(App.java:54)" + e.getMessage());
            }
        }
        if (JTBQRCodeSDK.initSDK("/data/data/com.xb.haikou/")) {
            Log.i("交通部", "初始化成功");
        } else {
            Log.i("交通部", "初始化失败");
        }
        clearDateBase();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static App getInstance() {
        return instance;
    }

    private void initConfig() {
        AppBuildConfig.createConfig(BuildConfig.CITY);
        Task.runTask();

        //初始化网络请求
        NoHttp.initialize(InitializationConfig.newBuilder(this)
                .networkExecutor(new URLConnectionNetworkExecutor())
                .connectionTimeout(15 * 1000)
                .build());

        UnionPayManager unionPayManager = new UnionPayManager();
        BusllPosManage.init(unionPayManager);
        if (BuildConfig.isTest) {
            BusllPosManage.getPosManager().setMachId("308640041110003");
            BusllPosManage.getPosManager().setKey("796D684C7C1561BC3D494C911FBA92C7");
            BusllPosManage.getPosManager().setPosSn("10020831");

            SLog.d("Util(updateUnionParam.java:334)银联参数设置成功>>>马上签到");
            BusllPosManage.getPosManager().setTradeSeq();
            Iso8583Message message = SignIn.getInstance().message(BusllPosManage.getPosManager().getTradeSeq());
            SLog.d("LoopCard(run.java:242)" + message.toFormatString());

            UnionPay.getInstance().exeSSL(UnionConfig.SIGN, message.getBytes(), true);
        }
        initService();

    }

    //连接服务
    private void initService() {
        Intent i = new Intent();
        i.setAction("com.lypeer.aidl");
        i.setPackage("com.lilei.tool.tool");
        boolean ret = bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public IToolInterface getmService() {
        return mService;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IToolInterface.Stub.asInterface(service);
        }
    };

    public String getPakageVersion() {
        try {
            return getPackageManager().getPackageInfo(
                    "com.xb.haikou", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 用于防止数据库被撑爆
     */
    public void clearDateBase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RecordUpload.clearDateBase();
            }
        }).start();
    }
}
