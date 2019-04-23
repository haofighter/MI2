package com.hao.show.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hao.show.moudle.main.novel.Entity.NovelListItemContent;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NOVEL_LIST_ITEM_CONTENT".
*/
public class NovelListItemContentDao extends AbstractDao<NovelListItemContent, Long> {

    public static final String TABLENAME = "NOVEL_LIST_ITEM_CONTENT";

    /**
     * Properties of entity NovelListItemContent.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property NID = new Property(0, Long.class, "nID", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Url = new Property(2, String.class, "url", false, "URL");
        public final static Property Auther = new Property(3, String.class, "auther", false, "AUTHER");
        public final static Property NewChapter = new Property(4, String.class, "newChapter", false, "NEW_CHAPTER");
        public final static Property NewChapter_utl = new Property(5, String.class, "newChapter_utl", false, "NEW_CHAPTER_UTL");
    }


    public NovelListItemContentDao(DaoConfig config) {
        super(config);
    }
    
    public NovelListItemContentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOVEL_LIST_ITEM_CONTENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: nID
                "\"TITLE\" TEXT," + // 1: title
                "\"URL\" TEXT," + // 2: url
                "\"AUTHER\" TEXT," + // 3: auther
                "\"NEW_CHAPTER\" TEXT," + // 4: newChapter
                "\"NEW_CHAPTER_UTL\" TEXT);"); // 5: newChapter_utl
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOVEL_LIST_ITEM_CONTENT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, NovelListItemContent entity) {
        stmt.clearBindings();
 
        Long nID = entity.getNID();
        if (nID != null) {
            stmt.bindLong(1, nID);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(3, url);
        }
 
        String auther = entity.getAuther();
        if (auther != null) {
            stmt.bindString(4, auther);
        }
 
        String newChapter = entity.getNewChapter();
        if (newChapter != null) {
            stmt.bindString(5, newChapter);
        }
 
        String newChapter_utl = entity.getNewChapter_utl();
        if (newChapter_utl != null) {
            stmt.bindString(6, newChapter_utl);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, NovelListItemContent entity) {
        stmt.clearBindings();
 
        Long nID = entity.getNID();
        if (nID != null) {
            stmt.bindLong(1, nID);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(3, url);
        }
 
        String auther = entity.getAuther();
        if (auther != null) {
            stmt.bindString(4, auther);
        }
 
        String newChapter = entity.getNewChapter();
        if (newChapter != null) {
            stmt.bindString(5, newChapter);
        }
 
        String newChapter_utl = entity.getNewChapter_utl();
        if (newChapter_utl != null) {
            stmt.bindString(6, newChapter_utl);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public NovelListItemContent readEntity(Cursor cursor, int offset) {
        NovelListItemContent entity = new NovelListItemContent( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // nID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // url
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // auther
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // newChapter
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // newChapter_utl
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, NovelListItemContent entity, int offset) {
        entity.setNID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAuther(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setNewChapter(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNewChapter_utl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(NovelListItemContent entity, long rowId) {
        entity.setNID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(NovelListItemContent entity) {
        if(entity != null) {
            return entity.getNID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(NovelListItemContent entity) {
        return entity.getNID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}