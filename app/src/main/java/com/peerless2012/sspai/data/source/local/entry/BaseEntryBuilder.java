package com.peerless2012.sspai.data.source.local.entry;

import android.content.ContentValues;
import android.database.Cursor;

import com.peerless2012.sspai.domain.ArticleDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/4 10:18
 * @Version V1.0
 * @Description :
 */
public abstract class BaseEntryBuilder<T> {

    protected static final String INTEGER_TYPE = " INTEGER";

    protected static final String CHAR_TYPE = " CHAR";

    protected static final String BOOLEAN_TYPE = " INTEGER";

    protected static final String PRIMARY_KEY = " PRIMARY KEY";

    protected static final String AUTOINCREMENT = " AUTOINCREMENT";

    protected static final String COMMA_SEP = ", ";

    protected static final String SQL_HEADER = "CREATE TABLE IF NOT EXISTS ";

    protected static final String SQL_PRE = " (";

    protected static final String SQL_AFTER = ");";

    /**
     * Creates object out of cursor
     *
     * @param cursor
     * @return
     */
    public T build(Cursor cursor){
        if (cursor == null || cursor.getCount() == 0) return null;
        generateIndex(cursor);
        T t = generateEntry(cursor);
        resetIndexs();
        return t;
    }


    /**
     * Creates object out of cursor
     *
     * @param cursor
     * @return
     */
    public List<T> buildAll(Cursor cursor){
        if (cursor == null || cursor.getCount() == 0) return null;
        generateIndex(cursor);
        List<T> articleDetails = new ArrayList<T>();
        cursor.moveToFirst();
        do {
            articleDetails.add(generateEntry(cursor));
        }while (cursor.moveToNext());
        resetIndexs();
        return articleDetails;
    }

    /**
     * Puts an object into a ContentValues instance
     *
     * @param t
     * @return
     */
    public abstract ContentValues deconstruct(T t);

    /**
     * Generate cousor indexs
     *
     * @param cursor
     */
    protected abstract void generateIndex(Cursor cursor);

    /**
     * Generate entry
     *
     * @param cursor
     * @return
     */
    protected abstract T generateEntry(Cursor cursor);

    /**
     * Reset indexs
     */
    protected abstract void resetIndexs();
}
