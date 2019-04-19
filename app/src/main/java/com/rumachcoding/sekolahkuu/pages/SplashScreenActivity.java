package com.rumachcoding.sekolahkuu.pages;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rumachcoding.sekolahkuu.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent pindahList = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(pindahList);

                finish();
            }
        },3000);
    }
}
