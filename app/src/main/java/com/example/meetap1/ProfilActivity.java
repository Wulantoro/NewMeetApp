package com.example.meetap1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.anggastudio.spinnerpickerdialog.SpinnerPickerDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.meetap1.Adapter.CountryAdapter;
import com.example.meetap1.ImagePicker.RxImageConverter;
import com.example.meetap1.ImagePicker.RxImagePicker;
import com.example.meetap1.ImagePicker.Sources;
import com.example.meetap1.Model.City;
import com.example.meetap1.Model.Country;
import com.example.meetap1.Model.Province;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import id.zelory.compressor.Compressor;
import io.reactivex.Observable;


import static com.example.meetap1.Utils.GlobalHelper.convertFileToContentUri;
import static com.example.meetap1.Utils.GlobalHelper.deleteRecursive;
import static com.example.meetap1.Utils.GlobalHelper.encodeFileBase64;
import static com.example.meetap1.Utils.GlobalHelper.getMimeTypeFromUri;
import static com.example.meetap1.Utils.GlobalHelper.getPath;
import static com.example.meetap1.Utils.GlobalVars.BASE_DIR;
import static com.example.meetap1.Utils.GlobalVars.EXTERNAL_DIR_FILES;
import static com.example.meetap1.Utils.GlobalVars.IMAGES_DIR;
import static java.lang.String.valueOf;

public class ProfilActivity extends AppCompatActivity {

    private Button btnSimpan;
    Dialog popUpProfil;
    private Spinner spKode, spGender, spProvince, spCountry;
    private EditText etNoTelp, etBirthday;
    private TextInputLayout tiBirthday;

    private static final int PICK_IMAGE_FILE = 1;
    private ImageView imgAdd, imgTgl, ivCamera, imgUser;
    private Uri photoUri;
    private TextView tiFullname1;
    private Spinner spJK;

    private RadioGroup converterRadioGroup;

    private CountryCodePicker ccp;
    Spinner spinner_country, spKota;
    //Country
    CountryAdapter countryAdapter;
    List<Country> list_country;
    //Photo
    private String id;
    private File tempFile= null;
    private File compressedImage = null;
    private Uri finalPhotoUri = null;
    private String photoExt = "";
    private String encodePhoto = "";
    private Bitmap theBitmap = null;
    private String prv, con;
    //Firebase
    FirebaseAuth auth;
    DatabaseReference reference;

    //Progress
    private AlertDialog waitingDialog;

    private String[] Gender = {
            "Select Gender",
            "Laki-laki",
            "Perempuan"
    };

    DateFormat simpleDate;
    Date date;

    public SharedPreferences pref;
    private Gson gson;
    private static String TAG = ProfilActivity.class.getSimpleName();
    String idUser, emailUser, newPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //Firebase
        auth = FirebaseAuth.getInstance();
        list_country = new ArrayList<Country>();
        gson = new Gson();

        popUpProfil = new Dialog(ProfilActivity.this);
        ccp = findViewById(R.id.ccp);
        imgAdd = findViewById(R.id.imgAdd);
        imgUser = findViewById(R.id.imgUser);

        tiFullname1 = findViewById(R.id.tiFullname1);
        etNoTelp = findViewById(R.id.etNoTelp);
        spJK = findViewById(R.id.spJK);
        spProvince = findViewById(R.id.spProvince);
        spKota = findViewById(R.id.spKota);

        btnSimpan = findViewById(R.id.btnSmpProf);
        spGender = findViewById(R.id.spJK);
        etNoTelp = findViewById(R.id.etNoTelp);
        etBirthday = findViewById(R.id.etTglBirthday);
        imgTgl = findViewById(R.id.imgTgl);
        ivCamera = findViewById(R.id.ivCamera);

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromSource(Sources.CAMERA);
            }
        });

        date = Calendar.getInstance().getTime();
        simpleDate = new SimpleDateFormat("ddMMyyyy");
        spinner_country = (Spinner) findViewById(R.id.spinner_country);

//        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        //Country Adapter
//        countryAdapter = new CountryAdapter(ProfilActivity.this, list_country);
//        spinner_country.setAdapter(countryAdapter);
//        //ListCountry Api
        listCountry();
        //ListProvince Api
        listProvince();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });

        //Function Tanggal
        imgTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateSpinner();
            }
        });

        spGender.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Gender));
