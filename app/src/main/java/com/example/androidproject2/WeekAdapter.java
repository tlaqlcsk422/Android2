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

public class WeekAdapter  extends BaseAdapter implements OnItemClickListener{

    private static final String TAG="Week grid Adapter View";

    private final Context mContext;
    private LayoutInflater inflater;
    private ArrayList<String> items = new ArrayList<String>();
    private View view;
    private int year,month,day;

    OnItemClickListener listener;

    public WeekAdapter(Context context, ArrayList<String> items, int year, int month, int day) {
        this.mContext = context;
        this.items = items;
        this.year=year;
        this.month=month+1;

        this.day=day;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
    public void setOnItemClickListener (OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
            return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener 클릭) {
        this.listener=listener;
    }

    @Override
    public View getView( int position, View View, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.text_item, parent, false);
            this.view = view;
        }

        Log.d(TAG,"create "+position);

        Log.d(TAG,"여기까지---------------------------------------------------------");

        for(int i=0;i<items.size();i++) {
            Log.d(TAG, items.get(i)+"");
        }

        TextView itemTv=view.findViewById(R.id.text);

        if(items.get(position)==null){
            itemTv.setText("");
        }
        else{
            itemTv.setText(items.get(position));
            itemTv.setBackgroundColor(Color.WHITE);

            //일요일 빨간색 표시
            if(position == 1)
                itemTv.setTextColor(Color.RED);

            //토요일 파란색 표시
            if(position == 7)
                itemTv.setTextColor(Color.BLUE);

            //오늘 날짜 받아오기
            Calendar cal = Calendar.getInstance();
            int nowYear = cal.get(Calendar.YEAR);
            int nowMonth = cal.get(Calendar.MONTH);
            nowMonth++;
            int date = cal.get(Calendar.DATE);

        }

        Log.d(TAG,position+": "+itemTv.getText()+" check");

        /*itemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "클릭");
                //다른 포지션 배경색 white로 바꾸기 추가해야됨
                if (getItem(position) != null) {//null 값일 때 출력 X
                    itemTv.setBackgroundColor(Color.CYAN);

                    print(year + "년" + month + "월" + ((int) getItem(position)) + "일");
                    Log.d(TAG, year + "년" + month + "월" + ((int) getItem(position)) + "일");
                }

            }
        });*/

        return View;
    }

    void print(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

}
