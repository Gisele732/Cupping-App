package com.example.cuppingapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class CuppingDetailFragment extends Fragment {

    private Cupping cupping;
    private CuppingDao cuppingDao;
    private EditText editTextDate, editTextNotes;
    private RatingBar ratingBarAcidity, ratingBarFlavour, ratingBarSweetness, ratingBarBitterness, ratingBarTactile, ratingBarBalance;
    private Button buttonUpdate, buttonCancel, buttonDelete;
    private Object calendar;

    public CuppingDetailFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cupping_detail2, container, false);

        // Initialize views
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextNotes = view.findViewById(R.id.editTextNotes);
        ratingBarAcidity = view.findViewById(R.id.ratingBarAcidity);
        ratingBarFlavour = view.findViewById(R.id.ratingBarFlavour);
        ratingBarSweetness = view.findViewById(R.id.ratingBarSweetness);
        ratingBarBitterness = view.findViewById(R.id.ratingBarBitterness);
        ratingBarTactile = view.findViewById(R.id.ratingBarTactile);
        ratingBarBalance = view.findViewById(R.id.ratingBarBalance);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        editTextDate = view.findViewById(R.id.editTextDate);

        cuppingDao = new CuppingDao(requireContext());

        // Get the selected cupping from the arguments
        if (getArguments() != null) {
            int cuppingID = getArguments().getInt("cuppingID");

            // Retrieve the full cupping object from the database
            cupping = cuppingDao.getCuppingById(cuppingID);

            // Ensure cupping data is loaded immediately
            if (cupping != null) {
                loadCuppingDetails(view);  // Load details right after retrieving the cupping object
            } else {
                Toast.makeText(getContext(), "Failed to load the cupping details.", Toast.LENGTH_SHORT).show();
            }
        }

        // Handle update button click
        buttonUpdate.setOnClickListener(v -> {
            if (cupping != null) {
                updateCupping();
            }
        });

        // Handle cancel button click
        buttonCancel.setOnClickListener(v -> {
            // Go back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Handle delete button click
        buttonDelete.setOnClickListener(v -> {
            if (cupping != null) {
                deleteCupping();
            }
        });

        // Set a click listener to show DatePickerDialog
        editTextDate.setOnClickListener(v -> showDatePicker());

        return view;
    }

    private void loadCuppingDetails(View view) {
        // Check if the cupping object has valid data
        if (cupping != null) {
            editTextDate.setText(cupping.getDate());
            editTextNotes.setText(cupping.getNotes());
            ratingBarAcidity.setRating(cupping.getAcidity());
            ratingBarFlavour.setRating(cupping.getFlavour());
            ratingBarSweetness.setRating(cupping.getSweetness());
            ratingBarBitterness.setRating(cupping.getBitterness());
            ratingBarTactile.setRating(cupping.getTactile());
            ratingBarBalance.setRating(cupping.getBalance());
            Log.d("CuppingDetailFragment", "Cupping Data: " + cupping.getDate() + ", " + cupping.getAcidity());
        } else {
            Log.e("CuppingDetailFragment", "Cupping object is null");
        }
    }

    private void updateCupping() {
        String newDate = editTextDate.getText().toString();
        String newNotes = editTextNotes.getText().toString();
        int newAcidity = (int) ratingBarAcidity.getRating();
        int newFlavour = (int) ratingBarFlavour.getRating();
        int newSweetness = (int) ratingBarSweetness.getRating();
        int newBitterness = (int) ratingBarBitterness.getRating();
        int newTactile = (int) ratingBarTactile.getRating();
        int newBalance = (int) ratingBarBalance.getRating();

        if (TextUtils.isEmpty(newDate)) {
            Toast.makeText(getContext(), "Please enter a valid date.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the cupping object
        cupping.setDate(newDate);
        cupping.setNotes(newNotes);
        cupping.setAcidity(newAcidity);
        cupping.setFlavour(newFlavour);
        cupping.setSweetness(newSweetness);
        cupping.setBitterness(newBitterness);
        cupping.setTactile(newTactile);
        cupping.setBalance(newBalance);

        // Calculate the new total score based on the updated attributes
        //Todo: totalScore is not calculated properly?
        float totalScore = (newAcidity + newFlavour + newSweetness + newBitterness + newTactile + newBalance) / 6.0f;
        cupping.setTotalScore(totalScore);

        // Update the cupping in the database
        boolean isUpdated = cuppingDao.updateCupping(cupping);

        if (isUpdated) {
            Toast.makeText(getContext(), "Cupping updated successfully!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack(); // Go back to the previous fragment
        } else {
            Toast.makeText(getContext(), "Failed to update cupping.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCupping() {
        // Call delete method in CuppingDao
        boolean isDeleted = cuppingDao.deleteCupping(cupping.getCuppingID());

        if (isDeleted) {
            Toast.makeText(getContext(), "Cupping deleted successfully!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();  // Go back after deletion
        } else {
            Toast.makeText(getContext(), "Failed to delete cupping.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker() {
        // Get current date to display in the DatePicker
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Set the selected date to the EditText
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    editTextDate.setText(date);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

}
