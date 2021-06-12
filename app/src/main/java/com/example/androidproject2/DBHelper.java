package com.example.androidproject2;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import static java.sql.Types.NULL;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteDBTest";

    public DBHelper(@Nullable Context context) {
        super(context, Schedule.DB_NAME, null, Schedule.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,getClass().getName() + ".onCreate() ");
        db.execSQL(Schedule.Schedules.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Schedule.Schedules.DELETE_TABLE);
        onCreate(db);
    }

    public void insertSchedule(int year, int month, int day, String title,
                               int sHour, int sMin, int eHour, int eMin,
                               String place, String memo){
        try{
            String sql = String.format(
                    "INSERT INTO %s(%d %d, %d, %s, %d, %d, %d %d %s %s) VALUES('%s', '%d','%d','%d','%s','%d','%d','%d','%d','%s','%s')",
                    Schedule.Schedules.TABLE_NAME,
                    Schedule.Schedules.KEY_YEAR,
                    Schedule.Schedules.KEY_MONTH,
                    Schedule.Schedules.KEY_DAY,
                    Schedule.Schedules.KEY_TITLE,
                    Schedule.Schedules.KEY_SHOUR,
                    Schedule.Schedules.KEY_SMIN,
                    Schedule.Schedules.KEY_EHOUR,
                    Schedule.Schedules.KEY_EMIN,
                    Schedule.Schedules.KEY_PLACE,
                    Schedule.Schedules.KEY_MEMO,
                    Schedule.DB_NAME,year, month, day, title, sHour, sMin, eHour, eMin, place, memo);

            getWritableDatabase().execSQL(sql);
        }catch (SQLException e){
            Log.e(TAG, "Error in inserting recodes");
        }
    }

    public void updateSchedule(int year, int month, int day, String title,
                               int sHour, int sMin, int eHour, int eMin,
                               String place, String memo){
        try {
            String sql = String.format(
                    "UPDATE %s SET %s = '%s', %d = '%d', %d = '%d', %d = '%d', %d = '%d', %s = '%s', %s ='%s' WHERE %d = '%d' AND %d = '%d' AND %d = '%d'",
                    Schedule.Schedules.TABLE_NAME,
                    Schedule.Schedules.KEY_TITLE, title,
                    Schedule.Schedules.KEY_SHOUR, sHour,
                    Schedule.Schedules.KEY_SMIN, sMin,
                    Schedule.Schedules.KEY_EHOUR, eHour,
                    Schedule.Schedules.KEY_EMIN, eMin,
                    Schedule.Schedules.KEY_PLACE, place,
                    Schedule.Schedules.KEY_MEMO, memo,
                    Schedule.Schedules.KEY_YEAR, year,
                    Schedule.Schedules.KEY_MONTH, month,
                    Schedule.Schedules.KEY_DAY, day);
            getWritableDatabase().execSQL(sql);
        }catch (SQLException e){
            Log.e(TAG, "Error in updating recodes");
        }
    }

    public void deleteSchedule(int year, int month, int day){
        try{
            String sql = String.format(
                    "DELETE FROM %s WHERE %s = %s AND %s = %s AND %s = %s",
                    Schedule.Schedules.TABLE_NAME,
                    Schedule.Schedules.KEY_YEAR, year,
                    Schedule.Schedules.KEY_MONTH, month,
                    Schedule.Schedules.KEY_DAY, day);
            getWritableDatabase().execSQL(sql);
        }catch (SQLException e){
            Log.e(TAG, "Error in deleting recodes");
        }
    }

    public Cursor getAllSchedules(){
        String sql = "Select * From " + Schedule.Schedules.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }

/*
    public String isSchadule(int year, int month, int day){
            String sql = String.format(
                    "(SELECT EXISTS(SELECT * FROM %s WHERE %d = %d AND % = %d AND %d = %d) AS SUCCESS",
                    Schedule.Schadules.TABLE_NAME,
                    Schedule.Schadules.KEY_YEAR, year,
                    Schedule.Schadules.KEY_MONTH, month,
                    Schedule.Schadules.KEY_DAY, day
            );

        Boolean s = getReadableDatabase().rawQuery(sql, null);
        return getReadableDatabase().rawQuery(sql, null);
    }
 */


}

