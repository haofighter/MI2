package com.hao.show.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hao.show.moudle.main.novel.Entity.NovelChapter;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NOVEL_CHAPTER".
*/
public class NovelChapterDao extends AbstractDao<NovelChapter, Long> {

    public static final String TABLENAME = "NOVEL_CHAPTER";

    /**
     * Properties of entity NovelChapter.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Cid = new Property(0, Long.class, "Cid", true, "_id");
        public final static Property Nid = new Property(1, Long.class, "Nid", false, "NID");
        public final static Property ChapterName = new Property(2, String.class, "chapterName", false, "CHAPTER_NAME");
        public final static Property ChapterUrl = new Property(3, String.class, "chapterUrl", false, "CHAPTER_URL");
        public final static Property ChapterContent = new Property(4, String.class, "chapterContent", false, "CHAPTER_CONTENT");
        public final static Property NextChapterUrl = new Property(5, String.class, "nextChapterUrl", false, "NEXT_CHAPTER_URL");
        public final static Property BeforChapterUrl = new Property(6, String.class, "beforChapterUrl", false, "BEFOR_CHAPTER_URL");
    }


    public NovelChapterDao(DaoConfig config) {
        super(config);
    }
    
    public NovelChapterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOVEL_CHAPTER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: Cid
                "\"NID\" INTEGER," + // 1: Nid
                "\"CHAPTER_NAME\" TEXT," + // 2: chapterName
                "\"CHAPTER_URL\" TEXT," + // 3: chapterUrl
                "\"CHAPTER_CONTENT\" TEXT," + // 4: chapterContent
                "\"NEXT_CHAPTER_URL\" TEXT," + // 5: nextChapterUrl
                "\"BEFOR_CHAPTER_URL\" TEXT);"); // 6: beforChapterUrl
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOVEL_CHAPTER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, NovelChapter entity) {
        stmt.clearBindings();
 
        Long Cid = entity.getCid();
        if (Cid != null) {
            stmt.bindLong(1, Cid);
        }
 
        Long Nid = entity.getNid();
        if (Nid != null) {
            stmt.bindLong(2, Nid);
        }
 
        String chapterName = entity.getChapterName();
        if (chapterName != null) {
            stmt.bindString(3, chapterName);
        }
 
        String chapterUrl = entity.getChapterUrl();
        if (chapterUrl != null) {
            stmt.bindString(4, chapterUrl);
        }
 
        String chapterContent = entity.getChapterContent();
        if (chapterContent != null) {
            stmt.bindString(5, chapterContent);
        }
 
        String nextChapterUrl = entity.getNextChapterUrl();
        if (nextChapterUrl != null) {
            stmt.bindString(6, nextChapterUrl);
        }
 
        String beforChapterUrl = entity.getBeforChapterUrl();
        if (beforChapterUrl != null) {
            stmt.bindString(7, beforChapterUrl);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, NovelChapter entity) {
        stmt.clearBindings();
 
        Long Cid = entity.getCid();
        if (Cid != null) {
            stmt.bindLong(1, Cid);
        }
 
        Long Nid = entity.getNid();
        if (Nid != null) {
            stmt.bindLong(2, Nid);
        }
 
        String chapterName = entity.getChapterName();
        if (chapterName != null) {
            stmt.bindString(3, chapterName);
        }
 
        String chapterUrl = entity.getChapterUrl();
        if (chapterUrl != null) {
            stmt.bindString(4, chapterUrl);
        }
 
        String chapterContent = entity.getChapterContent();
        if (chapterContent != null) {
            stmt.bindString(5, chapterContent);
        }
 
        String nextChapterUrl = entity.getNextChapterUrl();
        if (nextChapterUrl != null) {
            stmt.bindString(6, nextChapterUrl);
        }
 
        String beforChapterUrl = entity.getBeforChapterUrl();
        if (beforChapterUrl != null) {
            stmt.bindString(7, beforChapterUrl);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public NovelChapter readEntity(Cursor cursor, int offset) {
        NovelChapter entity = new NovelChapter( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // Cid
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // Nid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // chapterName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // chapterUrl
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // chapterContent
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // nextChapterUrl
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // beforChapterUrl
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, NovelChapter entity, int offset) {
        entity.setCid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNid(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setChapterName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setChapterUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setChapterContent(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNextChapterUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBeforChapterUrl(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(NovelChapter entity, long rowId) {
        entity.setCid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(NovelChapter entity) {
        if(entity != null) {
            return entity.getCid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(NovelChapter entity) {
        return entity.getCid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
