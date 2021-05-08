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
    private ArrayList<Integer> items = new ArrayList<Integer>();
    private View view;
    private int year,month,day, position;
    TextView tempView;
    private int height,width;//화면의 높이, 너비
    private WeekAdapter weekAdapter;//시간 선택 tempView를 확인하기 위해서

    OnItemClickListener listener;

    public WeekDateAdapter(Context context, ArrayList<Integer> items, int year, int month, int day, int height ,int width,WeekAdapter weekAdapter) {
        this.mContext = context;
        this.items = items;
        this.year=year;
        this.month=month+1;
        this.height=height;
        this.width=width;
        this.weekAdapter=weekAdapter;

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

        Log.d(TAG,"create "+position);

        TextView itemTv=view.findViewById(R.id.text);

        if(width<height)//세로
            itemTv.setHeight(153);
        else//가로
            itemTv.setHeight(135);

        if(items.get(position)==null){//여백
            itemTv.setText("");
        }else {//날짜 확인 가능
            itemTv.setText(items.get(position)+"");
        }
        itemTv.setBackgroundColor(Color.WHITE);


        if(position == 0)//일요일 빨간색 표시
            itemTv.setTextColor(Color.RED);


        if(position == 6)//토요일 파란색 표시
            itemTv.setTextColor(Color.BLUE);

        itemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "클릭");

                if(tempView !=null)//그 전에 선택 날짜 바탕 하얀색으로 변경
                    tempView.setBackgroundColor(Color.WHITE);

                if(weekAdapter.tempView!=null){//그 전에 선택 시간 바탕 하얀색으로 변경
                    weekAdapter.tempView.setBackgroundColor(Color.WHITE);
                    weekAdapter.tempView=null;
                }


                for(int i=0;i<position;i++){
                    //날짜가 넘어가면 (만약 화요일(position 2)이 1일이면 -1(월요일 position)을 했을 때 0이 되므로 한 달이 넘어갔음을 확인 가능함
                    if(((int)getItem(position)-i)<=0){
                        month++;
                        break;
                    }
                }

                tempView=itemTv;//다음을 위해 저장

                if(items.get(position)!=null)//여백이 아닐 때
                    print(year + "년" + (month) + "월" + getItem(position) + "일");//토스트 메시지 출력
                    Log.d(TAG, year + "년" + (month) + "월" + getItem(position) + "일");//로그 확인
                    itemTv.setBackgroundColor(Color.CYAN);//색깔 변경

            }
        });

        return view;
    }

    void print(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
}
