package com.example.cuppingapp;

import android.content.Intent;
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

public class CreateAccount extends AppCompatActivity {

    Button buttonCreateAccount;
    Button buttonBack;
    EditText usernameInput;
    EditText passwordInput;
    EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_account);

        // Create new user and insert into database
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        buttonCreateAccount.setOnClickListener(view -> {
            usernameInput = findViewById(R.id.Username);
            emailInput = findViewById(R.id.Email);
            passwordInput = findViewById(R.id.Password);

            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            String email = emailInput.getText().toString();

            UserDao userDao = new UserDao(this);
            long result = userDao.insertUser(username, email, password);

            if (result > 0) {
                Toast.makeText(CreateAccount.this, "Account created!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent);
                // Close CreateAccount so the user can't go back to it
                finish();
            } else {
                Toast.makeText(CreateAccount.this, "Failed to create account", Toast.LENGTH_SHORT).show();
            }
        });

        buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_account), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
