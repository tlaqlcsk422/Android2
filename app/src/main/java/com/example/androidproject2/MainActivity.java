package com.example.androidproject2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE){
            Intent intent=new Intent(getApplicationContext(), PortraitActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(getApplicationContext(), LandscapeActivity.class);
            startActivity(intent);
        }


    }

}