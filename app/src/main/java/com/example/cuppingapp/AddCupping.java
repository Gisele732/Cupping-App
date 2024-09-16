package com.example.cuppingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class AddCupping extends AppCompatActivity {

    private Spinner spinnerCoffee, spinnerRoast;
    private EditText editDate, editNotes;
    private SeekBar seekBarAcidity, seekBarFlavour, seekBarSweetness, seekBarBitterness, seekBarTactile, seekBarBalance;
    private Button buttonSaveCupping;
    private CuppingDao cuppingDao;
    private CoffeeDao coffeeDao;
    private RoastDao roastDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cupping);

        // Initialize UI components
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        spinnerRoast = findViewById(R.id.spinnerRoast);
        editDate = findViewById(R.id.editDate);
        editNotes = findViewById(R.id.editNotes);

        seekBarAcidity = findViewById(R.id.seekBarAcidity);
        seekBarFlavour = findViewById(R.id.seekBarFlavour);
        seekBarSweetness = findViewById(R.id.seekBarSweetness);
        seekBarBitterness = findViewById(R.id.seekBarBitterness);
        seekBarTactile = findViewById(R.id.seekBarTactile);
        seekBarBalance = findViewById(R.id.seekBarBalance);
        buttonSaveCupping = findViewById(R.id.buttonSaveCupping);

        // Initialize the DAOs
        cuppingDao = new CuppingDao(this);
        coffeeDao = new CoffeeDao(this);
        roastDao = new RoastDao(this);

        // Populate Coffee and Roast spinners
        loadCoffeeSpinner();
        loadRoastSpinner();

        // Handle DatePicker
        editDate.setOnClickListener(v -> showDatePicker());

        // Save button click listener
        buttonSaveCupping.setOnClickListener(v -> saveCupping());
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, (view, year1, month1, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            editDate.setText(selectedDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void loadCoffeeSpinner() {
        List<String> coffeeNames = coffeeDao.getAllCoffeeNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, coffeeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCoffee.setAdapter(adapter);
    }

    private void loadRoastSpinner() {
        List<String> roastNames = roastDao.getAllRoastNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roastNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoast.setAdapter(adapter);
    }

    private void saveCupping() {
        // Get selected values from the spinners and input fields
        int coffeeId = spinnerCoffee.getSelectedItemPosition() + 1;  // Assuming position matches coffee ID
        int roastId = spinnerRoast.getSelectedItemPosition() + 1;  // Assuming position matches roast ID
        String date = editDate.getText().toString();
        int acidity = seekBarAcidity.getProgress();
        int flavour = seekBarFlavour.getProgress();
        int sweetness = seekBarSweetness.getProgress();
        int bitterness = seekBarBitterness.getProgress();
        int tactile = seekBarTactile.getProgress();
        int balance = seekBarBalance.getProgress();
        String notes = editNotes.getText().toString();

        // Calculate total score
        float totalScore = (acidity + flavour + sweetness + bitterness + tactile + balance) / 6.0f;

        // Insert the new cupping record into the database
        long result = cuppingDao.insertCupping(coffeeId, roastId, date, acidity, flavour, sweetness, bitterness, tactile, balance, notes);

        if (result > 0) {
            Toast.makeText(this, "Cupping added successfully!", Toast.LENGTH_SHORT).show();

            // Fetch the selected coffee and roast names from the spinners
            String coffeeName = spinnerCoffee.getSelectedItem().toString();
            String roastName = spinnerRoast.getSelectedItem().toString();

            // Create an intent to pass data to the summary activity
            Intent intent = new Intent(AddCupping.this, CuppingSummary.class);
            intent.putExtra("date", date);
            intent.putExtra("coffeeName", coffeeName);
            intent.putExtra("roastName", roastName);
            intent.putExtra("acidity", acidity);
            intent.putExtra("flavour", flavour);
            intent.putExtra("sweetness", sweetness);
            intent.putExtra("bitterness", bitterness);
            intent.putExtra("tactile", tactile);
            intent.putExtra("balance", balance);
            intent.putExtra("totalScore", totalScore);
            intent.putExtra("notes", notes);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Failed to add cupping", Toast.LENGTH_SHORT).show();
        }
    }

}
