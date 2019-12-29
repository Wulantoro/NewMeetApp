package com.example.meetap1;

import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.meetap1.Model.User;
import com.example.meetap1.Model.UserId;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChangePassActivity extends AppCompatActivity {

    Dialog popPass;
    private ImageView imgEye1, imgEye2;
    private EditText NewPass, ConPass;
    private TextView tvEmail;
    private Button btnNewPass;

    public SharedPreferences pref, prf;
    private Gson gson;
    private List<User> allList;

//    FirebaseAuth auth;
//    DatabaseReference reference;

    String idUser, tvemail1, pass;
    boolean isPlay = false;
    private static String TAG = ChangePassActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        popPass = new Dialog(ChangePassActivity.this);

//        auth = FirebaseAuth.getInstance();

        gson = new Gson();
        allList = new ArrayList<>();

        imgEye1 = findViewById(R.id.imgEye1);
        imgEye2 = findViewById(R.id.imgEye2);

        NewPass = findViewById(R.id.etNewPassword);
        ConPass = findViewById(R.id.etConPassword);

        btnNewPass = findViewById(R.id.btnNewPass);

        tvEmail = findViewById(R.id.tvEmail);
        prf = getSharedPreferences("email", MODE_PRIVATE);
        tvEmail.setText(prf.getString("etemail1", null));

        imgEye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay){
                    v.setBackgroundResource(R.drawable.ic_hide);
                    NewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }else {
                    v.setBackgroundResource(R.drawable.ic_show);
                    NewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                isPlay = !isPlay;
            }
        });

        imgEye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay){
                    v.setBackgroundResource(R.drawable.ic_hide);
                    ConPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }else {
                    v.setBackgroundResource(R.drawable.ic_show);
                    ConPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                isPlay = !isPlay;
            }
        });

        //SharedPreference
        pref = getSharedPreferences("id", MODE_PRIVATE);
        idUser = pref.getString("IdUser1", null);
        SharedPreferences.Editor editorId = pref.edit();
        editorId.putString("IdUser2", idUser);
        editorId.commit();

        pref = getSharedPreferences("email", MODE_PRIVATE);
        tvemail1 = tvEmail.getText().toString();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email", tvemail1);
        editor.commit();


        btnNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref = getSharedPreferences("password", MODE_PRIVATE);
                pass = NewPass.getText().toString();
                SharedPreferences.Editor editor1 = pref.edit();
                editor1.putString("password", pass);
                editor1.commit();

                Log.e(TAG,"pASSWORD : "+NewPass.getText().toString());

                if (NewPass.getText().toString().length()== 0 || NewPass.getText().toString().length()<6){
                   NewPass.setError("Password kurang dari 6 digit");
                   NewPass.requestFocus();

                }else if (!ConPass.getText().toString().equals(NewPass.getText().toString())){
                    ConPass.setError("Cek Password anda");
                    ConPass.requestFocus();
                }else {
//                    regFirebase(tvEmail.getText().toString(), NewPass.getText().toString());
//                    Log.e(TAG,"Password : " +NewPass);
                    Fan();

                }

            }
        });


    }

    private void Fan(){

        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray newArr = new JSONArray();
            jsonObject.put("id", idUser);
            jsonObject.put("passwordBaru", NewPass.getText().toString());
            jsonObject.put("passwordKonf", ConPass.getText().toString());

            newArr.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("http://ask.meetap.id/api/profile/updatePassword?id/")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");
                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            if (status.equals("success")){

                                Intent go = new Intent(ChangePassActivity.this, ProfilActivity.class);
                                go.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(go);
                                finish();
                                Toast.makeText(getApplicationContext(), idUser, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONExceptions"+ e, Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    //NewPass.getText().toString().trim()

//    private void regFirebase(final String email, final String password){
//        auth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            FirebaseUser firebaseUser = auth.getCurrentUser();
//                            String userIdFirebase = firebaseUser.getUid();
//
//                            Log.e(TAG, "Email : " +tvemail1);
//                            Log.e(TAG, "Pass : " +pass);
//
//                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userIdFirebase);
//
//                            HashMap<String, String> hashMap = new HashMap<>();
//                            hashMap.put("id", userIdFirebase);
//                            hashMap.put("username",email);
//                            hashMap.put("email",email);
//                            hashMap.put("password",password);
//                            hashMap.put("Status","Member");
//                            hashMap.put("imageURL", "default");
//
//                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()){
//                                       Fan();
//                                    }else {
//                                        Toast.makeText(ChangePassActivity.this,"Authentification Failed", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }
//                    }
//                });
//    }


    private void showPopBerhasil() {

        Button Btnok;
        popPass.setContentView(R.layout.popupregis);

        Btnok = popPass.findViewById(R.id.btnOk);
        Btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ChangePassActivity.this, ProfilActivity.class);
                startActivity(go);
            }
        });

        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popPass.show();
    }

}
