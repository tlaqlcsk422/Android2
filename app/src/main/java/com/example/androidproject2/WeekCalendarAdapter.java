package com.example.androidproject2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS = 100;

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
        month = cal.get(Calendar.MONTH) + 1;
        int date;
        date = cal.get(Calendar.DATE);


        //50주 전으로 만들기
        for(int i =0; i<50; i++){
            if(date <= 0){//1월이면 12월로 바꾸고 연도 -1
                month--;
                if(month<=0) {
                    month=11;
                    year--;
                }
                cal.set(year,month,date);
            }
            else {
                date-=7;

            }
        }

        //50주 전부터~50주 후까지 년, 월, 주 저장
        for(int i =0; i<NUM_ITEMS; i++){
            weekCF.add(WeekCalendarFragment.newInstance(year,month,date));
            date+=7;
            if(date>cal.getActualMaximum(Calendar.DAY_OF_MONTH)){//12월이면 1월로 바꾸고 연도 +1
                month++;
                date=date-cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                if(month>12){
                    month=month-12;
                    year++;
                }
            }
            cal.set(year,month,date);
        }
        return weekCF.get(position);
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
