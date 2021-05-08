package com.example.androidproject2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.main_container, new MonthViewFragment());
        fragmentTransaction.commit();
        /*
        if(getWindowManager().getDefaultDisplay().getRotation() != Surface.ROTATION_0){        // 화면의 각도가 0이면

            fragmentTransaction.replace(R.id.main_container, new MonthViewFragment());        // 화면을 불러라!

        }else {

            fragmentTransaction.replace(R.id.main_container, new WeekViewFragment());        // 화면을 불러라!

        }
        fragmentTransaction.commit();

         */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DisplayMetrics display = this.getResources().getDisplayMetrics();
        int width = display.widthPixels;
        int height = display.heightPixels;

        switch (item.getItemId()) {
            case R.id.month_view:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //if(width < height) {
                    fragmentTransaction.replace(R.id.main_container, new MonthViewFragment());
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "month_view", Toast.LENGTH_SHORT).show();
                    return true;
                //}
                /*
                else{
                    fragmentTransaction.replace(R.id.main_container, new WeekViewFragment());
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "month_view2", Toast.LENGTH_SHORT).show();
                    return true;
                }

                 */


            case R.id.week_view:
                //if(width < height) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, new WeekViewFragment());
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "week_view", Toast.LENGTH_SHORT).show();
                    return true;
                //}
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}