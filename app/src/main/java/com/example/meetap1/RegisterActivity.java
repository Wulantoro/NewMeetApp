package com.example.meetap1;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    Dialog dialogEmail;
    private EditText etEmail;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dialogEmail = new Dialog(RegisterActivity.this);

        btnRegister = findViewById(R.id.btnREGISTER);
        etEmail = findViewById(R.id.etEmailKonfirm);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });
    }

    private void showPopUp() {
        TextView tvTrue, tvFalse;

        dialogEmail.setContentView(R.layout.popupregis);

        tvTrue = dialogEmail.findViewById(R.id.tvTrue);
        tvFalse = dialogEmail.findViewById(R.id.tvFalse);

        tvFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEmail.dismiss();
                etEmail.requestFocus();

            }
        });

        tvTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(RegisterActivity.this, PassRandomActivity.class);
                startActivity(go);
            }
        });

        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEmail.show();
    }
}
