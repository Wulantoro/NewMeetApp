package com.example.meetap1;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PassRandomActivity extends AppCompatActivity {

    private Button btnKirim;
    Dialog suksesDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_random);

        suksesDialog = new Dialog(PassRandomActivity.this);

        btnKirim = findViewById(R.id.btnKirim);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpCheck();
            }
        });

    }

    private void showPopUpCheck() {
        Button Btnok;
        suksesDialog.setContentView(R.layout.popupcheck);

        Btnok = suksesDialog.findViewById(R.id.btnOk);
        Btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(PassRandomActivity.this, ChangePassActivity.class);
                startActivity(go);
            }
        });

        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        suksesDialog.show();
    }
}
