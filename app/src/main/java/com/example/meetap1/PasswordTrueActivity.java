package com.example.meetap1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordTrueActivity extends AppCompatActivity {

    private Button btnMelanjutkan;
    private TextView tvEmail;
    private TextView tvpass;
    public SharedPreferences pref, prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_true);

        btnMelanjutkan = findViewById(R.id.btnMelanjutkan);
        btnMelanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get ID
                pref = getSharedPreferences("id", MODE_PRIVATE);
                String idUser = pref.getString("IdUser", null);
                SharedPreferences.Editor editorId = pref.edit();
                editorId.putString("IdUser1", idUser);
                editorId.commit();
                Toast.makeText(getApplicationContext(), idUser, Toast.LENGTH_SHORT).show();

                //get Email
                pref = getSharedPreferences("email", MODE_PRIVATE);
                //String tvemail1 = tvEmail.getText().toString();
                String tvemail = pref.getString("etemail",null);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("etemail1", tvemail);
                editor.commit();

                //get Password
                pref = getSharedPreferences("password", MODE_PRIVATE);
                //String tvpassword1 = tvpass.getText().toString();
                String tvpassword1 = pref.getString("etpassword", null);
                SharedPreferences.Editor editor1 = pref.edit();
                editor1.putString("etpassword", tvpassword1);
                editor1.commit();

                Intent go = new Intent(PasswordTrueActivity.this, ChangePassActivity.class);
                startActivity(go);
            }
        });
    }
}
