package com.example.meetap1;

import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassActivity extends AppCompatActivity {

    Dialog popPass;
    private ImageView imgEye1, imgEye2;
    private EditText NewPass, ConPass;
    private TextView tvEmail;
    private Button btnNewPass;

    boolean isPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        popPass = new Dialog(ChangePassActivity.this);

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


        btnNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ChangePassActivity.this, ProfilActivity.class);
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


}
