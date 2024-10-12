package com.example.cuppingapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CoffeeDetailFragment extends Fragment {

    private Coffee coffee;
    private CoffeeDao coffeeDao;
    private Button buttonUpdate, buttonDelete, buttonCancel;
    private EditText editTextCoffeeName, editTextCoffeeOrigin, editTextCoffeeProcess, editTextCoffeeVarietal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee_detail, container, false);

        // Initialize views
        editTextCoffeeName = view.findViewById(R.id.editTextCoffeeName);
        editTextCoffeeOrigin = view.findViewById(R.id.editTextCoffeeOrigin);
        editTextCoffeeProcess = view.findViewById(R.id.editTextCoffeeProcess);
        editTextCoffeeVarietal = view.findViewById(R.id.editTextCoffeeVarietal);
        buttonUpdate = view.findViewById(R.id.buttonUpdateCoffee);
        buttonDelete = view.findViewById(R.id.buttonDeleteCoffee);
        buttonCancel = view.findViewById(R.id.buttonCancelCoffee);

        coffeeDao = new CoffeeDao(requireContext());

        // Get the selected coffee from the arguments
        if (getArguments() != null) {
            int coffeeID = getArguments().getInt("coffeeID");

            // Log the coffeeID to verify it was passed correctly
            Log.d("CoffeeDetailFragment", "Received Coffee ID: " + coffeeID);

            // Retrieve the full coffee object from the database
            coffee = coffeeDao.getCoffeeById(coffeeID);

            if (coffee != null) {
                editTextCoffeeName.setText(coffee.getName());
                editTextCoffeeOrigin.setText(coffee.getOrigin());
                editTextCoffeeProcess.setText(coffee.getProcess());
                editTextCoffeeVarietal.setText(coffee.getVarietal());
            } else {
                Toast.makeText(getContext(), "Failed to load coffee details.", Toast.LENGTH_SHORT).show();
            }
        }

        // Handle Update button click
        buttonUpdate.setOnClickListener(v -> {
            if (coffee != null) {
                updateCoffee();
            }
        });

        // Handle Delete button click
        buttonDelete.setOnClickListener(v -> {
            if (coffee != null) {
                deleteCoffee();
            }
        });

        // Handle Cancel button click (Go back)
        buttonCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void updateCoffee() {
        // Get updated data from EditText fields
        String newCoffeeName = editTextCoffeeName.getText().toString();
        String newCoffeeOrigin = editTextCoffeeOrigin.getText().toString();
        String newCoffeeProcess = editTextCoffeeProcess.getText().toString();
        String newCoffeeVarietal = editTextCoffeeVarietal.getText().toString();

        // Validate the input
        if (TextUtils.isEmpty(newCoffeeName) || TextUtils.isEmpty(newCoffeeOrigin) || TextUtils.isEmpty(newCoffeeProcess) || TextUtils.isEmpty(newCoffeeVarietal)) {
            Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the coffee object
        coffee.setName(newCoffeeName);
        coffee.setOrigin(newCoffeeOrigin);
        coffee.setProcess(newCoffeeProcess);
        coffee.setVarietal(newCoffeeVarietal);

        // Update the coffee in the database
        boolean isUpdated = coffeeDao.updateCoffee(coffee);

        if (isUpdated) {
            Toast.makeText(getContext(), "Coffee updated successfully!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack(); // Go back after update
        } else {
            Toast.makeText(getContext(), "Failed to update coffee.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCoffee() {
        // Perform the delete operation
        boolean isDeleted = coffeeDao.deleteCoffee(coffee.getCoffeeID());

        if (isDeleted) {
            Toast.makeText(getContext(), "Coffee deleted successfully!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        } else {
            Toast.makeText(getContext(), "Failed to delete coffee.", Toast.LENGTH_SHORT).show();
        }
    }
}
