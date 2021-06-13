package com.example.androidproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import static com.example.androidproject2.Schedule.Schedules.TABLE_NAME;
import static java.sql.Types.NULL;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteDBTest";

    public DBHelper(@Nullable Context context) {
        super(context, Schedule.DB_NAME, null, Schedule.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,getClass().getName() + ".onCreate() ");
        try {
            db.execSQL(Schedule.Schedules.CREATE_TABLE);
        }catch (Exception e){
            Log.e(TAG, "Error in onCreate");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Schedule.Schedules.DELETE_TABLE);
        onCreate(db);
    }

    public void insertSchedule(String sql)
            /*(int year, int month, int day, String title,
                               int sHour, int sMin, int eHour, int eMin,
                               String place, String memo)

             */
                               {

        try{
            /*
            String sql = String.format(
                    "INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s %s) VALUES(%d,%d,%d,'%s',%d,%d,%d,%d,'%s','%s')",
                    TABLE_NAME,
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
                    year, month, day, title, sHour, sMin, eHour, eMin, place, memo);


             */
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
                    "UPDATE %s SET %s = '%s', %s = %d, %s = %d, %s = %d, %s = %d, %s = '%s', %s ='%s' WHERE %s = %d AND %s = %d AND %s = %d",
                    TABLE_NAME,
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

    public void deleteSchedule(String title, int year, int month, int day){
        try{
            String sql = String.format(
                    "DELETE FROM %s WHERE %s = '%s' AND %s = %d AND %s = %d AND %s = %d",
                    TABLE_NAME,
                    Schedule.Schedules.KEY_TITLE, title,
                    Schedule.Schedules.KEY_YEAR, year,
                    Schedule.Schedules.KEY_MONTH, month,
                    Schedule.Schedules.KEY_DAY, day);
            getWritableDatabase().execSQL(sql);
        }catch (SQLException e){
            Log.e(TAG, "Error in deleting recodes");
        }
    }

    public Cursor getTodaySchedules(int year, int month, int day){
        String sql = "Select "+Schedule.Schedules.KEY_TITLE+" From "+
                TABLE_NAME+
                " WHERE "+ Schedule.Schedules.KEY_YEAR + "=" + year + ","
                + Schedule.Schedules.KEY_MONTH + "=" + month + ","
                + Schedule.Schedules.KEY_DAY + "=" + day;
        return getReadableDatabase().rawQuery(sql,null);
    }

    public long insert(int year, int month, int day, String title,
    int sHour, int sMin, int eHour, int eMin,
    String place, String memo){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Schedule.Schedules.KEY_YEAR,year);
        values.put(Schedule.Schedules.KEY_MONTH, month);
        values.put(Schedule.Schedules.KEY_DAY, day);
        values.put(Schedule.Schedules.KEY_TITLE, title);
        values.put(Schedule.Schedules.KEY_SHOUR, sHour);
        values.put(Schedule.Schedules.KEY_SMIN, sMin);
        values.put(Schedule.Schedules.KEY_EHOUR, eHour);
        values.put(Schedule.Schedules.KEY_EMIN, eMin);
        values.put(Schedule.Schedules.KEY_PLACE, place);
        values.put(Schedule.Schedules.KEY_MEMO,memo);

        return db.insert(TABLE_NAME,null,values);

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
