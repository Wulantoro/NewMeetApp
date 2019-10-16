package com.example.meetap1;

import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.anggastudio.spinnerpickerdialog.SpinnerPickerDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.meetap1.Model.UserId;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfilActivity extends AppCompatActivity {

    private Button btnSimpan;
    Dialog popUpProfil;
    private Spinner spKode, spGender, spProvince;
    private EditText etNoTelp, etBirthday;
    private TextInputLayout tiBirthday;

    private static final int PICK_IMAGE_FILE = 1;
    private ImageView imgUser, imgAdd, imgTgl;
    private Uri photoUri;
    private TextView tiFullname1;
    private Spinner spJK;

    private CountryCodePicker ccp;

    private String[] Gender = {
            "Select Gender",
            "Laki-laki",
            "Perempuan"
    };

    DateFormat simpleDate;
    Date date;

    private TextView tvemail;
    private TextView tvpassword;
    private TextView tviduser1;
    public SharedPreferences pref, prf;
    private Gson gson;
    private static String TAG = ProfilActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);



        gson = new Gson();
        tvemail = findViewById(R.id.tvemail);
        tvpassword = findViewById(R.id.tvpassword);
        tviduser1 = findViewById(R.id.tviduser1);

        TextView tviduser2 = findViewById(R.id.tviduser1);
        tviduser2.setText("10");

        TextView tvemail2 = findViewById(R.id.tvemail);
        prf = getSharedPreferences("email", MODE_PRIVATE);
        tvemail2.setText(prf.getString("etemail", null));

        TextView tvpass = findViewById(R.id.tvpassword);
        prf = getSharedPreferences("password", MODE_PRIVATE);
        tvpass.setText(prf.getString("etpassword", null));

        popUpProfil = new Dialog(ProfilActivity.this);
        ccp = findViewById(R.id.ccp);
        imgAdd = findViewById(R.id.imgAdd);
        imgUser = findViewById(R.id.imgUser);

        tiFullname1 = findViewById(R.id.tiFullname1);
        etNoTelp = findViewById(R.id.etNoTelp);
        spJK = findViewById(R.id.spJK);
        spProvince = findViewById(R.id.spProvince);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });

        btnSimpan = findViewById(R.id.btnSmpProf);
//        spKode = findViewById(R.id.spCode);
        spGender = findViewById(R.id.spJK);
        etNoTelp = findViewById(R.id.etNoTelp);
        etBirthday = findViewById(R.id.etTglBirthday);
        imgTgl = findViewById(R.id.imgTgl);

        spGender.setSelection(0);
//        spKode.setSelection(62);

        date = Calendar.getInstance().getTime();
        simpleDate = new SimpleDateFormat("ddMMyyyy");
//        ccp.get
        //Function Tanggal
        imgTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateSpinner();
            }
        });


        spGender.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, Gender));
//        spKode.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopUpProfil();
                updateprofil();
                Intent go = new Intent(ProfilActivity.this, MenuActivity.class);
                startActivity(go);
            }
        });

//        getIdUser(tvemail.getText().toString(), tvpassword.getText().toString());
        getIdUser();
    }

    private void DateSpinner() {
        final SpinnerPickerDialog spinnerPickerDialog = new SpinnerPickerDialog();
        spinnerPickerDialog.setContext(this);
        spinnerPickerDialog.setAllColor(ContextCompat.getColor(this, R.color.greenMeetAp));
        spinnerPickerDialog.setmTextColor(Color.BLACK);
        spinnerPickerDialog.setArrowButton(true);
        spinnerPickerDialog.setOnDialogListener(new SpinnerPickerDialog.OnDialogListener() {
            @Override
            public void onSetDate(int month, int day, int year) {
                month = month + 1;
//                String date = day + "-" + month + "-" + year;
                String date = year  + "-" + month + "-" + day;
                DateFormat dp_medium = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                String dp_medium_uk_strg = dp_medium.format(Calendar.getInstance().getTime());
                etBirthday.setText(date);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onDismiss() {

            }
        });

        spinnerPickerDialog.show(this.getSupportFragmentManager(), "");
    }

    private void openFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FILE && resultCode == RESULT_OK && data != null && data.getData() != null){
            photoUri = data.getData();
            Glide.with(this)
                    .load(photoUri)
                    .skipMemoryCache(true)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgUser);
        }
    }

    private void showPopUpProfil() {

        Button Btnok;
        popUpProfil.setContentView(R.layout.popupregis);

        Btnok = popUpProfil.findViewById(R.id.btnOk);
        Btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ProfilActivity.this, MenuActivity.class);
                startActivity(go);
            }
        });

        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpProfil.show();
    }

//    public void getIdUser(String email, String pass) {

    public void getIdUser() {

//        final JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("email", tvemail.getText().toString());
//            jsonObject.put("password", tvpassword.getText().toString());
//
//            Log.e(TAG, "email " + tvemail.getText().toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }



//     AndroidNetworking.post("http://ask.meetap.id/api/auth/login/?email=" + email + "&passs=" + pass)
        AndroidNetworking.post("http://ask.meetap.id/api/auth/login/")
            .addBodyParameter("email", tvemail.getText().toString())
                .addBodyParameter("password", tvpassword.getText().toString())
                .setPriority(Priority.MEDIUM)
             .build()
             .getAsJSONObject(new JSONObjectRequestListener() {
                 @Override
                 public void onResponse(JSONObject response) {
                     List<UserId> result = new ArrayList<>();

                     try {

                         if (result != null)
                             result.clear();

                         Log.e(TAG, "tampil user" + response.toString(1));
                         String message = response.getString("message");

                         if (message.equals("Login success")) {
                             String records = response.getString("data");

                             JSONArray dataArr = new JSONArray(records);

                             if (dataArr.length() > 0) {
                                 for (int i = 0; i < dataArr.length(); i++) {
                                     UserId userId = gson.fromJson(dataArr.getJSONObject(i).toString(), UserId.class);
                                     result.add(userId);
                                     tviduser1.setText(String.valueOf(userId.getId()));
                                     Log.e(TAG, "id" + userId.getId() );
                                 }
                             }
                         }
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }

                 @Override
                 public void onError(ANError anError) {

                 }
             });

    }

    public void updateprofil() {
        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray newArr = new JSONArray();
            jsonObject.put("id", tviduser1.getText().toString());
            jsonObject.put("fullname", tiFullname1.getText().toString());
            jsonObject.put("handphone_number", etNoTelp.getText().toString());
            jsonObject.put("spJK", spJK.getSelectedItemPosition());
            jsonObject.put("birthdate", etBirthday.getText().toString());

            newArr.put(jsonObject);
            Log.e("coba input p = ", newArr.toString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post("http://ask.meetap.id/api/profile/updateProfile?id")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Gagal menambah data", Toast.LENGTH_SHORT).show();

                    }
                });


    }
}

