package com.example.androidproject2;

import android.util.Log;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeekCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS = 100;
    private static final String TAG="Week Calendar Adapter";

    public WeekCalendarAdapter(@NonNull Fragment fragment) {super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ArrayList<WeekCalendarFragment> weekCF = new ArrayList<WeekCalendarFragment>();
        Calendar cal;
        cal = Calendar.getInstance();

        int year;
        year = cal.get(Calendar.YEAR);
        int month;
        month = cal.get(Calendar.MONTH);
        int date;
        date = cal.get(Calendar.DATE);

        date=date-cal.get(Calendar.DAY_OF_WEEK)+1;//일요일 날짜로 바꿈


        //50주 전으로 만들기
        for(int i =0; i<50; i++){
            date-=7;
            if(date <= 0){//한달이 지나면 날짜를 바꾸고 달은 -1
                month--;
                if(month<0) {//1월이면 12월로 바꾸고 연도 -1
                    month+=12;
                    year--;
                }
                cal.set(year,month,1);
                date=date+cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            cal.set(year,month,date);
            Log.d(TAG,year+"/"+(month+1)+"/"+date);
        }

        date=date-cal.get(Calendar.DAY_OF_WEEK)+1;//일요일 날짜로 바꿈

        //50주 전부터~50주 후까지 년, 월, 주 저장
        for(int i =0; i<NUM_ITEMS; i++){
            weekCF.add(WeekCalendarFragment.newInstance(year,month,date));
            Log.d(TAG,month+" 월월월");
            date+=7;
            if(date>cal.getActualMaximum(Calendar.DAY_OF_MONTH)){//한달 지나면 날짜를 바꾸고 한 달을 뒤로 넘김
                month++;
                date=date-cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                if(month>11){//12월이면 1월로 바꾸고 연도 +1
                    month=month-12;
                    year++;
                }
            }
            cal.set(year,month,date);
            Log.d(TAG,year+"/"+(month+1)+"/"+date);
        }

        return weekCF.get(position);
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
