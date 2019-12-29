package com.example.meetap1.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetap1.Model.Coment;
import com.example.meetap1.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ComentAdapter extends RecyclerView.Adapter<ComentAdapter.ViewHolder> {


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Coment> mComent;
    private String imgUrl;
    public SharedPreferences pref;

    private static String TAG = ComentAdapter.class.getSimpleName();


    public ComentAdapter(Context mContext, List<Coment> mComent, String imgUrl) {
        this.mContext = mContext;
        this.mComent = mComent;
        this.imgUrl = imgUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){

            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ComentAdapter.ViewHolder(view);

        }else {

            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new ComentAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Coment coment = mComent.get(position);

        holder.show_Coment.setText(coment.getContent());
    }

    @Override
    public int getItemCount() {
        return mComent.size();
    }

    public void add(Coment r) {
        mComent.add(r);
        notifyItemInserted(mComent.size() - 1);
    }

    public void addAll (List<Coment> moveResults) {
        for (Coment result : moveResults) {
            add(result);
        }
    }

    private Coment getItem(int position) {
        if (mComent != null) {
            return  mComent.get(position);
        }
        return null;
    }

    private void remove(Coment r) {
        int position = mComent.indexOf(r);
        if (position > -1) {
            mComent.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void clerAll() {
        if (!mComent.isEmpty()) {
            mComent.clear();
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_Coment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_Coment = itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        pref = mContext.getSharedPreferences("id", MODE_PRIVATE);
        String idUser = pref.getString("IdUser", null);

        Log.e(TAG,"IDUSER: "+idUser);
        if (mComent.get(position).getUserId().equals(idUser)){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}
