package com.example.meetap1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetap1.ChatActivity;
import com.example.meetap1.Model.User;
import com.example.meetap1.Model.UserFirebase;
import com.example.meetap1.R;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private Context mContext;
    private List<User> mUser;
    private List<UserFirebase>mUserFirebase;
    private static String TAG = MemberAdapter.class.getSimpleName();
    public MemberAdapter(Context context){
        mContext = context;
        mUser = new ArrayList<>();
        mUserFirebase = new ArrayList<>();
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new MemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        final User user = mUser.get(holder.getAdapterPosition());

        Log.e(TAG, "dataArr" +user.getFullname());

//        User userCurrent = mUser.get(position);
        holder.tvMember.setText(user.getFullname());
        holder.tvEmail.setText(user.getCountry());
        holder.rvMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("Member", user.getFullname() );

                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public void add(User user){
        mUser.add(user);
        notifyItemInserted(mUser.size() -1);
    }

    public void addAll(List<User> MoveResult){
        for(User user : MoveResult){
            add(user);
        }
    }

    private void remove(User r) {
        int position = mUser.indexOf(r);
        if (position > -1) {
            mUser.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    private User getItem(int i) {
        if (mUser != null) {
            return  mUser.get(i);
        }
        return null;
    }

    public void clerAll() {
        if (!mUser.isEmpty()) {
            mUser.clear();
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public class MemberViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMember, tvEmail;
        public ImageView imgMemver;
        public RelativeLayout rvMember;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMember = itemView.findViewById(R.id.tvNameMember);
            tvEmail = itemView.findViewById(R.id.tvEmailMember);
            rvMember = itemView.findViewById(R.id.RvMember);

        }
    }
}
