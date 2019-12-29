package com.example.meetap1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CheckEmailActivity extends AppCompatActivity {
    private Button btnMelanjutkanReg;
    private EditText etPassword1;
    private ImageButton imgCopy;

    public SharedPreferences pref, prf;
    private static String TAG = CheckEmailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);

        btnMelanjutkanReg = findViewById(R.id.btnMelanjutkanReg);
        etPassword1 = findViewById(R.id.etPassword1);
        imgCopy = findViewById(R.id.imgCopy);

        pref = getSharedPreferences("password", MODE_PRIVATE);
        String password1 = pref.getString("pasword1", null);
       // Log.e(TAG,"Passworde : " +password1);

        String password = getIntent().getStringExtra("password");
        Log.e(TAG,"Passworde : " +password);
        etPassword1.setText(password);

        imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("passwode", etPassword1.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(CheckEmailActivity.this, "Salin",Toast.LENGTH_SHORT).show();
            }
        });

        btnMelanjutkanReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(CheckEmailActivity.this, LoginActivity.class);
                startActivity(go);
            }
        });
    }
}
