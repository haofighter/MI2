package com.xb.haikou.config;

import android.content.Intent;
import android.content.res.AssetManager;
import android.text.TextUtils;
import com.hao.lib.Util.ThreadUtils;
import com.szxb.jni.libszxb;
import com.xb.haikou.BuildConfig;
import com.xb.haikou.MainActivity;
import com.xb.haikou.base.App;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.util.BusToast;

public class InitConfig {
    public static void initBin() {
        ThreadUtils.createSingle("bin").execute(new Runnable() {
            @Override
            public void run() {
                String binName = BuildConfig.BIN_NAME;
                AppConfig config = DBManager.checkConfig();
                if (config == null || !TextUtils.equals(config.getBinVer(), binName)) {
                    AssetManager ass = App.getInstance().getAssets();
                    int k = libszxb.ymodemUpdate(ass, binName);
                    if (k == 0) {
                        config.setBinVer(binName);
                        DBManager.updateConfig(config);
                    }
                    App.appPreload.binLoadSucess = true;
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    BusToast.showToast(App.getInstance(), "固件更新成功", true);
                    checkConfig();
                }
            }
        });
    }


    private static void checkConfig() {
        if (App.appPreload.sucesse()) {
            App.getInstance().getNowActivitie().startActivity(new Intent(App.getInstance(), MainActivity.class));
            App.getInstance().getNowActivitie().finish();
        }
    }
}
