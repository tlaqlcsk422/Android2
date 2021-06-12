package com.example.androidproject2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class WriteActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button saveBtn, delBtn, escBtn, findBtn;
    private TimePicker sTimePicker, eTimePicker;
    private EditText subTv, memoTv, addressTv;
    private String subText, memoText, addressText, date, sDate, eDate;
    private int sHour, sMin, eHour, eMin, year, month, day, time;
    private Geocoder geocoder;
    private List<Address> addressList = null;
    private GoogleMap mMap;
    private static final String TAG = "WriteActivity";
    private DBHelper mDbHelper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Intent intent = getIntent();
        year = intent.getIntExtra("year", -1);
        month = intent.getIntExtra("month", -1);
        day = intent.getIntExtra("date", -1);
        time=intent.getIntExtra("time", -1);
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
                insertRecord();
                //나가기
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check=false;
                //다이어그램 출력/ 확인, 취소 받고 여튼 작성
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

                builder.setNegativeButton("아니오",null);
                builder.setNeutralButton("취소",null);
                builder.create().show();

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
    private void insertRecord(){
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
        memoText= String.valueOf(memoTv.getText());//메모 받기
        subText= String.valueOf(subTv.getText());//제목 받기
        addressText=String.valueOf(addressTv.getText());//주소 받기

        //저장
        mDbHelper.insertSchadule(year, month, day, subText,
                sHour, sMin, eHour, eMin, addressText, memoText);
    }

    //sql 삭제 함수
    private void deleteRecord(){
        mDbHelper.deleteSchadule(year, month, day);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateRecord(){
        memoText = memoTv.getText().toString();
        sHour = sTimePicker.getHour();
        sMin = sTimePicker.getMinute();
        eHour = eTimePicker.getHour();
        eMin = eTimePicker.getMinute();
        mDbHelper.updateSchadule(year, month, day, subText,
                sHour, sMin, eHour, eMin, addressText, memoText);
    }

    private void viewAllRecord(){
        Cursor cursor = mDbHelper.getAllSchadules();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.text_item, cursor, new String[]{
                Schedule.Schadules._ID,
                Schedule.Schadules.KEY_TITLE},
                new int[]{R.id.text2}, 0);

        ListView lv = (ListView)findViewById(R.id.list_item);
        lv.setAdapter(adapter);

    }

}