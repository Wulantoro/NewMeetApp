package com.example.meetap1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.meetap1.Model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AskFragment extends Fragment {

    private Button btnPublish;
    private TextInputEditText tags;
    private TextInputEditText tiBody;
    private TextInputEditText tiTitle;
    private TextView tviduser;

    private Gson gson;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        return inflater.inflate(R.layout.fragment_ask, null);
        View rootView = inflater.inflate(R.layout.fragment_ask, container, false);

        btnPublish = rootView.findViewById(R.id.btnPublish);
        tags = rootView.findViewById(R.id.tags);
        tiBody = rootView.findViewById(R.id.tiBody);
        tiTitle = rootView.findViewById(R.id.tiTitle);
        tviduser = rootView.findViewById(R.id.tviduser);

        TextView iduser = rootView.findViewById(R.id.tviduser);
        iduser.setText("1");


        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishTicket();
            }
        });

        loadUser();

        return rootView;
    }


    public void loadUser() {

        AndroidNetworking.post("http://ask.meetap.id/api/profile/tampilProfile")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<User> result = new ArrayList<>();
                        try {
                            if (result != null)
                                result.clear();

                            Log.e("tampil user", response.toString(1));

                            String message = response.getString("message");

                            if (message.equals("Profile Ditemuka")) {
                                String records = response.getString("data");

                                JSONArray dataArr = new JSONArray(records);

                                if (dataArr.length() > 0) {
                                    for (int i = 0; i < dataArr.length(); i ++) {
                                        User user = gson.fromJson(dataArr.getJSONObject(i).toString(), User.class);
                                        result.add(user);
//                                        tviduser.setText(String.valueOf(user.getUser_id()));
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


//    [{"img":"/assets/img_ticket/7/screenshot1.jpg","thumbnail":"1"},{"img":"/assets/img_ticket/7/screenshot2.jpg","thumbnail":"0"}]


    public void publishTicket() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            JSONArray newArr = new JSONArray();
//
//            jsonObject.put("title", tiTitle.getText().toString());
//            jsonObject.put("content", tiBody.getText().toString());
//            jsonObject.put("user_id", tags.getText().toString());
//
//            newArr.put(jsonObject);
//            Log.e("coba input = ", newArr.toString(1));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        AndroidNetworking.post("http://ask.meetap.id/api/ticket/insertTicket")
//                .addJSONObjectBody(jsonObject)
                .addBodyParameter("title", tiTitle.getText().toString())
                .addBodyParameter("content", tiBody.getText().toString())
                .addBodyParameter("user_id", tviduser.getText().toString())
                .addBodyParameter("category_id", tags.getText().toString())
//                .addBodyParameter("title", tiTitle.getText().toString())
//                .addBodyParameter("title", tiTitle.getText().toString())
//                .addBodyParameter("title", tiTitle.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "JSONEXceptions"+ e, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), "Gagal menambah data", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
