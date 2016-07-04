package com.peerless2012.sspai.data.source.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/4 9:34
 * @Version V1.0
 * @Description :
 */
public class BaseLocalDataSource {

    protected static final Object LOCK = new Object();

    protected SQLiteOpenHelper mDbHelper = null;

    public BaseLocalDataSource(SQLiteOpenHelper openHelper) {
        this.mDbHelper = openHelper;
    }

    public SQLiteDatabase getReadableDatabase() {
        return mDbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return mDbHelper.getWritableDatabase();
    }

    protected void close(Cursor cursor, SQLiteDatabase db) {
        closeCursor(cursor);
        closeDatabase(db);
    }

    protected void closeDatabase(SQLiteDatabase db) {
        try {
            if (db != null) {
                db.close();
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    protected void closeCursor(Cursor cursor) {
        try {
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }
}
