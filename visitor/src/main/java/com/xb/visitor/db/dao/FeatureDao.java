package com.xb.visitor.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.xb.visitor.entity.Feature;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FEATURE".
*/
public class FeatureDao extends AbstractDao<Feature, Long> {

    public static final String TABLENAME = "FEATURE";

    /**
     * Properties of entity Feature.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property FeatureId = new Property(0, Long.class, "FeatureId", true, "_id");
        public final static Property Openid = new Property(1, String.class, "openid", false, "OPENID");
        public final static Property Fvect = new Property(2, String.class, "fvect", false, "FVECT");
        public final static Property Face_x1 = new Property(3, int.class, "face_x1", false, "FACE_X1");
        public final static Property Face_y1 = new Property(4, int.class, "face_y1", false, "FACE_Y1");
        public final static Property Face_x2 = new Property(5, int.class, "face_x2", false, "FACE_X2");
        public final static Property Face_y2 = new Property(6, int.class, "face_y2", false, "FACE_Y2");
    }


    public FeatureDao(DaoConfig config) {
        super(config);
    }
    
    public FeatureDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FEATURE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: FeatureId
                "\"OPENID\" TEXT," + // 1: openid
                "\"FVECT\" TEXT," + // 2: fvect
                "\"FACE_X1\" INTEGER NOT NULL ," + // 3: face_x1
                "\"FACE_Y1\" INTEGER NOT NULL ," + // 4: face_y1
                "\"FACE_X2\" INTEGER NOT NULL ," + // 5: face_x2
                "\"FACE_Y2\" INTEGER NOT NULL );"); // 6: face_y2
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FEATURE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Feature entity) {
        stmt.clearBindings();
 
        Long FeatureId = entity.getFeatureId();
        if (FeatureId != null) {
            stmt.bindLong(1, FeatureId);
        }
 
        String openid = entity.getOpenid();
        if (openid != null) {
            stmt.bindString(2, openid);
        }
 
        String fvect = entity.getFvect();
        if (fvect != null) {
            stmt.bindString(3, fvect);
        }
        stmt.bindLong(4, entity.getFace_x1());
        stmt.bindLong(5, entity.getFace_y1());
        stmt.bindLong(6, entity.getFace_x2());
        stmt.bindLong(7, entity.getFace_y2());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Feature entity) {
        stmt.clearBindings();
 
        Long FeatureId = entity.getFeatureId();
        if (FeatureId != null) {
            stmt.bindLong(1, FeatureId);
        }
 
        String openid = entity.getOpenid();
        if (openid != null) {
            stmt.bindString(2, openid);
        }
 
        String fvect = entity.getFvect();
        if (fvect != null) {
            stmt.bindString(3, fvect);
        }
        stmt.bindLong(4, entity.getFace_x1());
        stmt.bindLong(5, entity.getFace_y1());
        stmt.bindLong(6, entity.getFace_x2());
        stmt.bindLong(7, entity.getFace_y2());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Feature readEntity(Cursor cursor, int offset) {
        Feature entity = new Feature( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // FeatureId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // openid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // fvect
            cursor.getInt(offset + 3), // face_x1
            cursor.getInt(offset + 4), // face_y1
            cursor.getInt(offset + 5), // face_x2
            cursor.getInt(offset + 6) // face_y2
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Feature entity, int offset) {
        entity.setFeatureId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setOpenid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFvect(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFace_x1(cursor.getInt(offset + 3));
        entity.setFace_y1(cursor.getInt(offset + 4));
        entity.setFace_x2(cursor.getInt(offset + 5));
        entity.setFace_y2(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Feature entity, long rowId) {
        entity.setFeatureId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Feature entity) {
        if(entity != null) {
            return entity.getFeatureId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Feature entity) {
        return entity.getFeatureId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}