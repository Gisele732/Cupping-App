package com.example.cuppingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddCuppingActivity extends AppCompatActivity {

    private Spinner spinnerCoffees, spinnerRoasts;
    private EditText editTextDate, notes;
    private RatingBar acidity, flavour, sweetness, bitterness, tactile, balance;
    private CuppingDao cuppingDao;
    private CoffeeDao coffeeDao;
    private RoastDao roastDao;

    private List<Coffee> coffeeList;
    private List<Roast> roastList;
    private int selectedCoffeeId;
    private int selectedRoastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cupping);

        spinnerCoffees = findViewById(R.id.spinnerCoffees);
        spinnerRoasts = findViewById(R.id.spinnerRoasts);
        editTextDate = findViewById(R.id.editTextDate);
        acidity = findViewById(R.id.ratingBarAcidity);
        flavour = findViewById(R.id.ratingBarFlavour);
        sweetness = findViewById(R.id.ratingBarSweetness);
        bitterness = findViewById(R.id.ratingBarBitterness);
        tactile = findViewById(R.id.ratingBarTactile);
        balance = findViewById(R.id.ratingBarBalance);
        notes = findViewById(R.id.editTextNotes);
        Button buttonSaveCupping = findViewById(R.id.buttonSaveCupping);
        Button buttonCancelCupping = findViewById(R.id.buttonCancel);

        cuppingDao = new CuppingDao(this);
        coffeeDao = new CoffeeDao(this);
        roastDao = new RoastDao(this);

        loadCoffees();
        loadRoasts();

        buttonSaveCupping.setOnClickListener(v -> saveCupping());
        buttonCancelCupping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCuppingActivity.this, ViewCuppings.class);
                startActivity(intent);
            }
        });

        // Show DatePickerDialog when date EditText is clicked
        editTextDate.setOnClickListener(v -> showDatePickerDialog());
    }

    // Method to show the DatePickerDialog
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Set the selected date into the EditText
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    editTextDate.setText(date);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    // Load Coffees from the database and populate the Coffee Spinner
    private void loadCoffees() {
        coffeeList = (List<Coffee>) coffeeDao.getAllCoffees();  // Get all coffees from the CoffeeDao

        // Create a list of Coffee Names to display in the Spinner
        List<String> coffeeNames = new ArrayList<>();
        for (Coffee coffee : coffeeList) {
            coffeeNames.add(coffee.getCoffeeName());
        }

        // Set up the Spinner with Coffee Names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, coffeeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCoffees.setAdapter(adapter);

        // Handle Coffee selection
        spinnerCoffees.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCoffeeId = coffeeList.get(position).getCoffeeID();  // Store the selected CoffeeID
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    // Load Roasts from the database and populate the Roast Spinner
    private void loadRoasts() {
        roastList = (List<Roast>) roastDao.getAllRoasts();  // Get all roasts from the RoastDao

        // Create a list of Roast Batch Numbers to display in the Spinner
        List<String> roastBatchNumbers = new ArrayList<>();
        for (Roast roast : roastList) {
            roastBatchNumbers.add(roast.getBatchNumber());
        }

        // Set up the Spinner with Roast Batch Numbers
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roastBatchNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoasts.setAdapter(adapter);

        // Handle Roast selection
        spinnerRoasts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoastId = roastList.get(position).getRoastID();  // Store the selected RoastID
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void saveCupping() {
        try {
            String date = editTextDate.getText().toString();
            String notesText = notes.getText().toString();

            // Get RatingBar values
            int acidityValue = (int) acidity.getRating();
            int flavourValue = (int) flavour.getRating();
            int sweetnessValue = (int) sweetness.getRating();
            int bitternessValue = (int) bitterness.getRating();
            int tactileValue = (int) tactile.getRating();
            int balanceValue = (int) balance.getRating();

            // Insert the new cupping into the database using the selected CoffeeID and RoastID
            long result = cuppingDao.insertCupping(selectedCoffeeId, selectedRoastId, date, acidityValue, flavourValue, sweetnessValue, bitternessValue, tactileValue, balanceValue, notesText);

            if (result > 0) {
                Toast.makeText(this, "Cupping added successfully!", Toast.LENGTH_LONG).show();
                finish();  // Close the activity and go back to the cupping list
            } else {
                Toast.makeText(this, "Failed to add cupping.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Invalid input. Please check your entries.", Toast.LENGTH_SHORT).show();
        }
    }
}
