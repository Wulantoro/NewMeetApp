package com.example.meetap1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetap1.Model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private Context context;
    private List<Ticket> list;

    public TicketAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public TicketAdapter(BerandaFragment berandaFragment) {
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
        final Ticket ticket = list.get(holder.getAdapterPosition());

        holder.tvProblem.setText(ticket.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return 0;
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
        public TextView tvProblem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProblem = itemView.findViewById(R.id.tvProblem);
        }
    }
}
