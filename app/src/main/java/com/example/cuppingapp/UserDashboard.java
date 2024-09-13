package com.example.cuppingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity {

    Button buttonLogout;
    Button buttonEditAccount;
    ListView previousCuppings;
    ArrayAdapter<String> arrayAdapter;
    private List<String> cuppingDescriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(view -> {
            Intent intent = new Intent(UserDashboard.this, MainActivity.class);
            startActivity(intent);
        });

        buttonEditAccount = findViewById(R.id.buttonEdit);
        buttonEditAccount.setOnClickListener(view -> {
            replaceFragment(new EditProfile());
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.landing_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize ListView for previous cuppings
        previousCuppings = findViewById(R.id.previousCuppings);

        // Load the last 5 cuppings from the database and update the ListView
        loadLastFiveCuppings();
    }

    private void loadLastFiveCuppings() {
        CuppingDao cuppingDao = new CuppingDao(this);  // Create an instance of CuppingDao
        List<Cupping> lastFiveCuppings = cuppingDao.getLastFiveCuppings();

        // Convert the Cupping objects to a descriptive string format for display
        cuppingDescriptions.clear();  // Clear previous entries
        for (Cupping cupping : lastFiveCuppings) {
            cuppingDescriptions.add(cupping.getDate() + " - " + cupping.getCoffeeName() + " (Score: " + cupping.getTotalScore() + ")");
        }

        // Update ListView with cupping descriptions
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cuppingDescriptions);
        previousCuppings.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("LandingPage", "onCreateOptionsMenu called");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cCupping:
                Toast.makeText(getApplicationContext(), "Start Create Cupping Activity", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.cForm:
                Toast.makeText(getApplicationContext(), "Start Create Form Activity", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.vCupping:
                Toast.makeText(getApplicationContext(), "Start View Cuppings Activity", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.vCoffee:
                Toast.makeText(getApplicationContext(), "Start View Coffees Activity", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.vRoast:
                Toast.makeText(getApplicationContext(), "Start View Roasts Activity", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Set custom animations for entering and exiting the fragment
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_right,  // Enter animation
                R.anim.slide_out_left,  // Exit animation
                R.anim.slide_in_left,   // Pop Enter animation (when coming back)
                R.anim.slide_out_right  // Pop Exit animation (when going back)
        );

        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.addToBackStack(null);  // Add this transaction to the back stack
        fragmentTransaction.commit();
    }
}
