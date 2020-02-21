package com.example.meetap1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.carrier.CarrierService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.meetap1.Model.UserId;
import com.example.meetap1.Utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilFragment extends Fragment {

    public SharedPreferences pref, prf;

    public TextView Username, Email, Pendidikan, Ulangtahun, Negara, Alamat, NoTelp;
    public CircleImageView imgUser;
    String idUser;
    private ImageView imgLogOut;
    private static String TAG = ProfilFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profil, container, false);
        setHasOptionsMenu(true);

        Username = v.findViewById(R.id.tvUsernameProfil);
        Email = v.findViewById(R.id.tvEmailProfil);
        Pendidikan = v.findViewById(R.id.tvPendidikanProfil);
        imgUser = v.findViewById(R.id.imgUserProfil);
        Ulangtahun = v.findViewById(R.id.tvUlangtahunProfil);
        Negara = v.findViewById(R.id.tvCountryProfil);
        Alamat = v.findViewById(R.id.tvAlamatProfil);
        NoTelp = v.findViewById(R.id.tvTelp);
        imgLogOut = v.findViewById(R.id.imgLogOut);

        pref = this.getActivity().getSharedPreferences("id",getActivity().MODE_PRIVATE);
        idUser = pref.getString("IdUser", null);
        Log.e(TAG,"User id : " +idUser);

        readProfil();

        imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogout();
            }
        });

        return v;
    }

    private void readProfil() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", idUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post("http://skripsiku.my.id/meetap/api/profile/tampilProfile")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.e(TAG,"RESPONSE : " +response.toString(1));
                            String message = response.getString("message");
                            if (message.equals("Profile Ditemukan")){
                                String records = response.getString("data");
                                JSONObject jsonObject = new JSONObject(records);
                                Username.setText(jsonObject.getString("fullname"));
                                Email.setText(jsonObject.getString("email"));
                                Ulangtahun.setText(jsonObject.getString("birthdate"));
                                Negara.setText(jsonObject.getString("country"));
                                NoTelp.setText(jsonObject.getString("handphone_number"));
                                Glide.with(getContext())
                                        .load(jsonObject.getString("photo_file"))
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .centerCrop()
                                        .dontAnimate()
                                        .into(imgUser);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


    private void userLogout() {
        final SharedPref sharedPrefManager;
        sharedPrefManager = new SharedPref(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Really Logout?")
                .setMessage("Are you sure you want to Logout?")
                .setCancelable(false)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPrefManager.saveSPBoolean(SharedPref.SP_SUDAH_LOGIN, false);
                        startActivity(new Intent(getActivity(), LoginActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                        finish();

                    }
                }).create().show();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profil, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Bantuan :
                //DO SOMETHING
                break;
            case R.id.Informasi :
                //DO SOMETHING
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
