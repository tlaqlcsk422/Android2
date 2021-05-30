package com.example.androidproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class WriteActivity extends AppCompatActivity {
    private Button saveBtn, delBtn, escBtn, findBtn;
    private TimePicker sTimePicker, eTimePicker;
    private TextView subTv,memoTv, addressTv;
    private String subText, memoText, addressText, date;
    private int sHour, sMin,eHour, eMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        sTimePicker=(TimePicker) findViewById(R.id.startTimePicker);
        eTimePicker=(TimePicker) findViewById(R.id.endTimePicker);
        subTv=findViewById(R.id.subjectText);
        memoTv=findViewById(R.id.memoText);
        addressTv=findViewById(R.id.addressEnterText);
        saveBtn=findViewById(R.id.saveButton);
        delBtn=findViewById(R.id.deleteButton);
        escBtn=findViewById(R.id.escButton);
        findBtn=findViewById(R.id.findMapButton);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

        }
    }
}