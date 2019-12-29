package com.example.meetap1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.meetap1.Adapter.MessageAdapter;
import com.example.meetap1.Model.Chat;
import com.example.meetap1.Model.UserFirebase;
import com.example.meetap1.Notification.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    TextView tvNameMember;
    private ImageButton btnSend;
    private EditText etSend;
    private CircleImageView imgUser;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    MessageAdapter messageAdapter;
    List<Chat> mChat;

    RecyclerView recyclerView;

    private static String TAG = MemberFragment.class.getSimpleName();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        userId = getIntent().getStringExtra("userId");
        recyclerView = findViewById(R.id.rvChat);
        btnSend = findViewById(R.id.btnSend);
        etSend = findViewById(R.id.etInput);
        tvNameMember = (TextView) findViewById(R.id.tvNameMemberChat);
        imgUser = findViewById(R.id.imgUserChat);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        tvNameMember.setText("");
        Log.e(TAG,"MEMBER : " +userId);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserFirebase userFirebase = dataSnapshot.getValue(UserFirebase.class);
                Log.e(TAG,"IMAGE: "+userFirebase.getImageURL());
                tvNameMember.setText(userFirebase.getEmail());
                if (userFirebase.getImageURL().equals("default")){
                    imgUser.setImageResource(R.drawable.icon_profil);
                }else {
                    Glide.with(ChatActivity.this).load(userFirebase.getImageURL()).into(imgUser);
                }

                readMessage(firebaseUser.getUid(), userId, userFirebase.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etSend.getText().toString();

                if (!msg.equals("")){
                    sendMessage(firebaseUser.getUid(), userId, msg);
                }else {
                    Toast.makeText(ChatActivity.this, "You cant send empty message", Toast.LENGTH_SHORT).show();
                }

                etSend.setText("");
            }
        });

    }

    private void updateToken(String token) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        //reference.child()
    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(final String myid, final String userId, final String imageurl){
        mChat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userId)
                            || chat.getReceiver().equals(userId) && chat.getSender().equals(myid)){
                        mChat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(ChatActivity.this, mChat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