//        spinner_country.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        //        spKode.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        //open camera
        converterRadioGroup = findViewById(R.id.radio_group);
        converterRadioGroup.check(R.id.radio_file);

        if (RxImagePicker.with(ProfilActivity.this).getActiveSubscription() != null) {
            RxImagePicker.with(ProfilActivity.this).getActiveSubscription().subscribe(this::onImagePicked);
        }
        id = valueOf(System.currentTimeMillis());

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopUpProfil();
                updateprofil();
                Log.e(TAG,"ID_User : " +idUser);
                Log.e(TAG,"Email : " +emailUser);
                Log.e(TAG,"Password : " +newPass);

            }
        });


        //SharedPreference
        pref = getSharedPreferences("id", MODE_PRIVATE);
        idUser = pref.getString("IdUser2", null);
        SharedPreferences.Editor editorId = pref.edit();
        editorId.putString("IdUser3", idUser);
        editorId.commit();


        pref = getSharedPreferences("email", MODE_PRIVATE);
        emailUser = pref.getString("email", null);
        SharedPreferences.Editor editorEmail = pref.edit();
        editorEmail.putString("etemail", emailUser);
        editorEmail.commit();

        pref = getSharedPreferences("password", MODE_PRIVATE);
        newPass = pref.getString("password", null);
        SharedPreferences.Editor editorPass = pref.edit();
        editorPass.putString("newPass", emailUser);
        editorPass.commit();


    }

    private void pickImageFromSource(Sources source) {
        RxImagePicker.with(ProfilActivity.this).requestImage(source)
                .flatMap(uri -> {
                    switch (converterRadioGroup.getCheckedRadioButtonId()) {
                        case R.id.radio_file:
                            return RxImageConverter.uriToFile(ProfilActivity.this, uri, createTempFile());
                        case R.id.radio_bitmap:
                            return RxImageConverter.uriToBitmap(ProfilActivity.this, uri);
                        default:
                            return Observable.just(uri);
                    }
                })
                .subscribe(this::onImagePicked, throwable -> Toast.makeText(ProfilActivity.this, String.format("Error: %s", throwable), Toast.LENGTH_LONG).show());
    }


    private void onImagePicked(Object result) {
        if (result instanceof Bitmap) {
            //ivImage.setImageBitmap((Bitmap) result);
        }else{
            photoUri = Uri.parse(valueOf(result));

            tempFile = new File(valueOf(photoUri));

            compressedImage = compressFoto(ProfilActivity.this, tempFile);


            try {
                finalPhotoUri = convertFileToContentUri(ProfilActivity.this, compressedImage);

            } catch (Exception e) {
                e.printStackTrace();
            }

            photoExt = "."+getMimeTypeFromUri(ProfilActivity.this, finalPhotoUri);
            encodePhoto = encodeFileBase64(getPath(ProfilActivity.this, finalPhotoUri));

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }

                    try {
                        theBitmap = Glide.
                                with(ProfilActivity.this).
                                asBitmap().
                                load(getPath(ProfilActivity.this, finalPhotoUri))
                                .apply(RequestOptions.circleCropTransform()).
                                        into(100, 100).get();

                    } catch (final ExecutionException e) {
                        Log.e("TAG","ExecutionException " + e.getMessage());
                    } catch (final InterruptedException e) {
                        Log.e("TAG","InterruptedException " + e.getMessage());
                    }
                    return null;
                }
                @SuppressLint("WrongThread")
                @Override
                protected void onPostExecute(Void dummy) {
                    if (null != theBitmap) {
                        // The full bitmap should be available here
                        //ivAvatar.setImageBitmap(theBitmap);

                        File mypath=new File(IMAGES_DIR,id+".jpeg");

                        ContextWrapper cw = new ContextWrapper(ProfilActivity.this);
                        // path to /data/data/yourapp/app_data/imageDir
                        // Create imageDir
                        //File mypath=new File(fotoPath,userId+".jpeg");

                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(mypath);
                            // Use the compress method on the BitMap object to write image to the OutputStream
                            theBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        Glide.with(getApplicationContext())
                                .load(mypath)
                                .apply(RequestOptions.circleCropTransform())
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .placeholder(R.drawable.ic_person)
                                .into(imgUser);

                        Log.d("TAG", "Image loaded");
                    };
                }
            }.execute();

            deleteRecursive(new File(valueOf(finalPhotoUri)));
            deleteRecursive(createTempFile());
            deleteRecursive(tempFile);

        }
    }

    private File createTempFile() {
        return new File(BASE_DIR + EXTERNAL_DIR_FILES, id + ".jpeg");
    }

    public static File compressFoto(Context context, File actualImage) {
        final String path = IMAGES_DIR;

        File compressedImage = new Compressor.Builder(context)
                .setMaxWidth(1280)
                .setMaxHeight(1024)
                .setQuality(85)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(path)
                .build()
                .compressToFile(actualImage);

        deleteRecursive(actualImage);

        return compressedImage;
    }


    private void listProvince() {
        AndroidNetworking.post("http://skripsiku.my.id/meetap/api/profile/tampilProvince")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Province> results = new ArrayList<>();

                        if (results != null)
                            results.clear();

                        try {
                            String message = response.getString("message");

                            if (message.equals("Province Ditemukan")) {
                                String records = response.getString("data");
                                JSONArray dataArr = new JSONArray(records);

                                if (dataArr.length() > 0 && dataArr != null ) {
                                    for (int i = 0; i < dataArr.length(); i++) {
                                        Province province = gson.fromJson(dataArr.getJSONObject(i).toString(), Province.class);
                                        results.add(province);
                                        Log.e(TAG, "province = " + province.getStateProvinceName());
                                    }

                                    setProvince(results);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONException "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "ANError "+anError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void listCountry() {

        AndroidNetworking.post("http://skripsiku.my.id/meetap/api/profile/tampilCountry")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Country> result = new ArrayList<>();
                        try {
                            if (result != null)
                                result.clear();

                            String message = response.getString("message");

                            if (message.equals("Country Ditemukan")) {
                                String records = response.getString("data");

                                JSONArray dataArr = new JSONArray(records);

                                if (dataArr.length() > 0) {
                                    for (int i = 0; i < dataArr.length(); i++) {
                                        Country country = gson.fromJson(dataArr.getJSONObject(i).toString(), Country.class);
                                        result.add(country);
                                        Log.e(TAG, "negara " + country.getNicename());
                                    }
                                    setCountry(result);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONExceptionCountry "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "ANErrorCountry "+anError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void setCountry(final List<Country> countryList) {

//        Province province = new Province(-1, getString(R.string.province));
//        provinceList.add(0, province);

        ArrayAdapter<Country> voteTypeAdapter = new ArrayAdapter<Country>(getApplicationContext(), R.layout.list_country, R.id.tvcountry, countryList) {

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setText(countryList.get(position).getNicename());

//                if (position == 0) {
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
//                } else {
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.grey_700));
//                }

                return textView;
            }

            public View getView(int position, View convertView, ViewGroup parent) {

                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(countryList.get(position).getNicename().toString());

//                if (position==0){
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
//                }else{
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.grey_700));
//                }
                return textView;
            }
        };

        spinner_country.setAdapter(voteTypeAdapter);
        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                con = countryList.get(i).toString();

//                Toast.makeText(getApplicationContext(), prv, Toast.LENGTH_SHORT).show();
//                if (prv.equalsIgnoreCase("")) {
//                    listKota("");
//                } else {
//                    listKota(prv);
//                }

//                if (prv.equalsIgnoreCase("")) {
//                    List<City> results = new ArrayList<>();
//                    City city = new City();
//                    city.setCityName("");
//                    results.add(city);
//
//                } else {
//                    listKota(prv);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
//                String date = day + "-" + month + "-" + year;
                String date = year + "-" + month + "-" + day;
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
        if (requestCode == PICK_IMAGE_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
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

    public void updateprofil() {
        waitingDialog = new SpotsDialog(ProfilActivity.this,"Harap Sabar");
        waitingDialog.show();
        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray newArr = new JSONArray();
            jsonObject.put("id", idUser);
            jsonObject.put("fullname", tiFullname1.getText().toString());
            jsonObject.put("phone_number", etNoTelp.getText().toString());
            jsonObject.put("gender", spJK.getItemAtPosition(spJK.getSelectedItemPosition()).toString());
            jsonObject.put("birthdate", etBirthday.getText().toString());
            jsonObject.put("country", spinner_country.getItemAtPosition(spinner_country.getSelectedItemPosition()).toString());
            jsonObject.put("province", spProvince.getItemAtPosition(spProvince.getSelectedItemPosition()).toString());
            jsonObject.put("city", spKota.getItemAtPosition(spKota.getSelectedItemPosition()).toString());
            jsonObject.put("photo_name", id+photoExt);
            jsonObject.put("photo_file", encodePhoto);
            newArr.put(jsonObject);
            Log.e(TAG,"coba input p = "+ newArr.toString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post("http://skripsiku.my.id/meetap/api/profile/updateProfile?id")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            RegFirebase(emailUser,newPass);
                            Log.e(TAG, "eMail: "+emailUser);
                            Log.e(TAG, "ePass: "+newPass);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONExceptionsUpdateProfil " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Gagal menambah data "+anError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void RegFirebase(final String email, final String password) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        String userIdFirebase = firebaseUser.getUid();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(userIdFirebase);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", userIdFirebase);
                        hashMap.put("username",email);
                        hashMap.put("email",email);
                        hashMap.put("password",password);
                        hashMap.put("Status","Member");
                        hashMap.put("imageURL", id+photoExt);

                        reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()){
                                Intent go = new Intent(ProfilActivity.this, MenuActivity.class);
                                startActivity(go);
                                waitingDialog.dismiss();
                                finish();
                            }else {
                                Toast.makeText(ProfilActivity.this,"Authentification Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

    }

    public void setProvince(final List<Province> provinceList) {
        //        Province province = new Province(-1, getString(R.string.province));
//        provinceList.add(0, province);

        ArrayAdapter<Province> voteTypeAdapter = new ArrayAdapter<Province>(getApplicationContext(), R.layout.province_spinner, provinceList) {

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setText(provinceList.get(position).getStateProvinceName());

//                if (position == 0) {
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
//                } else {
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.grey_700));
//                }

                return textView;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(provinceList.get(position).getStateProvinceName());

//                if (position==0){
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
//                }else{
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.grey_700));
//                }
                return textView;
            }
        };

        spProvince.setAdapter(voteTypeAdapter);
        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                prv = provinceList.get(i).toString();
//                Toast.makeText(getApplicationContext(), prv, Toast.LENGTH_SHORT).show();
                listKota(prv);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void listKota(String prop) {
        AndroidNetworking.post("http://skripsiku.my.id/meetap/api/profile/tampilCity")
                .addBodyParameter("province_name", prop)
//                .addBodyParameter("province_name", "Aceh")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<City> results = new ArrayList<>();


                        try {
                            Log.e(TAG,"criteria = "+ response.toString(1));
                            if (results != null)
                                results.clear();

                            String message = response.getString("message");

                            if (message.equals("City Ditemukan")) {
                                String records = response.getString("data");
                                JSONArray dataArr = new JSONArray(records);

                                if (dataArr.length() > 0) {
                                    for (int i = 0; i < dataArr.length(); i++) {
                                        City city = gson.fromJson(dataArr.getJSONObject(i).toString(), City.class);
                                        results.add(city);
                                        Log.e(TAG, "city = " + city.getCityName());

                                    }

                                    setCity(results);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONExceptionCity " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "ANError CityList" + anError, Toast.LENGTH_LONG).show();
                    }
                });
//        return false;
    }

    private void setCity(final List<City> cityList) {
        ArrayAdapter<City> voteTypeAdapter = new ArrayAdapter<City>(getApplicationContext(), R.layout.city_spinner, cityList) {

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setText(cityList.get(position).getCityName());


//                if (position == 0) {
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
//                } else {
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.grey_700));
//                }
                return textView;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(cityList.get(position).getCityName());

//                if (position==0){
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
//                }else{
//                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.grey_700));
//                }
                return textView;
            }
        };



        spKota.setAdapter(voteTypeAdapter);
        spKota.setSelection(2);
        spKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String cty = cityList.get(i).toString();
//                Toast.makeText(getApplicationContext(), cty, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}

