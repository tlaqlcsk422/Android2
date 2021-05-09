package com.example.androidproject2;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MonthCalendarFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int year;
    private int month;

    int firstDay; //시작 요일
    int allDay; //한달 일 수

    private int mParam1;
    private int mParam2;

    public MonthCalendarFragment() {}

    public static MonthCalendarFragment newInstance(int year, int month) {
        MonthCalendarFragment fragment = new MonthCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        //앱바 타이틀 변경하기
        ((MainActivity) activity).setActionBarTitle(year+"년 "+(month+1)+"월");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_month_calendar, container, false);


        year = mParam1;
        month = mParam2;

        DisplayMetrics display = this.getResources().getDisplayMetrics();
        int width = display.widthPixels;
        int height = display.heightPixels;

        //gridView 일 표시
        String date = (month + 1) + "/" + "01/" + year;
        String pattern = "MM/dd/yyyy";
        Date selDate = null;
        try {
            selDate = new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(selDate);
        String test = "실패";
        if (selDate != null) {
            test = "성공";
        }

        firstDay = c.get(Calendar.DAY_OF_WEEK); //첫째날
        allDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);//그 달의 마지막 일

        ArrayList<Integer> days = new ArrayList<Integer>();

        for (int i = 1; i < firstDay; i++) {
            days.add(null);
        }
        for (int i = 1; i <= allDay; i++) {
            days.add(i);
        }
        while(days.size() != 42){
            days.add(null);
        }


        DayAdapter adapter = new DayAdapter(getActivity(), days, year, month, height, width);

        // 어탭터 연결
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }


}