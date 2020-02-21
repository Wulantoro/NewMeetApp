package com.example.meetap1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.meetap1.Adapter.TicketAdapter;
import com.example.meetap1.Model.NewTicket;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BerandaFragment extends Fragment {
    private RecyclerView rv;
    private TicketAdapter ticketAdapter;
    private Gson gson;
    private List<NewTicket> allList;

    private static String TAG = BerandaFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beranda, null);

        gson = new Gson();
        allList = new ArrayList<>();
        rv = rootView.findViewById(R.id.rvTicketList);
        ticketAdapter = new TicketAdapter(getActivity());
        rv.setAdapter(ticketAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        loadTicket();
        rv.setAdapter(ticketAdapter);

        return rootView;
    }

    public void loadTicket() {
        if (ticketAdapter != null)
            ticketAdapter.clerAll();

        AndroidNetworking.post("http://skripsiku.my.id/meetap/api/ticket/tampilTicket")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<NewTicket> result = new ArrayList<>();
                        try {
                            if (result != null)
                                result.clear();

                            String message = response.getString("message");

                            if (message.equals("Ticket Ditemukan")) {
                                String records = response.getString("data");

                                JSONArray dataArr = new JSONArray(records);

                                if (dataArr.length() > 0) {
                                    for (int i = 0; i < dataArr.length(); i++) {
                                        NewTicket ticket = gson.fromJson(dataArr.getJSONObject(i).toString(), NewTicket.class);
                                        result.add(ticket);
                                        Log.e(TAG, "id" + ticket.getId());

                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ticketAdapter.addAll(result);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
