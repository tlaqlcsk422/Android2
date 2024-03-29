package com.example.androidproject2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.androidproject2.ScheduleDataBase.KEY_DAY;
import static com.example.androidproject2.ScheduleDataBase.KEY_MONTH;
import static com.example.androidproject2.ScheduleDataBase.KEY_TITLE;
import static com.example.androidproject2.ScheduleDataBase.KEY_YEAR;
import static com.example.androidproject2.ScheduleDataBase.TABLE_NAME;

public class DayAdapter extends BaseAdapter implements OnItemClickListener{

        private static final String TAG="grid Adapter View";

        private final Context mContext;
        private LayoutInflater inflater;
        private ArrayList<Integer> items = new ArrayList<Integer>();//일(1~31)을 저장할 벡터
        private View view;
        private int year,month, day;
        private int height,width;//화면의 높이, 너비
        TextView tempView;
        OnItemClickListener listener;
        private ScheduleDataBase scheduleDataBase;
        ScheduleDataBase mDb;
        private ArrayList<Integer> Years = new ArrayList<>();
        private ArrayList<Integer> Months = new ArrayList<>();
        private ArrayList<Integer> Days = new ArrayList<>();


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
            view = inflater.inflate(R.layout.day_item, parent, false);
            this.view = view;
        }

        TextView dayTv = view.findViewById(R.id.text);//시작 문자는 대문자 X 소문자로 시작

        //화면 가로 세로에 따른 아이템 높이조정
        //int gridviewH = view.getHeight() / 6;
        if(width<height) { //세로일때
            dayTv.setHeight(253);
        }
        else {
            dayTv.setHeight(135);
        }

        if(items.get(position) == null) {//null 확인 후에 공백 문자 넣음
            dayTv.setText("");

        } else {
            dayTv.setText(items.get(position) + "");//String 으로 해야해서 +"" 추가함

            int oneDay = (Integer) getItem(position);
            loadData();
            for(int i=0; i<2&&i<Years.size(); i++) {
                Log.d(TAG,Years.get(i)+"/"+Months.get(i)+"/"+Days.get(i)+"    "+year+"/"+month+"/"+oneDay);

                if (Years.get(i) == year && Months.get(i) == month && Days.get(i) == oneDay) {
                    ArrayList<String> title = new ArrayList<String>();
                    title=loadTitle(oneDay);

                    Log.d(TAG,year+"/"+month+"/"+oneDay+title);
                    SchaduleAdapter adapter = new SchaduleAdapter(mContext, title);
                    // 어탭터 연결
                    ListView listView = (ListView) view.findViewById(R.id.list_item);
                    listView.setAdapter(adapter);
                }
            }

            if(tempView != null)
                tempView.setBackgroundColor(Color.CYAN);
            //일요일 빨간색 표시
            if(position % 7 == 0)
                dayTv.setTextColor(Color.RED);

            //토요일 파란색 표시
            if(position % 7 == 6)
                dayTv.setTextColor(Color.BLUE);

        }
        dayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이전 포지션 배경색 white로 바꾸기
                if(tempView !=null)
                    tempView.setBackgroundColor(Color.WHITE);
                //클릭한 아이템 배경색을 cyan으로 변경
                if (getItem(position) != null) {//null 값일 때 출력 X
                    dayTv.setBackgroundColor(Color.CYAN);
                    tempView = dayTv;
                    print(year + "년" + month + "월" + ((int) getItem(position)) + "일");
                }

                day = (Integer)getItem(position);

                MainActivity.year=year;
                MainActivity.month=month;
                MainActivity.date=day;
                MainActivity.time=0;

            }
        });
        return view;
    }


    private ArrayList<String> loadTitle(int oneDay) {

        ArrayList<String> title = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + KEY_YEAR + "=" + year + " AND " + KEY_MONTH + "=" + month + " AND " + KEY_DAY + "=" + oneDay;

        int recordCount = -1;
        ScheduleDataBase database = ScheduleDataBase.getInstance(mContext);

        if (database != null) {
            Cursor outCursor = database.rawQuery(sql);
            recordCount = outCursor.getCount();

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();
                int _id = outCursor.getInt(0);
                String getTitle = outCursor.getString(4);
                title.add(getTitle);
                Log.d(TAG, "#" + i + " -> " + _id + ", " + getTitle);
            }
        }
        return title;
    }


    private void loadData() {
        String sql = "SELECT * FROM " + TABLE_NAME ;
        int recordCount = -1;
        ScheduleDataBase database = ScheduleDataBase.getInstance(mContext);
        if (database != null) {
            Cursor outCursor = database.rawQuery(sql);
            recordCount = outCursor.getCount();

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();
                int getYear = outCursor.getInt(1);
                int getMonth = outCursor.getInt(2);
                int getDay = outCursor.getInt(3);
                Years.add(getYear);
                Months.add(getMonth);
                Days.add(getDay);
            }
            outCursor.close();
        }
    }


    void print(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

}   