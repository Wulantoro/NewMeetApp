package com.example.meetap1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.meetap1.ChatActivity;
import com.example.meetap1.Model.UserFirebase;
import com.example.meetap1.R;

import java.util.List;

public class MemberFirebaseAdapter extends RecyclerView.Adapter<MemberFirebaseAdapter.ViewHolder> {

    private Context mContext;
    private List<UserFirebase> mUserFirebase;

    private static String TAG = MemberFirebaseAdapter.class.getSimpleName();

    public MemberFirebaseAdapter(Context mContext, List<UserFirebase> mUserFirebase){
        this.mContext = mContext;
        this.mUserFirebase = mUserFirebase;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_member, parent, false);
        return new MemberFirebaseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserFirebase userFirebase = mUserFirebase.get(position);
        holder.Username.setText(userFirebase.getEmail());

        Log.e(TAG,"IMAGE: "+userFirebase.getImageURL());
        Log.e(TAG,"IMAGE2: "+userFirebase.getEmail());

        if (userFirebase.getImageURL().equals("default")){
            holder.imgUser.setImageResource(R.drawable.icon_profil);
        }else {
            Glide.with(mContext).load(userFirebase.getImageURL()).into(holder.imgUser);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("userId", userFirebase.getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserFirebase.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView Username;
        public ImageView imgUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Username = itemView.findViewById(R.id.tvNameMember);
            imgUser = itemView.findViewById(R.id.imgUser);
        }
    }
}
