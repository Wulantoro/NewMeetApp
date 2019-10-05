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

public class RegisterActivity extends AppCompatActivity {
    Dialog dialogEmail;
    private EditText etEmail;
    private TextView tvMasuk;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dialogEmail = new Dialog(RegisterActivity.this);

        btnRegister = findViewById(R.id.btnRegister);
        tvMasuk = findViewById(R.id.tvMasuk);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                //etEmail.requestFocus();

            }
        });

        tvTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(RegisterActivity.this, CheckEmailActivity.class);
                startActivity(go);
            }
        });

        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEmail.show();
    }
}
