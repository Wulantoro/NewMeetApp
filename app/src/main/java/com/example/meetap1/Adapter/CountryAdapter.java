package com.example.meetap1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.meetap1.Model.Country;
import com.example.meetap1.R;

import java.util.List;

public class CountryAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Country> list_item;

    public CountryAdapter(Activity activity, List<Country> item) {
        this.activity = activity;
        this.list_item = item;
    }

    @Override
    public int getCount() {
        return list_item.size();
    }

    @Override
    public Object getItem(int location) {
        return list_item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Country> getList_item() {
        return list_item;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_country, null);

        TextView pendidikan = (TextView) convertView.findViewById(R.id.tvcountry);

        Country country;
        country = list_item.get(position);

        pendidikan.setText(country.getNicename());

        return convertView;
    }

}
