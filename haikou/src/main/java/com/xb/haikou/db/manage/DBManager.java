package com.xb.haikou.db.manage;


import com.xb.haikou.config.AppConfig;
import com.xb.haikou.db.dao.AppConfigDao;
import com.xb.haikou.db.dao.ConfigDao;

public class DBManager {

    public static void updateConfig(AppConfig config) {
        AppConfig oldConfig = checkConfig();
        if (oldConfig != null) {
            config.setId(oldConfig.getId());
            if (config.getBusNo() == null || config.getBusNo().equals("")) {
                config.setBusNo(oldConfig.getBusNo());
            }
            if (config.getBinVer() == null || config.getBinVer().equals("")) {
                config.setBinVer(oldConfig.getBinVer());
            }
            if (config.getLineNo() == null || config.getLineNo().equals("")) {
                config.setLineNo(oldConfig.getLineNo());
            }
            if (config.getPosSn() == null || config.getPosSn().equals("")) {
                config.setPosSn(oldConfig.getPosSn());
            }
        }
        DBCore.getDaoSession().insert(config);
    }

    public static AppConfig checkConfig() {
        return DBCore.getDaoSession().getAppConfigDao().queryBuilder().orderDesc(AppConfigDao.Properties.UpdateTime).limit(1).unique();
    }
}