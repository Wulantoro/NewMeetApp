package com.example.meetap1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.meetap1.Adapter.TicketAdapter;
import com.example.meetap1.Model.Ticket;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitleQuest;
    private TicketAdapter ticketAdapter;
    private Ticket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitleQuest = findViewById(R.id.tvTitleQuest);

        ticketAdapter = new TicketAdapter(this);

        ticket = getIntent().getParcelableExtra("key_ticket");
        tvTitleQuest.setText(ticket.getTitle());
    }
}
