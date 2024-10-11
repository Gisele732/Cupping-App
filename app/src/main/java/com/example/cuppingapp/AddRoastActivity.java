package com.example.cuppingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddRoastActivity extends AppCompatActivity {

    private EditText editTextRoastID, editTextCoffeeID, editTextBatchNumber, editTextRoasterName, editTextRoastDate;
    private RoastDao roastDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roast);

        roastDao = new RoastDao(this);

        // Initialize UI elements
        editTextRoastID = findViewById(R.id.editTextRoastID);
        editTextCoffeeID = findViewById(R.id.editTextCoffeeID);
        editTextBatchNumber = findViewById(R.id.editTextBatchNumber);
        editTextRoasterName = findViewById(R.id.editTextRoasterName);
        editTextRoastDate = findViewById(R.id.editTextRoastDate);
        Button buttonAddRoast = findViewById(R.id.buttonAddRoast);

        // Set onClickListener for the button
        buttonAddRoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoast();
            }
        });
    }

    private void saveRoast() {
        // Get the input values
        String roastIdString = editTextRoastID.getText().toString();
        int roastID = Integer.parseInt(roastIdString);
        String coffeeIDString = editTextCoffeeID.getText().toString();
        int coffeeID = Integer.parseInt(coffeeIDString);
        String batchNumber = editTextBatchNumber.getText().toString().trim();
        String roasterName = editTextRoasterName.getText().toString().trim();
        String date = editTextRoastDate.getText().toString().trim();

        // Validate the input
        if (roasterName.isEmpty() || date.isEmpty() || batchNumber.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Roast object and save it using RoastDao
        Roast newRoast = new Roast(roastID, coffeeID, batchNumber, roasterName, date);
        roastDao.addRoast(newRoast);

        // Provide feedback and close the activity
        Toast.makeText(this, "Roast added!", Toast.LENGTH_SHORT).show();
        finish();  // Close the activity and return to the previous screen
    }
}
