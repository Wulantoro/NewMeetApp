package com.example.meetap1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class TabActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabPageAdapter tabPageAdapter;
    TabLayout tab;
    Button start;
    TextView swipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tab);
        super.onCreate(savedInstanceState);

        //when this activity is about to launch we need to check its opened true or false
        if (restorePrefData()){
            Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(mainActivity);
            finish();
        }

        //hide status bar
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        swipe = findViewById(R.id.Swipe);
        start = findViewById(R.id.Start);
        tab = findViewById(R.id.Tab);

        //Items on page
        final List<Items> content = new ArrayList<>();
        // \n this to next line
        content.add(new Items("Selamat Datang","Efisien waktu, langsung solve","Efisian waktu mu dengan \nlangsung tanya diMeetAp ",R.drawable.one ));
        content.add(new Items("","Kamu bisa langsung di remote","Tanya-tanya di#MeetAp kamu bisa\n langsung diremote oleh solver \n sampai error programmu solve ",R.drawable.dua));
        content.add(new Items("","Dari pada tanya diforum kamu dibully","Kalau tanya-tanya di forum fb,\n telegram ataupun forum lainnya kamu\n sering dibully, kalau di #MeetAp aman",R.drawable.tiga));
        content.add(new Items("","Yuk tanya langsung ke ahlinya","Share error mu. Tanya langsung\n dengan para Solver, dan temukan solusinya ",R.drawable.empat));


        //set viewPager
        viewPager = findViewById(R.id.viewPager);
        tabPageAdapter = new TabPageAdapter(this, content);
        viewPager.setAdapter(tabPageAdapter);
        //setup the tab layout with viewPager
        tab.setupWithViewPager(viewPager);


        //tabLayout swipe
        tab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //If tab layout switch to last page the button will pop with animation
                if (tab.getPosition() == content.size() - 1){
                    animateViewIn();
                }else if (tab.getPosition() == content.size() - 2) {
                    animateViewOut();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //get start button click listener
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //it already checked the TabActivity use shared preference to know true or false
                savePrefData();
                //If yes open MainActivity
                Intent main = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(main);
                finish();
            }
        });

    }

    private void animateViewOut(){
        swipe.setVisibility(View.VISIBLE);
        start.setVisibility(View.GONE);
        tab.setVisibility(View.VISIBLE);
    }

    private void animateViewIn(){
        //Hiding swip right text, tabs, and set Start Button Visible
        swipe.setVisibility(View.INVISIBLE);
        start.setVisibility(View.VISIBLE);
        tab.setVisibility(View.INVISIBLE);

        ViewGroup root = findViewById(R.id.one);
        int count = root.getChildCount();
        float offSet = getResources().getDimensionPixelSize(R.dimen.offset);
        Interpolator interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        //duration + interpolator
        for (int i = 0; i < count; i++ ){
            View view = root.getChildAt(i);
            view.setVisibility(View.VISIBLE);
            view.setTranslationX(offSet);
            view.setAlpha(0.85f);
            view.animate()
                    .translationX(0f)
                    .translationY(0f)
                    .alpha(1f)
                    .setInterpolator(interpolator)
                    .setDuration(1000L)
                    .start();
            offSet *= 1.5f;
        }
    }

    private void savePrefData(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Pre",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Opened", true);
        editor.apply();
    }
    private boolean restorePrefData(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Pre",MODE_PRIVATE);
        boolean ActivityOpen;
        ActivityOpen = preferences.getBoolean("Opened", false);
        return ActivityOpen;

    }

}

