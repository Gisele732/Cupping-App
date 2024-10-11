package com.example.cuppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CoffeeAdapter extends BaseAdapter {

    private Context context;
    private List<Coffee> coffeeList;

    public CoffeeAdapter(Context context, List<Coffee> coffeeList) {
        this.context = context;
        this.coffeeList = coffeeList;
    }

    @Override
    public int getCount() {
        return coffeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return coffeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_coffee, parent, false);
        }

        // Get current Coffee object
        Coffee coffee = coffeeList.get(position);

        // Bind Coffee data to UI elements
        TextView coffeeName = convertView.findViewById(R.id.textViewCoffeeName);
        coffeeName.setText(coffee.getName());

        return convertView;
    }
}
