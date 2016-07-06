package com.peerless2012.sspai.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.peerless2012.sspai.data.source.local.entry.ArticleDetailEntry;
import com.peerless2012.sspai.data.source.local.entry.ArticleEntry;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/4 9:39
 * @Version V1.0
 * @Description : 数据库
 */
public class SSPaiDBHelper extends SQLiteOpenHelper{
    private static volatile SSPaiDBHelper sInst = null;  // <<< 这里添加了 volatile

    private SSPaiDBHelper(Context context) {
        super(context.getApplicationContext(), "SSPai.db", null, 1);
    }

    public static SSPaiDBHelper getInstance(Context context) {
        SSPaiDBHelper inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (SSPaiDBHelper.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new SSPaiDBHelper(context);
                    sInst = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ArticleEntry.CREATE_SQL);
        db.execSQL(ArticleDetailEntry.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}


