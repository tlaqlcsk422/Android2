package com.example.androidproject2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MonthCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS = 100;

    public MonthCalendarAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //수정해야함.
        int year = position;
        int month = position+1;
        return MonthCalendarFragment.newInstance(year, month);
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
