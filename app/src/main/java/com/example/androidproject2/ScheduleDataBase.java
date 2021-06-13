package com.example.androidproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ScheduleDataBase {
    private static final String TAG="Database";

    private static ScheduleDataBase database;
    public static int DATABASE_VERSION=1;

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public static final String DB_NAME = "schedule.db";
    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String COMMA_SEP = ",";

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

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME+";";


    public ScheduleDataBase(Context context){
        this.context=context;
    }

    public static ScheduleDataBase getInstance(Context context){
        if(database==null){
            database=new ScheduleDataBase(context);
        }
        return database;
    }

    public boolean open(){
        println("opening database ["+DB_NAME+"]");
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();


        return true;
    }

    public void close(){
        println("closing database ["+DB_NAME+"]");
        db.close();

        database=null;
    }

    public Cursor rawQuery(String SQL){
        println("\nexecuteQuery called\n");

        Cursor cursor=null;

        try{
            open();
            cursor=db.rawQuery(SQL,null);
            println("cursor count :"+cursor.getCount());
            close();

        }catch (Exception e){
            Log.e(TAG, "Exception in executeQuery", e);
        }

        return cursor;
    }

    public boolean exeSQL(String SQL){
        println("\nexecuteQuery called\n");

        try{
            open();
            db.execSQL(SQL);
            close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public class DBHelper extends SQLiteOpenHelper {
        private static final String TAG = "SQLiteDBTest";
        private SQLiteDatabase db;
        private int oldVersion;
        private int newVersion;

        public DBHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String DROP_SQL = "drop table if exists " + TABLE_NAME;

            try {
                db.execSQL(DROP_SQL);
            } catch (Exception e) {
                Log.e(TAG, "Exception in DROP_SQL", e);
            }

            String CREATE_SQL = "create table " + TABLE_NAME + " (" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    KEY_YEAR + INTEGER_TYPE + COMMA_SEP +
                    KEY_MONTH + INTEGER_TYPE + COMMA_SEP +
                    KEY_DAY + INTEGER_TYPE + COMMA_SEP +
                    KEY_TITLE + TEXT_TYPE + COMMA_SEP +
                    KEY_SHOUR + INTEGER_TYPE + COMMA_SEP +
                    KEY_SMIN + INTEGER_TYPE + COMMA_SEP +
                    KEY_EHOUR + INTEGER_TYPE + COMMA_SEP +
                    KEY_EMIN + INTEGER_TYPE + COMMA_SEP +
                    KEY_PLACE + TEXT_TYPE + COMMA_SEP +
                    KEY_MEMO + TEXT_TYPE +
                    " )";


            Log.d(TAG, getClass().getName() + ".onCreate() ");
            try {
                db.execSQL(CREATE_SQL);
            } catch (Exception e) {
                Log.e(TAG, "Error in onCreate");
            }

            String CREATE_INDEX_SQL = "create index " + TABLE_NAME + "_IDX on "
                    + TABLE_NAME + "(" + "CREATE_DATE" + ")";

            try {
                db.execSQL(CREATE_INDEX_SQL);
            } catch (Exception e) {
                Log.e(TAG, "Exception in CREATE_INDEX_SQL", e);
            }

        }

        public Cursor getTodaySchedules(int year, int month, int day) {
            String sql = "Select " + KEY_TITLE + " From " +
                    TABLE_NAME +
                    " WHERE " + KEY_YEAR + "=" + year + ","
                    + KEY_MONTH + "=" + month + ","
                    + KEY_DAY + "=" + day;
            return getReadableDatabase().rawQuery(sql, null);
        }

        public long insert(int year, int month, int day, String title,
                           int sHour, int sMin, int eHour, int eMin,
                           String place, String memo) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_YEAR, year);
            values.put(KEY_MONTH, month);
            values.put(KEY_DAY, day);
            values.put(KEY_TITLE, title);
            values.put(KEY_SHOUR, sHour);
            values.put(KEY_SMIN, sMin);
            values.put(KEY_EHOUR, eHour);
            values.put(KEY_EMIN, eMin);
            values.put(KEY_PLACE, place);
            values.put(KEY_MEMO, memo);

            return db.insert(TABLE_NAME, null, values);

        }

        public void onOpen(SQLiteDatabase db){
            println("opened database ["+DB_NAME+"]");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version "+oldVersion+" to "+newVersion);

            String sql = "DROP TABLE if exists "+TABLE_NAME;

            db.execSQL(sql);
            onCreate(db);
        }

    }




    private void println(String msg){
        Log.d(TAG,msg);
    }
}