package com.example.meetap1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PasswordTrueActivity extends AppCompatActivity {

    private Button btnMelanjutkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_true);

        btnMelanjutkan = findViewById(R.id.btnMelanjutkan);

        btnMelanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(PasswordTrueActivity.this, ChangePassActivity.class);
                startActivity(go);
            }
        });
    }
}
