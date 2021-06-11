package com.example.androidproject2;

import android.provider.BaseColumns;

public final class Schedule {
    public static final String DB_NAME = "schedule.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //private Schadule() {}

    /* Inner class that defines the table contents */
    public static class Schadules implements BaseColumns {
        public static final String TABLE_NAME= "Schadules";
        public static final int KEY_YEAR = 0;
        public static final int KEY_MONTH = 0;
        public static final int KEY_DAY = 0;
        public static final String KEY_TITLE = "Title";//일정제목
        public static final int KEY_SHOUR = 0;//시작시간
        public static final int KEY_SMIN = 0;//종료 시간
        public static final int KEY_EHOUR = 0;//시작시간
        public static final int KEY_EMIN = 0;//종료 시간
        public static final String KEY_PLACE = "Place";
        public static final String KEY_MEMO = "Memo";


        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_YEAR + TEXT_TYPE + COMMA_SEP +
                KEY_MONTH + TEXT_TYPE +  COMMA_SEP +
                KEY_DAY + TEXT_TYPE +  COMMA_SEP +
                KEY_TITLE + TEXT_TYPE +  COMMA_SEP +
                KEY_SHOUR + TEXT_TYPE +  COMMA_SEP +
                KEY_SMIN + TEXT_TYPE +  COMMA_SEP +
                KEY_EHOUR + TEXT_TYPE +  COMMA_SEP +
                KEY_EMIN + TEXT_TYPE +  COMMA_SEP +
                KEY_PLACE + TEXT_TYPE +  COMMA_SEP +
                KEY_MEMO + TEXT_TYPE +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
