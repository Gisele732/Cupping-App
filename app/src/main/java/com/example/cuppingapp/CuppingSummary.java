package com.example.cuppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CuppingSummary extends AppCompatActivity {

    private TextView summaryCoffee;
    private TextView summaryRoast;
    private TextView summaryAcidity;
    private TextView summaryFlavour;
    private TextView summarySweetness;
    private TextView summaryBitterness;
    private TextView summaryTactile;
    private TextView summaryBalance;
    private TextView summaryTotalScore;
    private TextView summaryNotes;
    private Button buttonAddAnother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupping_summary);

        // Initialize UI components
        TextView summaryDate = findViewById(R.id.summaryDate);
        summaryCoffee = findViewById(R.id.summaryCoffee);
        summaryRoast = findViewById(R.id.summaryRoast);
        summaryAcidity = findViewById(R.id.summaryAcidity);
        summaryFlavour = findViewById(R.id.summaryFlavour);
        summarySweetness = findViewById(R.id.summarySweetness);
        summaryBitterness = findViewById(R.id.summaryBitterness);
        summaryTactile = findViewById(R.id.summaryTactile);
        summaryBalance = findViewById(R.id.summaryBalance);
        summaryTotalScore = findViewById(R.id.summaryTotalScore);
        summaryNotes = findViewById(R.id.summaryNotes);
        buttonAddAnother = findViewById(R.id.buttonAddAnother);

        // Get data passed from the previous activity
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String coffeeName = intent.getStringExtra("coffeeName");
        String roastName = intent.getStringExtra("roastName");
        int acidity = intent.getIntExtra("acidity", 0);
        int flavour = intent.getIntExtra("flavour", 0);
        int sweetness = intent.getIntExtra("sweetness", 0);
        int bitterness = intent.getIntExtra("bitterness", 0);
        int tactile = intent.getIntExtra("tactile", 0);
        int balance = intent.getIntExtra("balance", 0);
        float totalScore = intent.getFloatExtra("totalScore", 0);
        String notes = intent.getStringExtra("notes");

        // Set the text views with the data
        summaryDate.setText("Date: " + date);
        summaryCoffee.setText("Coffee: " + coffeeName);
        summaryRoast.setText("Roast: " + roastName);
        summaryAcidity.setText("Acidity: " + acidity);
        summaryFlavour.setText("Flavour: " + flavour);
        summarySweetness.setText("Sweetness: " + sweetness);
        summaryBitterness.setText("Bitterness: " + bitterness);
        summaryTactile.setText("Tactile: " + tactile);
        summaryBalance.setText("Balance: " + balance);
        summaryTotalScore.setText("Total Score: " + totalScore);
        summaryNotes.setText("Notes: " + notes);

        // Done button listener
        buttonAddAnother.setOnClickListener(v -> finish());  // Go back to the previous screen

        Button buttonBackToDashboard = findViewById(R.id.buttonBackToDashboard);
        buttonBackToDashboard.setOnClickListener(view -> {
            // Navigate back to the UserDashboard
            Intent intent2 = new Intent(CuppingSummary.this, UserDashboard.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
            finish(); // Finish the current activity
        });

    }
}
