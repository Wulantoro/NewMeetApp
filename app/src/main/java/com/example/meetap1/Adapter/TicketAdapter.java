package com.example.meetap1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetap1.DetailActivity;
import com.example.meetap1.Model.Ticket;
import com.example.meetap1.R;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private Context context;
    private List<Ticket> list;
    private static String TAG = TicketAdapter.class.getSimpleName();

    public TicketAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

//    [{"img":"/assets/img_ticket/7/screenshot1.jpg","thumbnail":"1"},{"img":"/assets/img_ticket/7/screenshot2.jpg","thumbnail":"0"}]


    @NonNull
    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        final TicketAdapter.ViewHolder holder = new TicketAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Ticket ticket = list.get(holder.getAdapterPosition());

        Log.e(TAG, "dataArr" + ticket.getContent());

        holder.tvTitleQuest.setText(ticket.getTitle());
        holder.tvcreate.setText(ticket.getCreated());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("key_ticket", ticket);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Ticket r) {
        list.add(r);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll (List<Ticket> moveResults) {
        for (Ticket result : moveResults) {
            add(result);
        }
    }

    private void remove(Ticket r) {
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

    private Ticket getItem(int position) {
        if (list != null) {
            return  list.get(position);
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitleQuest;
        public TextView tvcreate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitleQuest = itemView.findViewById(R.id.tvTitleQuest);
            tvcreate = itemView.findViewById(R.id.tvcreate);
        }
    }
}
