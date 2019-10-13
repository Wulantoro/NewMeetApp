package com.example.meetap1;

import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    Dialog dialogEmail;
    private EditText etEmailReg;
    private TextView tvMasuk;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dialogEmail = new Dialog(RegisterActivity.this);

        btnRegister = findViewById(R.id.btnRegister);
        etEmailReg = findViewById(R.id.etEmailReg);
        tvMasuk = findViewById(R.id.tvMasuk);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RegisterEmail();
                showPopUp();
            }
        });

        tvMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(go);
            }
        });
    }

    private void showPopUp() {
        TextView tvTrue, tvFalse;

        dialogEmail.setContentView(R.layout.popupregis);
        dialogEmail.setCancelable(false);

        tvTrue = dialogEmail.findViewById(R.id.tvBenar);
        tvFalse = dialogEmail.findViewById(R.id.tvSalah);

        tvFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEmail.dismiss();
//                etEmail.requestFocus();

            }
        });

        tvTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(RegisterActivity.this, CheckEmailActivity.class);
                startActivity(go);
                RegisterEmail();
            }
        });

        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEmail.show();
    }

    public void RegisterEmail() {

        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray newArr = new JSONArray();
            jsonObject.put("email", etEmailReg.getText().toString());

            newArr.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("http://ask.meetap.id/api/auth/register")
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
                        Toast.makeText(getApplicationContext(), "Gagal Registrasi", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}
