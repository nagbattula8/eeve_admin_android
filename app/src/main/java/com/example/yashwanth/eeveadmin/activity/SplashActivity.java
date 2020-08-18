package com.example.yashwanth.eeveadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.yashwanth.eeveadmin.AdminHome;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getJSON();
        // Start home activity
        startActivity(new Intent(SplashActivity.this, AdminHome.class));


        // close splash activity
        finish();
    }


}
