package com.peerless2012.sspai.data.source.local.entry;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/4 10:18
 * @Version V1.0
 * @Description :
 */
public abstract class BaseEntryBuilder<T> {
    /**
     * Creates object out of cursor
     *
     * @param cursor
     * @return
     */
    public abstract T build(Cursor cursor);

    /**
     * Creates object out of cursor
     *
     * @param cursor
     * @return
     */
    public abstract List<T> buildAll(Cursor cursor);

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
}
