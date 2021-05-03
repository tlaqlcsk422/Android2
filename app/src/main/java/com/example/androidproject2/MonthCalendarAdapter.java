package com.example.androidproject2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS = 100;

    public MonthCalendarAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ArrayList<MonthCalendarFragment> monthCF = new ArrayList<MonthCalendarFragment>();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        //50개월 전으로 만들기
        for(int i =0; i<50; i++){
            if(month <= 0){//1월이면 12월로 바꾸고 연도 -1
                month = 11;
                year--;
            }
            else {
                month--;
            }
        }

        //50개월 전부터~50개월 후까지 년, 월 저장
        for(int i =0; i<NUM_ITEMS; i++){
            monthCF.add(MonthCalendarFragment.newInstance(year,month));
            month++;
            if(month>=12){//12월이면 1월로 바꾸고 연도 +1
                month=month-12;
                year++;
            }
        }
        return monthCF.get(position);
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
