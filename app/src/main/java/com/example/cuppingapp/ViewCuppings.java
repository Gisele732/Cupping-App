package com.example.cuppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class ViewCuppings extends AppCompatActivity {

    ListView listViewCuppings;
    CuppingAdapter cuppingAdapter;
    private List<Cupping> cuppingList;
    private List<String> cuppingDescriptions = new ArrayList<>();
    private CuppingDao cuppingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cuppings);
        cuppingDao = new CuppingDao(this);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewCuppings = findViewById(R.id.listViewCuppings);
        Button buttonAddCupping = findViewById(R.id.buttonAddCupping);

        // Handle Add Cupping button click
        buttonAddCupping.setOnClickListener(v -> {
            Intent intent = new Intent(ViewCuppings.this, AddCuppingActivity.class);
            startActivity(intent);
        });
        // Load the cuppings list from the database and update the ListView
        loadCuppingsList();

        // Set item click listener for the ListView
        listViewCuppings.setOnItemClickListener((parent, view, position, id) -> {
            Cupping selectedCupping = (Cupping) parent.getItemAtPosition(position);

            // Create the CuppingDetailFragment and pass the Cupping object
            CuppingDetailFragment fragment = new CuppingDetailFragment();

            // Retrieve full details of the cupping using getCuppingById
            Cupping fullCupping = cuppingDao.getCuppingById(selectedCupping.getCuppingID());

            // Pass the selectedCupping to the fragment via Bundle
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedCupping", fullCupping); // Ensure Cupping implements Serializable
            fragment.setArguments(bundle);

            // Replace the fragment container with the details fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framelayout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload the cupping list to ensure the view is updated
        loadCuppingsList();
    }

    private void loadCuppingsList() {
        CuppingDao cuppingDao = new CuppingDao(this);  // Create an instance of CuppingDao
        cuppingList = cuppingDao.shortListCuppings();  // Get the cupping data

        // Check if the list is empty and make sure ListView is visible
        if (cuppingList != null && !cuppingList.isEmpty()) {
            listViewCuppings.setVisibility(View.VISIBLE);
            // Update ListView with cupping descriptions
            cuppingAdapter = new CuppingAdapter(this, cuppingList);
            listViewCuppings.setAdapter(cuppingAdapter);
        } else {
            // Optionally, handle the case where the list is empty
            listViewCuppings.setVisibility(View.GONE); // Hide the ListView if no data
        }
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
                // Redirect to UserDashboard
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

    public void openEditCuppingFragment(Cupping cupping) {
        // Create the CuppingDetailFragment and pass the Cupping object
        CuppingDetailFragment fragment = new CuppingDetailFragment();

        // Pass the selected cupping to the fragment via Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedCupping", cupping); // Ensure Cupping implements Serializable
        fragment.setArguments(bundle);

        // Replace the fragment container with the CuppingDetailFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.framelayout, fragment); // framelayout is where the fragment will be displayed
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
