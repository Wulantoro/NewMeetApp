package com.example.meetap1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    int i = 0;

    private int waktu_loading=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent home=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(home);
                finish();

            }
        },waktu_loading);

//        //inisialisasi
//        progressBar = findViewById(R.id.pb);
//
//
//        //Progress bar Color
//        progressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
//        progressBar.setMax(5000/1000);
//        countDownTimer = new CountDownTimer(5000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                i += 1;
//                progressBar.setProgress(i);
//            }
//
//            @Override
//            public void onFinish() {
//                i += 1;
//                progressBar.setProgress(i);
//                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        };
//
//        countDownTimer.start();


    }
}
