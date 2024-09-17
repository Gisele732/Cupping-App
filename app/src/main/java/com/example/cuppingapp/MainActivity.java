package com.example.cuppingapp;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize the Daos
        CoffeeDao coffeeDao = new CoffeeDao(this);
        RoastDao roastDao = new RoastDao(this);
        CuppingDao cuppingDao = new CuppingDao(this);

        // Insert dummy data only once
        if (shouldInsertDummyData()) {
            coffeeDao.insertDummyCoffees();
            roastDao.insertDummyRoasts();
            cuppingDao.insertDummyData();
        }

        // Initialize UI elements\
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        // TODO WHERE IS MY TOAST MESSAGE FOR UNSUCCESSFUL LOGIN??
        // Set a click listener for the login button
        buttonLogin.setOnClickListener(view -> {
            editTextUsername = findViewById(R.id.editTextUsername);
            editTextPassword = findViewById(R.id.editTextPassword);

            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            UserDao userDao = new UserDao(this);
            boolean isValid = userDao.checkUser(username, password);

            if (isValid) {
                // Store the username in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("current_user_name", username);
                editor.apply();  // Save changes

                // Continue to user dashboard
                Intent intent = new Intent(MainActivity.this, UserDashboard.class);
                startActivity(intent);
            } else
            {
                Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show();
            }

        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Method to ensure dummy data is inserted only once
    private boolean shouldInsertDummyData() {
        SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isDataInserted = preferences.getBoolean("dummy_data_inserted", false);

        if (!isDataInserted) {
            preferences.edit().putBoolean("dummy_data_inserted", true).apply();
            return true;
        }
        return false;
    }
}