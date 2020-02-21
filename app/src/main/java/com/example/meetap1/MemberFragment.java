package com.example.meetap1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.meetap1.Adapter.MemberAdapter;
import com.example.meetap1.Adapter.MemberFirebaseAdapter;
import com.example.meetap1.Model.User;
import com.example.meetap1.Model.UserFirebase;
import com.example.meetap1.Notification.Data;
import com.example.meetap1.Utils.SharedPref;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;



public class MemberFragment extends Fragment {

    View v;
    private RecyclerView rvMember;
    private MemberAdapter memberAdapter;
    private static String TAG = MemberFragment.class.getSimpleName();
    public SharedPreferences pref, prf;
    private TextView tvid;

    //milik firebase
    private MemberFirebaseAdapter memberFirebaseAdapter;
    private List<UserFirebase> mUserFirebase;

    private Gson gson;
    private List<User> users;
    String Id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_member, container, false);
//
//        gson = new Gson();
//        users = new ArrayList<>();
//        rvMember = v.findViewById(R.id.rvMember);
//        rvMember.setHasFixedSize(true);
//        memberAdapter = new MemberAdapter(getActivity());
//        rvMember.setAdapter(memberAdapter);
//        rvMember.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//
//        rvMember.setAdapter(memberAdapter);

//        String IdUser = getArguments().getString("key");

//        Bundle bundle = getArguments().getBundle()
//        if (bundle != null){
//
//            String IdUser = bundle.getString("key");
//            Toast.makeText(getApplicationContext(), IdUser, Toast.LENGTH_SHORT).show();
//        }
//        Bundle args = new Bundle();
//        String IdUser = args.getString("key");
//        Toast.makeText(getApplicationContext(), IdUser, Toast.LENGTH_SHORT).show();

//        String args = getArguments().getString("key");
//        Log.e(TAG,"iDuSER : "+args);
//        Toast.makeText(getApplicationContext(), args, Toast.LENGTH_SHORT).show();

        SharedPreferences prf = this.getActivity().getSharedPreferences("id", getContext().MODE_PRIVATE);
        Id = prf.getString("IdUser", null);

        rvMember = v.findViewById(R.id.rvMember);
        rvMember.setHasFixedSize(true);
        rvMember.setLayoutManager(new LinearLayoutManager(getContext()));

        mUserFirebase = new ArrayList<>();

        readUserFirebase();
        //memberList(Id);

        return v;
    }


//    public void member() {
//
//        if (memberAdapter != null) {
//            memberAdapter.clerAll();
//
//            AndroidNetworking.post("http://ask.meetap.id/api/profile/tampilProfile")
//                    .setPriority(Priority.MEDIUM)
//                    .build()
//                    .getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            List<User> result = new ArrayList<>();
//                            try {
//                                if (result != null)
//                                    result.clear();
//                                String message = response.getString("message");
//                                if (message.equals("Profile Ditemukan")) {
//                                    String records = response.getString("data");
//
//                                    JSONArray dataArr = new JSONArray(records);
//
//                                    if (dataArr.length() > 0) {
//                                        for (int i = 0; i < dataArr.length(); i++) {
//                                            User user = gson.fromJson(dataArr.getJSONObject(i).toString(), User.class);
//                                            result.add(user);
//                                            Log.e(TAG, "id" +user.getUser_id());
//
//                                        }
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            memberAdapter.addAll(result);
//                        }
//
//                        @Override
//                        public void onError(ANError anError) {
//
//                        }
//                    });
//        }
//    }

    public void memberList(String id){
        //Log.e(TAG,"ID_USER: " +Id);
        if (memberAdapter != null){
            memberAdapter.clerAll();

            AndroidNetworking.post("http://skripsiku.my.id/meetap/api/profile/tampilOtherProfile")
                    .addBodyParameter("my_id", id)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.e(TAG,"ID_USER2: "+Id);

                           List<User> result = new ArrayList<>();
                           try {
                               if (result != null)
                                   result.clear();

                               String message = response.getString("message");

                               if (message.equals("Profile Ditemukan")){

                                   String records = response.getString("data");

                                   JSONArray dataArr = new JSONArray(records);

                                   if (dataArr.length() > 0) {
                                       for (int i = 0; i < dataArr.length(); i++) {
                                           User user = gson.fromJson(dataArr.getJSONObject(i).toString(), User.class);
                                           result.add(user);
                                           Log.e(TAG, "id" + user.getUser_id());

                                       }
                                   }
                               }else {

                                   Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                               }

                           } catch (Exception e) {
                               e.printStackTrace();
                           }

                           memberAdapter.addAll(result);
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        }
    }

    public void readUserFirebase(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserFirebase.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserFirebase userFirebase = snapshot.getValue(UserFirebase.class);

                    assert userFirebase != null;
                    assert firebaseUser != null;
                    if (!userFirebase.getId().equals(firebaseUser.getUid())){
                        mUserFirebase.add(userFirebase);
                    }
                }

                memberFirebaseAdapter = new MemberFirebaseAdapter(getContext(), mUserFirebase);
                rvMember.setAdapter(memberFirebaseAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
