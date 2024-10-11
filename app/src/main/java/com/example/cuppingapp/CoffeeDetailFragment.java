package com.example.cuppingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CoffeeDetailFragment extends Fragment {

    private Coffee selectedCoffee;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee_detail, container, false);

        // Get the selected coffee from the arguments
        if (getArguments() != null) {
            selectedCoffee = (Coffee) getArguments().getSerializable("selectedCoffee");
        }

        // Bind coffee details to UI elements
        if (selectedCoffee != null) {
            // Bind Coffee Name
            TextView coffeeNameTextView = view.findViewById(R.id.textViewCoffeeName);
            coffeeNameTextView.setText(selectedCoffee.getName());

            // Bind Coffee Origin
            TextView coffeeOriginTextView = view.findViewById(R.id.textViewCoffeeOrigin);
            coffeeOriginTextView.setText(selectedCoffee.getOrigin());

            // Bind Coffee Process
            TextView coffeeProcessTextView = view.findViewById(R.id.textViewCoffeeProcess);
            coffeeProcessTextView.setText(selectedCoffee.getProcess());

            // Bind Coffee Varietal
            TextView coffeeVarietalTextView = view.findViewById(R.id.textViewCoffeeVarietal);
            coffeeVarietalTextView.setText(selectedCoffee.getVarietal());
        }

        return view;
    }
}
