package com.example.androidproject2;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WeekDateAdapter extends BaseAdapter implements OnItemClickListener{

    private static final String TAG="Week Date Adapter View";

    private final Context mContext;
    private LayoutInflater inflater;
    private ArrayList<String> items = new ArrayList<String>();
    private View view;
    private int year,month,day, position;

    OnItemClickListener listener;

    public WeekDateAdapter(Context context, ArrayList<String> items, int year, int month, int day) {
        this.mContext = context;
        this.items = items;
        this.year=year;
        this.month=month+1;

        this.day=day;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView( int position, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.text_item, parent, false);
            this.view = view;
        }
        this.position=position;

        TextView itemTv=view.findViewById(R.id.text);

        if(items.get(position)==null){
            itemTv.setText("");
        }else {
            itemTv.setText(items.get(position));
        }
        itemTv.setBackgroundColor(Color.WHITE);

            //일요일 빨간색 표시
        if(position == 0)
            itemTv.setTextColor(Color.RED);

            //토요일 파란색 표시
        if(position == 6)
            itemTv.setTextColor(Color.BLUE);

        itemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "클릭");
                //다른 포지션 배경색 white로 바꾸기 추가해야됨
                //Intent intent=new Intent();
                //intent.putExtra("position",position);

                print(year + "년" + month + "월" + getItem(position % 8) + "일");
                Log.d(TAG, year + "년" + month + "월" + getItem(position % 8) + "일");
                itemTv.setBackgroundColor(Color.CYAN);

            }
        });

        return view;
    }

    void print(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
}
