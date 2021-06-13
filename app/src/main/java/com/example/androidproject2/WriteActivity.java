package com.example.androidproject2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static com.example.androidproject2.ScheduleDataBase.DATABASE_VERSION;
import static com.example.androidproject2.ScheduleDataBase.KEY_DAY;
import static com.example.androidproject2.ScheduleDataBase.KEY_EHOUR;
import static com.example.androidproject2.ScheduleDataBase.KEY_EMIN;
import static com.example.androidproject2.ScheduleDataBase.KEY_MEMO;
import static com.example.androidproject2.ScheduleDataBase.KEY_MONTH;
import static com.example.androidproject2.ScheduleDataBase.KEY_PLACE;
import static com.example.androidproject2.ScheduleDataBase.KEY_SHOUR;
import static com.example.androidproject2.ScheduleDataBase.KEY_SMIN;
import static com.example.androidproject2.ScheduleDataBase.KEY_TITLE;
import static com.example.androidproject2.ScheduleDataBase.KEY_YEAR;
import static com.example.androidproject2.ScheduleDataBase.TABLE_NAME;

public class WriteActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button saveBtn, delBtn, escBtn, findBtn;
    private TimePicker sTimePicker, eTimePicker;
    private EditText subTv, memoTv, addressTv;
    private String subText, memoText, addressText, date, sDate, eDate, title;
    private int sHour, sMin, eHour, eMin, year, month, day, time, _id;
    private Geocoder geocoder;
    private List<Address> addressList = null;
    private GoogleMap mMap;
    private static final String TAG = "WriteActivity";
    private Context context;
    private ScheduleDataBase scheduleDataBase;
    ScheduleDataBase mDb;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        context=this;

        Intent intent = getIntent();
        year = intent.getIntExtra("year", -1);
        month = intent.getIntExtra("month", -1);
        day = intent.getIntExtra("date", -1);
        time=intent.getIntExtra("time", -1);
        _id=intent.getIntExtra("_id", -1);
        Log.d(TAG, year + ", " + month + ", " + day);


        sTimePicker = (TimePicker) findViewById(R.id.startTimePicker);
        eTimePicker = (TimePicker) findViewById(R.id.endTimePicker);
        subTv = findViewById(R.id.subjectText);
        memoTv = findViewById(R.id.memoText);
        addressTv = findViewById(R.id.addressEnterText);
        saveBtn = findViewById(R.id.saveButton);
        delBtn = findViewById(R.id.deleteButton);
        escBtn = findViewById(R.id.escButton);
        findBtn = findViewById(R.id.findMapButton);
        geocoder = new Geocoder(getBaseContext());

        subTv.setText(year+"년 "+month+"월 "+day+"일 "+time+"시");

        title=String.valueOf(subTv.getText());


        scheduleDataBase= new ScheduleDataBase(WriteActivity.this);
        mDb = scheduleDataBase.getInstance(WriteActivity.this);

        loadData();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                Log.d(TAG,"map");
                //mMap.setMyLocationEnabled();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_id==-1)
                    save();
                else
                    updateRecord();
                //나가기
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_id!=-1) {
                    boolean check = false;
                    //다이어로그 출력/ 확인, 취소 받고 여튼 작성
                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
                    builder.setTitle("알림");
                    builder.setMessage("일정을 삭제하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteRecord();
                            //나가기
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                    builder.setNegativeButton("아니오", null);
                    builder.setNeutralButton("취소", null);
                    builder.create().show();
                }

            }
        });

        escBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //나가기
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findAddress();
            }
        });
    }

    public void findAddress(){//지도 찾기 함수
        addressText= String.valueOf(addressTv.getText());
        Log.d(TAG,addressText);
        try{
            addressList=geocoder.getFromLocationName(addressText,20);
            for(int i=0;i<addressList.size();i++) {
                String tmp= String.valueOf(addressList.get(i));
                Log.d(TAG, tmp);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        String []splitStr = addressList.get(0).toString().split(",");
        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소

        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
        String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
        // 좌표(위도, 경도) 생성
        LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        System.out.print(latitude);
        MarkerOptions mOptions2 = new MarkerOptions();
        mOptions2.title("search result");
        mOptions2.snippet(address);
        mOptions2.position(point);
        // 마커 추가
        mMap.addMarker(mOptions2);
        // 해당 좌표로 화면 줌
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        geocoder=new Geocoder(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){

            @Override
            public void onMapClick(@NonNull LatLng point) {
                MarkerOptions mOptions=new MarkerOptions();
                mOptions.title("좌표");

                double latitude=point.latitude;
                double longitude=point.longitude;

                mOptions.snippet(latitude+", "+longitude);

                mOptions.position(new LatLng(latitude,longitude));
                googleMap.addMarker(mOptions);
            }
        });

        findBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                findAddress();
            }
        });
    }

    //sql 저장 함수
    private void save(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){//시간 받기
            sHour=sTimePicker.getHour();
            sMin=sTimePicker.getMinute();
            eHour=eTimePicker.getHour();
            eMin=eTimePicker.getMinute();
        }
        else{
            sHour=sTimePicker.getCurrentHour();
            sMin=sTimePicker.getCurrentMinute();
            eHour=eTimePicker.getCurrentHour();
            eMin=eTimePicker.getCurrentMinute();
        }
        subText= String.valueOf(subTv.getText());//제목 받기
        addressText=String.valueOf(addressTv.getText());//주소 받기
        memoText= String.valueOf(memoTv.getText());//메모 받기


        Log.d(TAG, year+"년"+ month+"월"+ day+"일"+ subText+sHour+"시"+ sMin+"분"+ eHour+"시"+ eMin+"분"+ addressText+ memoText);

        String Insert_SQL = String.format(
                "INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES(%d,%d,%d,'%s',%d,%d,%d,%d,'%s','%s')",
                TABLE_NAME,
                KEY_YEAR,
                KEY_MONTH,
                KEY_DAY,
                KEY_TITLE,
                KEY_SHOUR,
                KEY_SMIN,
                KEY_EHOUR,
                KEY_EMIN,
                KEY_PLACE,
                KEY_MEMO,
                year, month, day, title, sHour, sMin, eHour, eMin, addressText, memoText);
        try {
        mDb.execSQL(Insert_SQL);
        } catch (SQLException e) {
            Log.e(TAG, "Error in inserting recodes");
        }

    }

    //sql 삭제 함수
    private void deleteRecord(){
        subText= String.valueOf(subTv.getText());//제목 받기

        String delete_SQL = String.format(
                "DELETE FROM %s WHERE id=%d",
                TABLE_NAME, _id);

        try{
            mDb.execSQL(delete_SQL);
        } catch (SQLException e) {
            Log.e(TAG, "Error in updating recodes");
        }
    }

    //sql 업데이트 함수
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateRecord(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){//시간 받기
            sHour=sTimePicker.getHour();
            sMin=sTimePicker.getMinute();
            eHour=eTimePicker.getHour();
            eMin=eTimePicker.getMinute();
        }
        else{
            sHour=sTimePicker.getCurrentHour();
            sMin=sTimePicker.getCurrentMinute();
            eHour=eTimePicker.getCurrentHour();
            eMin=eTimePicker.getCurrentMinute();
        }
        subText= String.valueOf(subTv.getText());//제목 받기
        addressText=String.valueOf(addressTv.getText());//주소 받기
        memoText= String.valueOf(memoTv.getText());//메모 받기

        String update_SQL = String.format(
                "UPDATE %s SET %s = '%s', %s = %d, %s = %d, %s = %d, %s = %d, %s = '%s', %s ='%s' WHERE id=%d",
                TABLE_NAME,
                KEY_TITLE, title,
                KEY_SHOUR, sHour,
                KEY_SMIN, sMin,
                KEY_EHOUR, eHour,
                KEY_EMIN, eMin,
                KEY_PLACE, addressText,
                KEY_MEMO, memoText,
                _id);

        try {
            mDb.execSQL(update_SQL);
        } catch (SQLException e) {
            Log.e(TAG, "Error in updating recodes");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadData(){
        if(_id!=-1){
        String sql = "SELECT * FROM " +
                TABLE_NAME +
                " WHERE id=" + _id;

        if(mDb != null) {
            Cursor outCursor = mDb.rawQuery(sql);
            do {
                outCursor.moveToNext();
            } while (_id != outCursor.getInt(0));

            String getYear = outCursor.getString(1);
            String getMonth = outCursor.getString(2);
            String getDay = outCursor.getString(3);
            String getTitle = outCursor.getString(4);
            String getSHour = outCursor.getString(5);
            String getSMin = outCursor.getString(6);
            String getEHour = outCursor.getString(7);
            String getEMin = outCursor.getString(8);
            String getPlace = outCursor.getString(9);
            String getMemo = outCursor.getString(10);

            subTv.setText(getTitle);
            sTimePicker.setHour(Integer.parseInt(getSHour));
            sTimePicker.setMinute(Integer.parseInt(getSMin));
            eTimePicker.setHour(Integer.parseInt(getEHour));
            eTimePicker.setMinute(Integer.parseInt(getEMin));
            addressTv.setText(getPlace);
            memoTv.setText(getMemo);

            outCursor.close();
        }

        }

    }


/*
    private void viewAllRecord(){
        Cursor cursor = scheduleDataBase.getTodaySchedules(year, month, day);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.text_item, cursor, new String[]{
                KEY_TITLE},
                new int[]{R.id.text2}, 0);

        ListView lv = (ListView)findViewById(R.id.list_item);
        lv.setAdapter(adapter);
    }
    */

}