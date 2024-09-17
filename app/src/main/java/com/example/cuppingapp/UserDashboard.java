package com.example.cuppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UserDashboard extends AppCompatActivity {

    ImageView imgCuppings, imgCoffees, imgRoasts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dashboard);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        imgCuppings = findViewById(R.id.imgCuppings);
        imgCoffees = findViewById(R.id.imgCoffees);
        imgRoasts = findViewById(R.id.imgRoasts);

        imgCuppings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboard.this, ViewCuppings.class));
            }
        });

        imgCoffees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboard.this, ViewCoffees.class));
            }
        });

        imgRoasts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboard.this, ViewRoasts.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        switch (item.getItemId()) {
            case R.id.home:
                // Redirect to UserDashboard (already on UserDashboard in this case, so maybe refresh)
                Intent dashboardIntent = new Intent(this, UserDashboard.class);
                startActivity(dashboardIntent);
                return true;

            case R.id.profile:
                // Open the EditProfile fragment
                replaceFragment(new EditProfile());
                return true;

            case R.id.logout:
                // Redirect to MainActivity for logging out
                Intent logoutIntent = new Intent(this, MainActivity.class);
                startActivity(logoutIntent);
                finish(); // Optionally finish the current activity
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
