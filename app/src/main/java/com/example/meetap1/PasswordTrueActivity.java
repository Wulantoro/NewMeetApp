package com.example.meetap1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PasswordTrueActivity extends AppCompatActivity {

    private Button btnMelanjutkan;
    private TextView tvemail;
    private TextView tvpassword;
    public SharedPreferences pref, prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_true);

        btnMelanjutkan = findViewById(R.id.btnMelanjutkan);

        tvemail = findViewById(R.id.tvemail);
        tvpassword = findViewById(R.id.tvpassword);

        TextView tvemail2 = findViewById(R.id.tvemail);
        prf = getSharedPreferences("email", MODE_PRIVATE);
        tvemail2.setText(prf.getString("etemail", null));

        TextView tvpass = findViewById(R.id.tvpassword);
        prf = getSharedPreferences("password", MODE_PRIVATE);
        tvpass.setText(prf.getString("etpassword", null));



        btnMelanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(PasswordTrueActivity.this, ChangePassActivity.class);

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
                startActivity(go);
            }
        });
    }
}
