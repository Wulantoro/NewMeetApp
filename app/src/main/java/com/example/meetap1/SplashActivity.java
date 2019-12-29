package com.example.meetap1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    int i = 0;

    private int waktu_loading=4000;
    ImageView image1,image2;
    Animation frombuttom,mytranslation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);

        image1 = findViewById(R.id.LogoMeetAp);
        image2 = findViewById(R.id.LogoMeetAp2);

        frombuttom = AnimationUtils.loadAnimation(this,R.anim.frombuttom);
        mytranslation = AnimationUtils.loadAnimation(this,R.anim.mytranslation);

        image1.startAnimation(frombuttom);
        image2.startAnimation(mytranslation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent home=new Intent(SplashActivity.this, TabActivity.class);
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
