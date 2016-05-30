package com.didiincubator.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 枫叶1 on 2016/5/30.
 */
public class HistoryHelper extends SQLiteOpenHelper{
    /**
     * 历史记录表的sqlite辅助类
     */
    public static final String NAME = "incubator.db";
    public static final int VERSION=1;

    public HistoryHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    /**
     *
     * @param db
     * 第一次创建数据库调用，若已存在，则不调用
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table "+HistoryTable.Field.TABLE_NAME+
                " ("+HistoryTable.Field._ID+" integer primary key autoincrement,"+
                HistoryTable.Field.HISTORY_DIDI_ID+" integer,"+
                HistoryTable.Field.HISTORY_TIME+" text)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
