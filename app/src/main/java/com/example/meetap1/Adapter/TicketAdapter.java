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
import com.example.meetap1.ComentarActivity;
import com.example.meetap1.DetailActivity;
import com.example.meetap1.Model.NewTicket;
//import com.example.meetap1.Model.Ticket;
import com.example.meetap1.R;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private Context context;
    private List<NewTicket> list;
    private static String TAG = TicketAdapter.class.getSimpleName();

    public TicketAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }


    @NonNull
    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        final TicketAdapter.ViewHolder holder = new TicketAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewTicket ticket = list.get(holder.getAdapterPosition());

        Log.e(TAG, "dataArr" + ticket.getImagesFile());


        holder.tvTitleQuest.setText(ticket.getTitle());
        holder.tvcreate.setText(ticket.getCreated());
        holder.tvNamaUser.setText(ticket.getUsername());
        holder.tvTitleQuest.setText(ticket.getTitle());
        holder.tvContent.setText(ticket.getContent());
        holder.tvStatus.setText(ticket.getStatus());
        holder.tvcreate.setText(ticket.getCreated());
        if (ticket.getPhotoProfile() == null){
            holder.imgUser.setImageResource(R.drawable.icon_profil);
        } else {
            Glide.with(context).load(ticket.getPhotoProfile()).into(holder.imgUser);
        }

        if (ticket.getImagesFile().equals("")){
            holder.imgContent.setVisibility(View.INVISIBLE);
        }else {
            Glide.with(context).load(ticket.getImagesFile()).into(holder.imgContent);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ComentarActivity.class);
                intent.putExtra("key_ticket", ticket.getId());
                intent.putExtra("judul", ticket.getTitle());
                intent.putExtra("content", ticket.getContent());
                intent.putExtra("status", ticket.getStatus());
                intent.putExtra("created", ticket.getCreated());
                intent.putExtra("namaUser", ticket.getUsername());
                intent.putExtra("imageFile", ticket.getImagesFile());
                intent.putExtra("imageUser", ticket.getPhotoProfile());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(NewTicket r) {
        list.add(r);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll (List<NewTicket> moveResults) {
        for (NewTicket result : moveResults) {
            add(result);
        }
    }

    private void remove(NewTicket r) {
        int position = list.indexOf(r);
        if (position > -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void clerAll() {
        if (!list.isEmpty()) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    private NewTicket getItem(int position) {
        if (list != null) {
            return  list.get(position);
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitleQuest;
        public TextView tvcreate, tvContent, tvStatus, tvNamaUser;
        public ImageView imgUser, imgContent, imgStar, imgComent, imgShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitleQuest = itemView.findViewById(R.id.tvTitleQuest);
            tvcreate = itemView.findViewById(R.id.tvcreate);
            tvContent = itemView.findViewById(R.id.tvContentTicket);
            tvStatus = itemView.findViewById(R.id.statusTicket);
            tvNamaUser = itemView.findViewById(R.id.tvNameUser);
            imgUser = itemView.findViewById(R.id.imgUserTicket);
            imgContent = itemView.findViewById(R.id.imgTicket);
            imgStar = itemView.findViewById(R.id.imgStarTicket);
            imgComent = itemView.findViewById(R.id.imgComentarTicket);
            imgShare = itemView.findViewById(R.id.imgShareTicket);

        }
    }
}
