package com.example.cuppingapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuppingapp.CuppingDao;

public class AddCuppingActivity extends AppCompatActivity {

    private EditText editTextCoffee, editTextRoast, editTextDate, notes;
    private RatingBar acidity, flavour, sweetness, bitterness, tactile, balance;
    private CuppingDao cuppingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cupping);

        editTextCoffee = findViewById(R.id.editTextCoffee);
        editTextRoast = findViewById(R.id.editTextRoast);
        editTextDate = findViewById(R.id.editTextDate);
        acidity = findViewById(R.id.ratingBarAcidity);
        flavour = findViewById(R.id.ratingBarFlavour);
        sweetness = findViewById(R.id.ratingBarSweetness);
        bitterness = findViewById(R.id.ratingBarBitterness);
        tactile = findViewById(R.id.ratingBarTactile);
        balance = findViewById(R.id.ratingBarBalance);
        notes = findViewById(R.id.editTextNotes);
        Button buttonSaveCupping = findViewById(R.id.buttonSaveCupping);

        cuppingDao = new CuppingDao(this);

        // Set onClick listener for saving cupping
        buttonSaveCupping.setOnClickListener(v -> saveCupping());
    }

    private void saveCupping() {
        try {
            // Parse the values from the input fields
            int coffeeID = Integer.parseInt(editTextCoffee.getText().toString());
            int roastID = Integer.parseInt(editTextRoast.getText().toString());
            String date = editTextDate.getText().toString();

            // Get the integer values from the RatingBars
            int acidityValue = (int) acidity.getRating();
            int flavourValue = (int) flavour.getRating();
            int sweetnessValue = (int) sweetness.getRating();
            int bitternessValue = (int) bitterness.getRating();
            int tactileValue = (int) tactile.getRating();
            int balanceValue = (int) balance.getRating();
            String notesText = notes.getText().toString();

            // Insert the new cupping into the database
            long result = cuppingDao.insertCupping(coffeeID, roastID, date, acidityValue, flavourValue, sweetnessValue, bitternessValue, tactileValue, balanceValue, notesText);

            if (result > 0) {
                Toast.makeText(this, "Cupping added successfully!", Toast.LENGTH_SHORT).show();
                finish();  // Close the activity and go back to the cupping list
            } else {
                Toast.makeText(this, "Failed to add cupping.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        }
    }
}
