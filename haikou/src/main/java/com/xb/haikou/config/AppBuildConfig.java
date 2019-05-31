package com.xb.haikou.config;

import android.util.Log;
import com.example.zhoukai.modemtooltest.ModemToolTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hao.lib.Util.FileUtils;
import com.xb.haikou.base.App;
import com.xb.haikou.config.param.BuildConfigParam;
import com.xb.haikou.db.manage.DBCore;
import com.xb.haikou.db.manage.DBManager;

import java.util.List;

/**
 * APP打包的时候生成的配置参数
 */
public class AppBuildConfig {


    //获取当前的配置信息
    public static void createConfig(int city) {
        String config = FileUtils.readAssetsFile("config.json", App.getInstance().getApplicationContext());
        Log.i("config", config);
        List<BuildConfigParam> configParam = new Gson().fromJson(config, new TypeToken<List<BuildConfigParam>>() {
        }.getType());
        BuildConfigParam configBean = configParam.get(city);
        configBean.setUpdateTime(System.currentTimeMillis());
        DBCore.getDaoSession().getBuildConfigParamDao().deleteAll();
        DBCore.getDaoSession().insert(configBean);
        Log.i("config", "当前配置:" + configBean.toString());

        initSn();//获取SN并填充
    }


    //初始化SN号
    private static void initSn() {
        String item;
        try {
            item = ModemToolTest.getItem(7);
        } catch (Exception e) {
            item = "default";
        }
        AppRunConfigEntity config = DBManager.checkConfig();
        try {
            if (config == null || !config.getPosSn().equals(item)) {
                config.setPosSn(item == null ? "default" : item);
            }
        } catch (Exception e) {
            config = new AppRunConfigEntity();
            config.setPosSn(item == null ? "default" : item);
        }
        DBManager.updateConfig(config);
    }


}
