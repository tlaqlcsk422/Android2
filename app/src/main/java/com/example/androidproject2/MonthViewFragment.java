package com.example.androidproject2;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MonthViewFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MonthViewFragment() { }

    public static MonthViewFragment newInstance(String param1, String param2) {
        MonthViewFragment fragment = new MonthViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_month_view, container, false);

        DisplayMetrics display = this.getResources().getDisplayMetrics();
        int width = display.widthPixels;
        int height = display.heightPixels;

        if(width<height) {
            ViewPager2 vpPager = rootView.findViewById(R.id.vpPager);
            FragmentStateAdapter adapter = new MonthCalendarAdapter(this);
            vpPager.setAdapter(adapter);
            vpPager.setCurrentItem(49);//현재 페이지를 오늘 날짜 position으로 설정

    }
        else{
            ViewPager2 vpPager = rootView.findViewById(R.id.vpPager);
            FragmentStateAdapter adapter = new MonthCalendarAdapter(this);
            vpPager.setAdapter(adapter);
            vpPager.setCurrentItem(49);//현재 페이지를 오늘 날짜 position으로 설정
        }

        // Inflate the layout for this fragment
        return rootView;
    }

}