package com.example.meetap1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.example.meetap1.Model.User;
import com.example.meetap1.Model.UserId;
import com.example.meetap1.Utils.AppPermission;
import com.example.meetap1.Utils.GlobalHelper;
import com.example.meetap1.Utils.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private static final String[] ALL_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 901;
    private static final int READ_EXTERNAL_STORAGE_CODE = 902;
    private static final int CAMERA_CODE = 904;
    private static final int ACCESS_CALL_PHONE = 903;
    private static final int ALL_REQUEST_CODE = 999;
    private AppPermission mRuntimePermission;


    private RelativeLayout rl2;
    private TextView TvDaftar;
    private EditText etPass, etEmail;
    private TextView tvDaftar, tvPassError;
    private Button btnLogin;
    private ImageView eye;
    Dialog mCobalagi;
    public SharedPreferences pref, prf;

    boolean isPlay = false;
    private Gson gson;
    private String idUser;

    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long filesize = 0;

    private static String TAG = LoginActivity.class.getSimpleName();

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();


        mRuntimePermission = new AppPermission(this);

        if (mRuntimePermission.hasPermission(ALL_PERMISSIONS)) {
            GlobalHelper.createFolder();
        }else{
            mRuntimePermission.requestPermission(this, ALL_PERMISSIONS, ALL_REQUEST_CODE);
        }

        SharedPref sharedPref;
        sharedPref = new SharedPref(this);

        if (sharedPref.getSPSudahLogin()) {
            startActivity(new Intent(LoginActivity.this, MenuActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//            startActivity(new Intent(LoginActivity.this, ProfilActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

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

        gson = new Gson();

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
                validasiLogin();


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
            login();
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
        final AlertDialog waitingDialog = new SpotsDialog(LoginActivity.this,"Harap Sabar");
        waitingDialog.show();

        final SharedPref sharedPref;
        sharedPref = new SharedPref(this);

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", etEmail.getText().toString());
            jsonObject.put("password", etPass.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("http://meetap.tech/api/auth/login/")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String message = response.getString("message");
                            String status = response.getString("status");

                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            if (status.equals("success")) {
                                //data user
                                String User = response.getString("data");

                                JSONObject jsonObjectUser = new JSONObject(User);
                                //get id_user
                                //String idUser = jsonObjectUser.getString("id");

                                //get fullname
                                String status1 = jsonObjectUser.getString("status");
                                //check new user or member
                                if (status1.equals("belum_update")){
                                    //New User
                                    sharedPref.saveSPBoolean(SharedPref.SP_SUDAH_LOGIN, true);

                                    pref = getSharedPreferences("id", MODE_PRIVATE);
                                    idUser = jsonObjectUser.getString("id");
                                    SharedPreferences.Editor editorId = pref.edit();
                                    editorId.putString("IdUser", idUser);
                                    editorId.commit();
                                    Toast.makeText(getApplicationContext(), idUser, Toast.LENGTH_SHORT).show();

                                    pref = getSharedPreferences("email", MODE_PRIVATE);
                                    String tvemail = etEmail.getText().toString();
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("etemail", tvemail);
                                    editor.commit();

                                    pref = getSharedPreferences("password", MODE_PRIVATE);
                                    String tvpassword = etPass.getText().toString();
                                    SharedPreferences.Editor editor1 = pref.edit();
                                    editor1.putString("etpassword", tvpassword);
                                    editor1.commit();

                                    waitingDialog.dismiss();
                                    Intent go = new Intent(LoginActivity.this, PasswordTrueActivity.class);
                                    startActivity(go);
                                    finish();
                                }else {
                                    //Member
                                    sharedPref.saveSPBoolean(SharedPref.SP_SUDAH_LOGIN, true);

                                    pref = getSharedPreferences("id", MODE_PRIVATE);
                                    String idUser = jsonObjectUser.getString("id");
                                    SharedPreferences.Editor editorId = pref.edit();
                                    editorId.putString("IdUser", idUser);
                                    editorId.commit();

                                    //Push Fragment
                                    Bundle id = new Bundle();
                                    id.putString("key", idUser);
                                    MemberFragment fragment = new MemberFragment();
                                    fragment.setArguments(id);

                                    Toast.makeText(getApplicationContext(), id.toString(), Toast.LENGTH_SHORT).show();

                                    pref = getSharedPreferences("email", MODE_PRIVATE);
                                    String tvemail = etEmail.getText().toString();
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("etemail", tvemail);
                                    editor.commit();

                                    pref = getSharedPreferences("password", MODE_PRIVATE);
                                    String tvpassword = etPass.getText().toString();
                                    SharedPreferences.Editor editor1 = pref.edit();
                                    editor1.putString("etpassword", tvpassword);
                                    editor1.commit();

                                    Log.e(TAG,"EMAIL : "+etEmail);
                                    Log.e(TAG,"PASSWORD : "+etPass);
                                    Log.e(TAG,"EMAIL1 : "+tvemail);
                                    Log.e(TAG,"PASSWORD1 : "+tvpassword);

                                    auth.signInWithEmailAndPassword(tvemail, tvpassword)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()){
                                                        waitingDialog.dismiss();
                                                        Intent go = new Intent(LoginActivity.this, MenuActivity.class);
                                                        go.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(go);
                                                        finish();
                                                    }else {
                                                        waitingDialog.dismiss();
                                                        Toast.makeText(LoginActivity.this,"Authentification Failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }

                                }
                            else{
                                showPopUp();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "JSONExceptions" + e, Toast.LENGTH_LONG).show();


                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        waitingDialog.dismiss();
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
