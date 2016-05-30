package com.didiincubator.utils;

import android.provider.BaseColumns;

/**
 * Created by 枫叶1 on 2016/5/30.
 * 历史记录表字段信息
 */
public final class HistoryTable {
    public static class Field implements BaseColumns{
        public static final String TABLE_NAME="history";
        public static final String HISTORY_DIDI_ID="didi_id";
        public static final String HISTORY_TIME="time";

    }
}
