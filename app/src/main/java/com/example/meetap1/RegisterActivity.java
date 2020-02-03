package com.example.meetap1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import androidx.appcompat.widget.ButtonBarLayout;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.internal.LockOnGetVariable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {
    Dialog dialogEmail;
    private EditText etEmail;
    private TextView tvMasuk;
    private Button btnRegister;
    public SharedPreferences pref, prf;

    private String message;

    private static String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dialogEmail = new Dialog(RegisterActivity.this);

        btnRegister = findViewById(R.id.btnRegister);
        etEmail = findViewById(R.id.etEmailRegister);
        tvMasuk = findViewById(R.id.tvMasuk);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().length()==0){
                    etEmail.setError("Email Kosong");
                }else {
                    showPopUp(etEmail.getText().toString());
                }
            }
        });

        tvMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(go);
            }
        });

        etEmail.setError(null);
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                etEmail.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()==0){
                    etEmail.setError("Email Kosong");
                }else{
                    etEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void showPopUp(String email) {
        TextView tvTrue, tvFalse, tvEmail;

        dialogEmail.setContentView(R.layout.popupregis);
        dialogEmail.setCancelable(false);

        tvTrue = dialogEmail.findViewById(R.id.tvBenar);
        tvFalse = dialogEmail.findViewById(R.id.tvSalah);
        tvEmail = dialogEmail.findViewById(R.id.tvEmailUser);

        tvEmail.setText(email);

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
                RegisterEmail(etEmail.getText().toString());
            }
        });

        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEmail.show();
    }

    public void RegisterEmail(String email) {
        final AlertDialog waitingDialog = new SpotsDialog(RegisterActivity.this,"Harap Sabar");
        waitingDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray newArr = new JSONArray();
            jsonObject.put("email",email);

            newArr.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("http://meetap.tech/api/auth/register")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            message = response.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            Intent go = new Intent(RegisterActivity.this, CheckEmailActivity.class);

//                            pref = getSharedPreferences("password", MODE_PRIVATE);
//                            String password = pref.getString("password", message);
//                            SharedPreferences.Editor editorId = pref.edit();
//                            editorId.putString("pasword1", password);
//                            editorId.commit();
                            go.putExtra("password",message);

                            //Log.e(TAG,"PASSWORD 1 : " +password);
                            startActivity(go);
                            finish();
                            Log.e(TAG,"PASSWORD : " +message);

                            dialogEmail.dismiss();
                            waitingDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            waitingDialog.dismiss();
                            dialogEmail.dismiss();
                            Toast.makeText(getApplicationContext(), "JSONExceptions"+ e, Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        waitingDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "aneeRORO : "+anError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (dialogEmail != null && dialogEmail.isShowing()){
//            dialogEmail.dismiss();
//        }
//    }
}
