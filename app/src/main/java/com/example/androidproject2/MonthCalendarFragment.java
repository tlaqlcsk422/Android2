package com.example.androidproject2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.androidproject2.R.string.app_name;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthCalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int year;
    private int month;

    int firstDay; //시작 요일
    int allDay; //한달 일 수

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;

    public MonthCalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MonthCalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
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
/*
    public static int getYear() {
        return year;
    }

    public static int getMonth() {
        return month;
    }

 */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_month_calendar, container, false);


        year = mParam1;
        month = mParam2;


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


        DayAdapter adapter = new DayAdapter(getActivity(), days, year, month);

        // 어탭터 연결
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(adapter);


        // Inflate the layout for this fragment
        return rootView;
    }



}