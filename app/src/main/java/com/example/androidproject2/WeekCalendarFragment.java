package com.example.androidproject2;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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


    private static final String TAG="Week Calendar Fragment";

    public WeekCalendarFragment() {
        // Required empty public constructor
    }

    public static WeekCalendarFragment newInstance(int year, int month,int date) {//년도, 달, 날짜 받기
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
        View rootView = inflater.inflate(R.layout.fragment_week_calendar, container, false);

        year = mParam1;
        month = mParam2;
        date =mParam3;

        DisplayMetrics display=this.getResources().getDisplayMetrics();
        int width=display.widthPixels;
        int height=display.heightPixels;

        //gridView 일 표시
        Calendar c = Calendar.getInstance();
        c.set(year,month, 1);

        Log.d(TAG,year+"/"+(month+1)+"/"+date);

        ArrayList<Integer> dateItems = new ArrayList<Integer>();//날짜 저장 ArrayList
        ArrayList<String> timeItems =new ArrayList<String>();
        ArrayList<String> items = new ArrayList<String>();//여백 저장 ArrayList

        //Time과 여백을 각각 설정, Adapter도 따로 설정


        for(int i=0;i<24;i++){
            String tmp= String.format("%2d",i);//시간 저장, 2자리 수 맞춤
            timeItems.add(tmp);
        }

        WeekTimeAdapter timeAdapter=new WeekTimeAdapter(getActivity(),timeItems,year,month,date,height,width);

        //어댑터 연결
        GridView timeGridView =(GridView) rootView.findViewById(R.id.weekTime);
        timeGridView.setAdapter(timeAdapter);

        int expandSpec= View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, View.MeasureSpec.AT_MOST);
        timeGridView.measure(0,expandSpec);
        timeGridView.getLayoutParams().height=timeGridView.getMeasuredHeight();


        for(int i=0;i<7*24;i++){//여백 공간 추가하기
            items.add(null);
        }

        WeekAdapter adapter = new WeekAdapter(getActivity(), items, year, month, date,height,width);

        //어탭터 연결
        GridView gridView = (GridView) rootView.findViewById(R.id.weekGridView);
        gridView.setAdapter(adapter);

        int expandSpec2= View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, View.MeasureSpec.AT_MOST);
        gridView.measure(0,expandSpec2);
        gridView.getLayoutParams().height=gridView.getMeasuredHeight();

        /*
        * 시간을 스크롤해도 날짜도 같이 스크롤 되면 안 되기 때문에 gridview를 따로 만들어서 각각 저장함
        * 사용할 함수가 조금씩 다르기 때문에 Adapter도 각각 따로 제작함 
        * */

        dateItems.add(null);
        for (int i = 0; i < 7; i++) {
            int tempDate= date+i;
            if(tempDate>c.getActualMaximum(Calendar.DAY_OF_MONTH)){//날짜 넘어갔을 때
                tempDate= date+i -c.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            dateItems.add(tempDate);
        }

        WeekDateAdapter dateAdapter=new WeekDateAdapter(getActivity(),dateItems,year,month,date,height,width,adapter);//시간 선택의 tempView를 확인하기 위해서 adapter도 넘김
        //어댑터 연결
        GridView dateGridView=(GridView) rootView.findViewById(R.id.weekDate);
        dateGridView.setAdapter(dateAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }

}