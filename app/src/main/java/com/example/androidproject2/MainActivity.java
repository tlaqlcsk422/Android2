package com.example.androidproject2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AutoPermissionsListener{
    public static int year1=0,month1=-1,day1=0;
    public static ArrayList<Activity> actList = new ArrayList<Activity>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        SharedPreferences pref = mContext.getSharedPreferences("GetDate",Activity.MODE_PRIVATE);
        year1 = pref.getInt("Year",0);
        year1 = pref.getInt("Month",0);
        year1 = pref.getInt("Day",0);

         */

        /*
        Intent intent = getIntent();
        year1 = intent.getIntExtra("Year",-1);
        month1 = intent.getIntExtra("Month", -1);
        day1 = intent.getIntExtra("Day",-1);
         */

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.main_container, new MonthViewFragment());
        fragmentTransaction.commit();

        FloatingActionButton fButton=findViewById(R.id.floatingActionButton);


        fButton.setOnClickListener(this::onClick);

        AutoPermissions.Companion.loadAllPermissions(this,121);

        /*
        DayAdapter.GetYearMonthDay date = new DayAdapter.GetYearMonthDay() {
            @Override
            public void getYearMonthDay(int year, int month, int day) {
                year1 = year;
                month1 = month;
                day1 = day;
            }
        }

         */
    }

    public void onClick(View v){
        actList.add(this);
        Intent intent =new Intent(this,WriteActivity.class);
        intent.putExtra("year", year1);
        intent.putExtra("month", month1);
        intent.putExtra("day", day1);
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

/*
    @Override
    public void onYearMonthSet(int year, int month, int day) {
        year1 = year;
        month1 = month;
        day1 = day;
    }

 */
}