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

public class WeekTimeAdapter extends BaseAdapter implements OnItemClickListener{

    private static final String TAG="Week Time Adapter View";

    private final Context mContext;
    private LayoutInflater inflater;
    private ArrayList<String> items = new ArrayList<String>();//시간 ArrayList
    private View view;
    private int year,month,day, position;
    private int height,width;//화면의 높이, 너비

    OnItemClickListener listener;

    public WeekTimeAdapter(Context context, ArrayList<String> items, int year, int month, int day, int height , int width) {
        this.mContext = context;
        this.items = items;
        this.year=year;
        this.month=month+1;
        this.height=height;
        this.width=width;

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

        //Log.d(TAG,"create "+position);

        TextView itemTv=view.findViewById(R.id.text);

        if(width<height)//세로
            itemTv.setHeight(253);
        else//가로
            itemTv.setHeight(135);


        itemTv.setText(items.get(position));//시간 입력


        itemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "클릭");

                print((position)+"시");//클릭한 시간
            }
        });

        return view;
    }

    void print(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

    private void setGridViewBackgroundColor(){

    }

}
