package com.example.cuppingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RoastDetailFragment extends Fragment {

    private Roast selectedRoast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roast_detail, container, false);

        // Get the selected roast from the arguments
        if (getArguments() != null) {
            selectedRoast = (Roast) getArguments().getSerializable("selectedRoast");
        }

        // Bind roast details to UI elements
        if (selectedRoast != null) {
            TextView roastIDTextView = view.findViewById(R.id.textViewRoastID);
            roastIDTextView.setText(selectedRoast.getRoastID());

            // Add more views to show roast details if needed
        }

        return view;
    }
}
