package com.example.meetap1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckEmailActivity extends AppCompatActivity {
    private Button btnMelanjutkanReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);

        btnMelanjutkanReg = findViewById(R.id.btnMelanjutkanReg);

        btnMelanjutkanReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(CheckEmailActivity.this, LoginActivity.class);
                startActivity(go);
            }
        });
    }
}
