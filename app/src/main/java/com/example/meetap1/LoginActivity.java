package com.example.meetap1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private RelativeLayout rl2;
    private TextView TvDaftar;
    private EditText etPass, etEmail;
    private TextView tvDaftar, tvPassError;
    private Button btnLogin;
    private ImageView eye;
    Dialog mCobalagi;

    boolean isPlay = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //popUp
        mCobalagi = new Dialog(LoginActivity.this);

        //RelativeLayout
        rl2 = findViewById(R.id.rlRed2);

        eye = findViewById(R.id.imgEye);

        etPass = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);

        btnLogin = findViewById(R.id.btnLogin);

        tvDaftar = findViewById(R.id.tvDaftar);
        tvPassError = findViewById(R.id.tvErrorPass);

        //Show Error
        rl2.setVisibility(View.INVISIBLE);
        tvPassError.setVisibility(View.INVISIBLE);

        tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(go);
            }
        });

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay){
                    v.setBackgroundResource(R.drawable.ic_hide);
                    etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }else {
                    v.setBackgroundResource(R.drawable.ic_show);
                    etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                isPlay = !isPlay;
            }
        });

        //btnLogin
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validasi
//                validasiLogin();
                login();

            }
        });

    }

    private void validasiLogin() {
        final String Password = etPass.getText().toString().trim();

        if (Password.isEmpty()){
            etPass.requestFocus();
            rl2.setVisibility(View.VISIBLE);
            tvPassError.setVisibility(View.VISIBLE);
        }else if (Password.length()< 6){
            showPopUp();
        }else {
            Intent go = new Intent(LoginActivity.this, PasswordTrueActivity.class);
            startActivity(go);
        }
    }

    private void showPopUp() {
        TextView tvCobaLagi;
        mCobalagi.setContentView(R.layout.popuploginfalse);
        mCobalagi.setCancelable(false);

        tvCobaLagi = mCobalagi.findViewById(R.id.tvCobaLagi);

        tvCobaLagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPass.requestFocus();
                mCobalagi.dismiss();
            }
        });

        mCobalagi.show();
    }

    private void login() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", etEmail.getText().toString());
            jsonObject.put("password", etPass.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("http://ask.meetap.id/api/auth/login/")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            String status = response.getString("status");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            if (status.equals("success")) {
                                Intent go = new Intent(LoginActivity.this, PasswordTrueActivity.class);
                                startActivity(go);

                            } else {
                                showPopUp();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONExceptions" + e, Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Gagal Login", Toast.LENGTH_LONG).show();

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
