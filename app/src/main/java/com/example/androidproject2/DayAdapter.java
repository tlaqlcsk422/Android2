package com.example.androidproject2;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class DayAdapter extends BaseAdapter implements OnItemClickListener{

        private static final String TAG="grid Adapter View";

        private final Context mContext;
        private LayoutInflater inflater;
        private ArrayList<Integer> items = new ArrayList<Integer>();//일(1~31)을 저장할 벡터
        private View view;
        private int year,month;
        private int height,width;//화면의 높이, 너비
        TextView tempView;

        OnItemClickListener listener;


    public DayAdapter(Context context, ArrayList < Integer > items, int year, int month, int height, int width) {
        this.mContext = context;
        this.items = items;
        this.year=year;
        this.month=month+1;
        this.height=height;
        this.width=width;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener (OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener 클릭) {
        this.listener=listener;
    }

    @Override
    public int getCount () {
        return items.size();
    }

    @Override
    public Object getItem ( int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView ( int position, View view, ViewGroup parent){
        if (view == null) {
            view = inflater.inflate(R.layout.text_item, parent, false);
            this.view = view;
        }

        Log.d(TAG,"create "+position);

        TextView dayTv = view.findViewById(R.id.text);//시작 문자는 대문자 X 소문자로 시작

        //화면 가로 세로에 따른 아이템 높이조정
        //int gridviewH = view.getHeight() / 6;
        if(width<height) { //세로일때
            dayTv.setHeight(253);
        }
        else
            dayTv.setHeight(135);

        if(items.get(position) == null) {//null 확인 후에 공백 문자 넣음
            dayTv.setText("");
        } else {
            dayTv.setText(items.get(position) + "");//String 으로 해야해서 +"" 추가함

            if(tempView != null)
                tempView.setBackgroundColor(Color.CYAN);
            //일요일 빨간색 표시
            if(position % 7 == 0)
                dayTv.setTextColor(Color.RED);

            //토요일 파란색 표시
            if(position % 7 == 6)
                dayTv.setTextColor(Color.BLUE);

        }
        Log.d(TAG, items.get(position) + ", "+dayTv.getText());

        dayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "클릭");
                //이전 포지션 배경색 white로 바꾸기
                if(tempView !=null)
                    tempView.setBackgroundColor(Color.WHITE);
                //클릭한 아이템 배경색을 cyan으로 변경
                if (getItem(position) != null) {//null 값일 때 출력 X
                    dayTv.setBackgroundColor(Color.CYAN);
                    tempView = dayTv;
                    print(year + "년" + month + "월" + ((int) getItem(position)) + "일");
                    Log.d(TAG, year + "년" + month + "월" + ((int) getItem(position)) + "일");
                }

            }
        });

        return view;
    }


    void print(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
}