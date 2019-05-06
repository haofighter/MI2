package com.xb.haikou.db.manage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.xb.haikou.db.dao.ConfigDao;
import com.xb.haikou.db.dao.DaoMaster;
import org.greenrobot.greendao.database.Database;


/**
 * TODO：更新数据库
 */


public class DBHelper extends DaoMaster.OpenHelper {

    DBHelper(Context context, String name) {
        super(context, name);
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        update(db, oldVersion, newVersion);
    }

    private void update(SQLiteDatabase db, int oldVersion, int newVersion) {
        //把需要管理的数据库表DAO作为最后一个参数传入到方法中
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, ConfigDao.class);
    }
}
