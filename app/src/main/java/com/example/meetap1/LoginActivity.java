package com.example.meetap1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private TextView TvDaftar;
    private EditText etPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TvDaftar = findViewById(R.id.tvRegister);
        //etPass = findViewById(R.id.etPassword);

        TvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(go);
            }
        });


    }

//    public void ShowHidePass(View view) {
//        if (view.getId() == R.id.ShowPass){
//            if (etPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
//
//
//                etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }else {
//
//                etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }
//        }
//    }
}
