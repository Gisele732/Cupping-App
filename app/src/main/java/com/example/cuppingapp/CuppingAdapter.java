package com.example.cuppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuppingapp.Cupping;
import com.example.cuppingapp.CuppingDao;

import java.util.List;

public class CuppingAdapter extends ArrayAdapter<Cupping> {

    private Context context;
    private List<Cupping> cuppings;
    private CuppingDao cuppingDao;

    public CuppingAdapter(Context context, List<Cupping> cuppings) {
        super(context, 0, cuppings);
        this.context = context;
        this.cuppings = cuppings;
        cuppingDao = new CuppingDao(context); // Initialize DAO
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout if it's not already inflated
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_cupping, parent, false);
        }

        // Get the cupping for this position
        Cupping cupping = getItem(position);

        // Find the views in the custom layout
        TextView textViewCupping = convertView.findViewById(R.id.textViewCupping);
        ImageView iconEdit = convertView.findViewById(R.id.iconEdit);
        ImageView iconDelete = convertView.findViewById(R.id.iconDelete);

        // Set the text for the cupping
        textViewCupping.setText(cupping.getDate() + "\n" + cupping.getCoffeeName() + "\nScore: " + cupping.getTotalScore());

        // Set click listener for the edit icon
        iconEdit.setOnClickListener(v -> {
            // Open an edit dialog or activity for modifying the cupping
            ((ViewCuppings) context).openEditCuppingFragment(cupping);
        });

        // Set click listener for the delete icon
        iconDelete.setOnClickListener(v -> {
            // Delete the cupping from the database
            cuppingDao.deleteCupping(cupping.getCuppingID());

            // Remove the item from the list and notify the adapter
            cuppings.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }
}
