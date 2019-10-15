package com.example.meetap1;

import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.meetap1.Model.UserId;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChangePassActivity extends AppCompatActivity {

    Dialog popPass;
    private ImageView imgEye1, imgEye2;
    private EditText NewPass, ConPass;
    private TextView tvEmail;
    private Button btnNewPass;

    private TextView tvemail;
    private TextView tvpassword;
    public SharedPreferences pref, prf;
    private Gson gson;
    private List<UserId> allList;

    boolean isPlay = false;

    private static String TAG = BerandaFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        popPass = new Dialog(ChangePassActivity.this);

        gson = new Gson();
        allList = new ArrayList<>();
        tvemail = findViewById(R.id.tvemail);
        tvpassword = findViewById(R.id.tvpassword);

        TextView tvemail2 = findViewById(R.id.tvemail);
        prf = getSharedPreferences("email", MODE_PRIVATE);
        tvemail2.setText(prf.getString("etemail", null));

        TextView tvpass = findViewById(R.id.tvpassword);
        prf = getSharedPreferences("password", MODE_PRIVATE);
        tvpass.setText(prf.getString("etpassword", null));

        imgEye1 = findViewById(R.id.imgEye1);
        imgEye2 = findViewById(R.id.imgEye2);

        NewPass = findViewById(R.id.etNewPassword);
        ConPass = findViewById(R.id.etConPassword);

        btnNewPass = findViewById(R.id.btnNewPass);

        tvEmail = findViewById(R.id.tvEmail);

        imgEye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay){
                    v.setBackgroundResource(R.drawable.ic_hide);
                    NewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }else {
                    v.setBackgroundResource(R.drawable.ic_show);
                    NewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                isPlay = !isPlay;
            }
        });

        imgEye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay){
                    v.setBackgroundResource(R.drawable.ic_hide);
                    ConPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }else {
                    v.setBackgroundResource(R.drawable.ic_show);
                    ConPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                isPlay = !isPlay;
            }
        });


        getIdUser();

        btnNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ChangePassActivity.this, ProfilActivity.class);

                pref = getSharedPreferences("email", MODE_PRIVATE);
                String tvemail1 = tvemail.getText().toString();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("etemail", tvemail1);
                editor.commit();

                pref = getSharedPreferences("password", MODE_PRIVATE);
                String tvpassword1 = tvpassword.getText().toString();
                SharedPreferences.Editor editor1 = pref.edit();
                editor1.putString("etpassword", tvpassword1);
                editor1.commit();

                ChangePass();
                startActivity(go);
            }
        });


    }

    private void showPopBerhasil() {

        Button Btnok;
        popPass.setContentView(R.layout.popupregis);

        Btnok = popPass.findViewById(R.id.btnOk);
        Btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ChangePassActivity.this, ProfilActivity.class);
                startActivity(go);
            }
        });

        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popPass.show();
    }

    public void ChangePass() {
        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray newArr = new JSONArray();
            jsonObject.put("passwordBaru", NewPass.getText().toString());
            jsonObject.put("passwordKonf", ConPass.getText().toString());

            newArr.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("http://ask.meetap.id/api/profile/updatePassword")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONExceptions"+ e, Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Gagal ubah password", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void getIdUser() {
        AndroidNetworking.post("http://ask.meetap.id/api/auth/login/")
                .addBodyParameter("email", tvemail.getText().toString())
                .addBodyParameter("password", tvpassword.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<UserId> result = new ArrayList<>();

                        try {
                            if (result != null)
                                result.clear();

                            Log.e(TAG, "tampil user" + response.toString(1));

                            String message = response.getString("message");
                            String status = response.getString("status");

                            if (message.equals("Login success")) {
                                String records = response.getString("data");

                                Log.e(TAG,"idddddd"+ result.toString());
                                JSONArray dataArr = new JSONArray(records);

                                if (dataArr.length() > 0) {
                                    for (int i = 0; i < dataArr.length(); i++) {
                                        UserId userId = gson.fromJson(dataArr.getJSONObject(i).toString(), UserId.class);
                                        result.add(userId);
                                        Log.e(TAG, "id udser " + userId.getId());
                                    }

                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


}
