package com.hao.show.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hao.show.moudle.main.novel.Entity.NovelClassify;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NOVEL_CLASSIFY".
*/
public class NovelClassifyDao extends AbstractDao<NovelClassify, Long> {

    public static final String TABLENAME = "NOVEL_CLASSIFY";

    /**
     * Properties of entity NovelClassify.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property NcId = new Property(0, Long.class, "ncId", true, "_id");
        public final static Property NtID = new Property(1, Long.class, "ntID", false, "NT_ID");
        public final static Property IdNum = new Property(2, Long.class, "idNum", false, "ID_NUM");
        public final static Property Title = new Property(3, String.class, "title", false, "TITLE");
        public final static Property Url = new Property(4, String.class, "url", false, "URL");
    }


    public NovelClassifyDao(DaoConfig config) {
        super(config);
    }
    
    public NovelClassifyDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOVEL_CLASSIFY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: ncId
                "\"NT_ID\" INTEGER," + // 1: ntID
                "\"ID_NUM\" INTEGER," + // 2: idNum
                "\"TITLE\" TEXT," + // 3: title
                "\"URL\" TEXT);"); // 4: url
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOVEL_CLASSIFY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, NovelClassify entity) {
        stmt.clearBindings();
 
        Long ncId = entity.getNcId();
        if (ncId != null) {
            stmt.bindLong(1, ncId);
        }
 
        Long ntID = entity.getNtID();
        if (ntID != null) {
            stmt.bindLong(2, ntID);
        }
 
        Long idNum = entity.getIdNum();
        if (idNum != null) {
            stmt.bindLong(3, idNum);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(5, url);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, NovelClassify entity) {
        stmt.clearBindings();
 
        Long ncId = entity.getNcId();
        if (ncId != null) {
            stmt.bindLong(1, ncId);
        }
 
        Long ntID = entity.getNtID();
        if (ntID != null) {
            stmt.bindLong(2, ntID);
        }
 
        Long idNum = entity.getIdNum();
        if (idNum != null) {
            stmt.bindLong(3, idNum);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(5, url);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public NovelClassify readEntity(Cursor cursor, int offset) {
        NovelClassify entity = new NovelClassify( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ncId
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // ntID
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // idNum
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // title
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // url
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, NovelClassify entity, int offset) {
        entity.setNcId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNtID(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setIdNum(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setTitle(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(NovelClassify entity, long rowId) {
        entity.setNcId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(NovelClassify entity) {
        if(entity != null) {
            return entity.getNcId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(NovelClassify entity) {
        return entity.getNcId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
