package com.example.androidproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class LandscapeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscape);

        if(getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT){
            Intent intent=new Intent(getApplicationContext(), PortraitActivity.class);
            startActivity(intent);
        }
        else{

        }
    }
}