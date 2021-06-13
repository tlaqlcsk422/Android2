package com.example.androidproject2;

import android.provider.BaseColumns;

public final class Schedule {
    public static final String DB_NAME = "schedule.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private Schedule() {}

    /* Inner class that defines the table contents */
    public static class Schedules implements BaseColumns {
        public static final String TABLE_NAME= "Schedules";
        public static final String KEY_YEAR = "year";
        public static final String KEY_MONTH = "month";
        public static final String KEY_DAY = "day";
        public static final String KEY_TITLE = "title";//일정제목
        public static final String KEY_SHOUR = "sHour";//시작시간
        public static final String KEY_SMIN = "sMin";//종료 시간
        public static final String KEY_EHOUR = "eHour";//시작시간
        public static final String KEY_EMIN = "eMin";//종료 시간
        public static final String KEY_PLACE = "place";
        public static final String KEY_MEMO = "memo";


        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
                KEY_YEAR + INTEGER_TYPE + COMMA_SEP +
                KEY_MONTH + INTEGER_TYPE +  COMMA_SEP +
                KEY_DAY + INTEGER_TYPE +  COMMA_SEP +
                KEY_TITLE + TEXT_TYPE +  COMMA_SEP +
                KEY_SHOUR + INTEGER_TYPE +  COMMA_SEP +
                KEY_SMIN + INTEGER_TYPE +  COMMA_SEP +
                KEY_EHOUR + INTEGER_TYPE +  COMMA_SEP +
                KEY_EMIN + INTEGER_TYPE +  COMMA_SEP +
                KEY_PLACE + TEXT_TYPE +  COMMA_SEP +
                KEY_MEMO + TEXT_TYPE +
                " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME+";";
    }
}