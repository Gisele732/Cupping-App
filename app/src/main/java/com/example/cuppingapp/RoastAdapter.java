package com.example.cuppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RoastAdapter extends BaseAdapter {

    private Context context;
    private List<Roast> roastList;

    public RoastAdapter(Context context, List<Roast> roastList) {
        this.context = context;
        this.roastList = roastList;
    }

    @Override
    public int getCount() {
        return roastList.size();
    }

    @Override
    public Object getItem(int position) {
        return roastList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_roast, parent, false);
        }

        // Get current Roast object
        Roast roast = roastList.get(position);

        // Bind Roast data to UI elements
        TextView textViewRoastID = convertView.findViewById(R.id.textViewRoastID);
        // Convert integer coffeeID to String before setting it to the TextView
        textViewRoastID.setText(String.valueOf(roast.getRoastID()));

        return convertView;
    }
}

