package com.example.meetap1;

import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.anggastudio.spinnerpickerdialog.SpinnerPickerDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProfilActivity extends AppCompatActivity {

    private Button btnSimpan;
    Dialog popUpProfil;
    private Spinner spKode, spGender;
    private EditText etNoTelp;
    private TextInputLayout tiBirthday;
    private TextInputEditText etBirthday;

    private static final int PICK_IMAGE_FILE = 1;
    private ImageView imgUser, imgAdd, imgTgl;
    private Uri photoUri;

    private String[] Gender = {
            "Select Gender",
            "Laki-laki",
            "Perempuan"
    };

    DateFormat simpleDate;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        popUpProfil = new Dialog(ProfilActivity.this);

        imgAdd = findViewById(R.id.imgAdd);
        imgUser = findViewById(R.id.imgUser);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });

        btnSimpan = findViewById(R.id.btnSmpProf);
        spKode = findViewById(R.id.spCode);
        spGender = findViewById(R.id.spJK);
        etNoTelp = findViewById(R.id.etNoTelp);
        etBirthday = findViewById(R.id.etTgl);
        imgTgl = findViewById(R.id.imgTgl);

        spGender.setSelection(0);
        spKode.setSelection(62);

        date = Calendar.getInstance().getTime();
        simpleDate = new SimpleDateFormat("ddMMyyyy");

        //Function Tanggal
        imgTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateSpinner();
            }
        });


        spGender.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, Gender));
        spKode.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopUpProfil();
                Intent go = new Intent(ProfilActivity.this, MenuActivity.class);
                startActivity(go);
            }
        });
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
                String date = day + "-" + month + "-" + year;
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
}

