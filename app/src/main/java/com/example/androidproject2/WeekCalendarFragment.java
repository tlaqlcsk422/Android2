package com.example.androidproject2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;


public class WeekCalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;
    private int mParam3;

    private int year;
    private int month;
    private int date;

    //OnItemClickListener listener;


    private static final String TAG="Week Calendar Fragment";

    public WeekCalendarFragment() {
        // Required empty public constructor
    }

    public static WeekCalendarFragment newInstance(int year, int month,int date) {
        WeekCalendarFragment fragment = new WeekCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        args.putInt(ARG_PARAM3,date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
        }
    }

    /*public void setOnItemClickListener (View.OnClickListener listener){
        this.listener = listener;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week_calendar, container, false);


        year = mParam1;
        month = mParam2;
        date =mParam3;

        //gridView 일 표시

        Calendar c = Calendar.getInstance();
        c.set(year,month, 1);

        Log.d(TAG,year+"/"+(month+1)+"/"+date);

        ArrayList<String> dateItems = new ArrayList<String>();
        ArrayList<String> items = new ArrayList<String>();



        dateItems.add(null);
        for (int i = 0; i < 7; i++) {
            int tempDate= date+i;
            if(tempDate>c.getActualMaximum(Calendar.DAY_OF_MONTH)){

                Log.d(TAG,month+" check");
                tempDate= date+i -c.getActualMaximum(Calendar.DAY_OF_MONTH);
                Log.d(TAG,year+"/"+(month+2)+"/"+tempDate);
            }
            else{
                Log.d(TAG,year+"/"+(month+1)+"/"+tempDate);
            }
            dateItems.add(tempDate+"");
        }

        WeekDateAdapter dateAdapter=new WeekDateAdapter(getActivity(),dateItems,year,month,date);

        GridView dateGridView=(GridView) rootView.findViewById(R.id.weekDate);
        dateGridView.setAdapter(dateAdapter);


        for(int i=0;i<24;i++){
            String tmp= String.format("%2d",i);
            items.add(tmp);
            for(int j=0;j<7;j++){
                items.add(null);
            }
        }

        WeekAdapter adapter = new WeekAdapter(getActivity(), items, year, month, date);

        // 어탭터 연결
        GridView gridView = (GridView) rootView.findViewById(R.id.weekGridView);
        gridView.setAdapter(adapter);


        // Inflate the layout for this fragment
        return rootView;
    }
}