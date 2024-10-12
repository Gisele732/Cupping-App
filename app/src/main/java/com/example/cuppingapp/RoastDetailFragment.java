package com.example.cuppingapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RoastDetailFragment extends Fragment {

    private Roast roast;
    private RoastDao roastDao;
    private Button buttonUpdate, buttonDelete, buttonCancel;
    private EditText editTextBatchNumber, editTextRoasterName, editTextRoastDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roast_detail, container, false);

        // Initialize views
        TextView roastIDTextView = view.findViewById(R.id.textViewRoastID);
        editTextBatchNumber = view.findViewById(R.id.editTextBatchNumber);
        editTextRoasterName = view.findViewById(R.id.editTextRoasterName);
        editTextRoastDate = view.findViewById(R.id.editTextRoastDate);
        buttonUpdate = view.findViewById(R.id.buttonUpdateRoast);
        buttonDelete = view.findViewById(R.id.buttonDeleteRoast);
        buttonCancel = view.findViewById(R.id.buttonCancelRoast);

        roastDao = new RoastDao(requireContext());

        // Get the selected roast from the arguments
        if (getArguments() != null) {
            int roastID = getArguments().getInt("roastID");

            // Retrieve the full roast object from the database
            roast = roastDao.getRoastById(roastID);

            if (roast != null) {
                roastIDTextView.setText(String.valueOf(roast.getRoastID()));
                editTextBatchNumber.setText(roast.getBatchNumber());
                editTextRoasterName.setText(roast.getRoasterName());
                editTextRoastDate.setText(roast.getDate());
            } else {
                Toast.makeText(getContext(), "Failed to load roast details.", Toast.LENGTH_SHORT).show();
            }
        }

        // Handle Update button click
        buttonUpdate.setOnClickListener(v -> {
            if (roast != null) {
                updateRoast();
            }
        });

        // Handle Delete button click
        buttonDelete.setOnClickListener(v -> {
            if (roast != null) {
                deleteRoast();
            }
        });

        // Handle Cancel button click (Go back)
        buttonCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void updateRoast() {
        // Get updated data from EditText fields
        String newBatchNumber = editTextBatchNumber.getText().toString();
        String newRoasterName = editTextRoasterName.getText().toString();
        String newRoastDate = editTextRoastDate.getText().toString();

        // Validate the input
        if (TextUtils.isEmpty(newBatchNumber) || TextUtils.isEmpty(newRoasterName) || TextUtils.isEmpty(newRoastDate)) {
            Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the roast object
        roast.setBatchNumber(newBatchNumber);
        roast.setRoasterName(newRoasterName);
        roast.setDate(newRoastDate);

        // Update the roast in the database
        boolean isUpdated = roastDao.updateRoast(roast);

        if (isUpdated) {
            Toast.makeText(getContext(), "Roast updated successfully!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack(); // Go back after update
        } else {
            Toast.makeText(getContext(), "Failed to update roast.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteRoast() {
        // Perform the delete operation
        boolean isDeleted = roastDao.deleteRoast(roast.getRoastID());

        if (isDeleted) {
            Toast.makeText(getContext(), "Roast deleted successfully!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        } else {
            Toast.makeText(getContext(), "Failed to delete roast.", Toast.LENGTH_SHORT).show();
        }
    }
}
