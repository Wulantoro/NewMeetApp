package com.example.meetap1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.meetap1.Adapter.ComentAdapter;
import com.example.meetap1.Adapter.TicketAdapter;
import com.example.meetap1.Model.Coment;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ComentarActivity extends AppCompatActivity {
    private static String TAG = ComentarActivity.class.getSimpleName();

    TextView nmUser, tanggal, status, content, judul;
    ImageView imgUser, imgContent, imgBack;
    List<Coment> mComent;
    ImageButton btnComent;
    EditText etInputComent;
    public SharedPreferences pref;

    private RecyclerView rvComent;
    private ComentAdapter comentAdapter;
    private Gson gson;

    String idTicket, idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentar);

        idTicket = getIntent().getStringExtra("key_ticket");
        String sNmUser = getIntent().getStringExtra("namaUser");
        String sCreated = getIntent().getStringExtra("created");
        String sStatus = getIntent().getStringExtra("status");
        String sContent = getIntent().getStringExtra("content");
        String sImgUser = getIntent().getStringExtra("imageUser");
        String sImgContent = getIntent().getStringExtra("imageFile");

        Log.e(TAG,"ID_TIKET: "+idTicket);
        Log.e(TAG,"user: "+sNmUser);
        Log.e(TAG,"tanggal: "+sCreated);
        Log.e(TAG,"status: "+sStatus);
        Log.e(TAG,"content: "+sContent);
        Log.e(TAG,"imgUser: "+sImgUser);
        Log.e(TAG,"imgContent: "+sImgContent);

        nmUser = findViewById(R.id.tvNmUser);
        tanggal = findViewById(R.id.tvCreateTgl);
        status = findViewById(R.id.tvStatus);
        content = findViewById(R.id.tvBody);
        imgUser = findViewById(R.id.imgUserComent);
        imgContent = findViewById(R.id.imgComent);
        rvComent = findViewById(R.id.rvComent);
        imgBack = findViewById(R.id.back);
        btnComent = findViewById(R.id.btnComent);
        etInputComent = findViewById(R.id.etInputComent);

        pref = getSharedPreferences("id", MODE_PRIVATE);
        idUser = pref.getString("IdUser", null);

        nmUser.setText(sNmUser);
        tanggal.setText(sCreated);
        status.setText(sStatus);
        content.setText(sContent);

        gson = new Gson();
        mComent = new ArrayList<>();
        comentAdapter = new ComentAdapter(this,mComent,"");
        rvComent.setAdapter(comentAdapter);
        rvComent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        readComent();
        rvComent.setAdapter(comentAdapter);


        //Glide.with(ComentarActivity.this).load("http://ask.meetap.id/api/assets/img_profile/1/profile1.jpg").into(imgUser);

        if (sImgUser.isEmpty()){
            imgUser.setImageResource(R.drawable.icon_profil);
        } else {
            Glide.with(ComentarActivity.this).load(sImgUser).into(imgUser);
        }

        if (sImgContent.isEmpty()){
            imgContent.setVisibility(View.VISIBLE);
        }else {
            Glide.with(ComentarActivity.this).load(sImgContent).into(imgContent);
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = new BerandaFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.frameBeranda,fragment).commit();

                //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //fragmentTransaction.replace(R.id.frameBeranda,fragment).addToBackStack(null);
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("fromFragment", "comment");
                startActivity(intent);
                finish();

            }
        });

        btnComent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coment = etInputComent.getText().toString();
                if (!coment.equals("")){
                    SendComent();
                }else {
                    Toast.makeText(ComentarActivity.this, "You cant send empty message", Toast.LENGTH_SHORT).show();
                }

                etInputComent.setText("");

            }
        });


    }

    private void SendComent() {
        final JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();

            jsonObject.put("user_id", idUser);
            jsonObject.put("ticket_id",idTicket);
            jsonObject.put("content", etInputComent.getText().toString().trim());

            jsonArray.put(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG,"Contentt: "+etInputComent);
        AndroidNetworking.post("http://skripsiku.my.id/meetap/api/ticket/insertKomentar")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(ComentarActivity.this, message, Toast.LENGTH_SHORT).show();
                            readComent();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    private void readComent(){
        if (comentAdapter != null)
            comentAdapter.clerAll();

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ticket_id", idTicket);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("http://skripsiku.my.id/meetap/api/ticket/tampilKomentar")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Coment> result = new ArrayList<>();
                        try {
                            if (result != null)
                                result.clear();

                            String message = response.getString("message");

                            if (message.equals("Komentar ditemukan")){
                                String records = response.getString("data");
                                JSONArray dataAr = new JSONArray(records);

                                if (dataAr.length() > 0){
                                    for (int i = 0; i < dataAr.length(); i++) {
                                        Coment coment = gson.fromJson(dataAr.getJSONObject(i).toString(), Coment.class);
                                        result.add(coment);
                                        Log.e(TAG, "id" + coment.getTicketId());

                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        comentAdapter.addAll(result);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }
}
