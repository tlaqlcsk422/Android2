package com.example.androidproject2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.util.ArrayList;
import java.util.Date;

import static com.example.androidproject2.ScheduleDataBase.TABLE_NAME;


public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    public static int year=0,month=-1,date=0,time=0;
    public static ArrayList<Activity> actList = new ArrayList<Activity>();
    private static final String TAG="Main Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.main_container, new MonthViewFragment());
        fragmentTransaction.commit();

        FloatingActionButton fButton=findViewById(R.id.floatingActionButton);


        fButton.setOnClickListener(this::onClick);

        AutoPermissions.Companion.loadAllPermissions(this,121);

        loadData();
    }

    public void onClick(View v){
        actList.add(this);
        Intent intent =new Intent(this,WriteActivity.class);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("date", date);
        intent.putExtra("time",time);
        intent.putExtra("_id", -1);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.month_view:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, new MonthViewFragment());
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "month_view", Toast.LENGTH_SHORT).show();
                    return true;

            case R.id.week_view:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, new WeekViewFragment());
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "week_view", Toast.LENGTH_SHORT).show();
                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //프래그먼트에서 이용할 타이틀 변경 함수
    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public void onDenied(int i, String[] permissions) {

    }

    @Override
    public void onGranted(int i, String[] permissions) {

    }

    public void loadData() {

        String sql = "select * from " + TABLE_NAME;

        int recordCount = -1;
        ScheduleDataBase database = ScheduleDataBase.getInstance(this);

        if (database != null) {
            Cursor outCursor = database.rawQuery(sql);

            recordCount = outCursor.getCount();
            Log.d(TAG,"record count : " + recordCount + "\n");

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);

                String getYear = outCursor.getString(1);
                String getMonth = outCursor.getString(2);
                String getDay = outCursor.getString(3);
                String getTitle = outCursor.getString(4);
                String getSHour = outCursor.getString(5);
                String getSMin = outCursor.getString(6);
                String getEHour = outCursor.getString(7);
                String getEMin = outCursor.getString(8);
                String getPlace = outCursor.getString(9);
                String getMemo = outCursor.getString(10);


                Log.d(TAG,"#" + i + " -> " + _id + ", " +
                        getYear + "년, " + getMonth + "월, " +
                       getDay + "일, " +getTitle + ", " +getSHour+getSMin + ", " +getEHour+getEMin + ", " +getPlace + ", " + getMemo);
            }
            outCursor.close();
        }

    }



}