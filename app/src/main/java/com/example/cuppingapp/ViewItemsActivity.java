package com.example.cuppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class ViewItemsActivity extends AppCompatActivity {

    ListView listViewItems;
    private List<Cupping> cuppingList;
    private List<Coffee> coffeeList;
    private List<Roast> roastList;
    private ItemType itemType;
    private CuppingDao cuppingDao;
    private CoffeeDao coffeeDao;  // Assuming you have CoffeeDao
    private RoastDao roastDao;    // Assuming you have RoastDao

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the ItemType from the Intent
        itemType = (ItemType) getIntent().getSerializableExtra("ITEM_TYPE");

        // Set the layout according to the ItemType
        if (itemType == ItemType.COFFEE) {
            setContentView(R.layout.activity_view_coffees);
            coffeeDao = new CoffeeDao(this);
        } else if (itemType == ItemType.ROAST) {
            setContentView(R.layout.activity_view_roasts);
            roastDao = new RoastDao(this);
        } else {
            setContentView(R.layout.activity_view_cuppings);
            cuppingDao = new CuppingDao(this);
        }

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize ListView and Add Button
        listViewItems = findViewById(R.id.listView);
        Button buttonAddItem = findViewById(R.id.buttonAdd);

        // Set up Add button click listener to open the correct activity
        buttonAddItem.setOnClickListener(v -> handleAddButtonClick());

        // Load the list data based on the item type
        loadItemsList();

        // Set item click listener to open details
        listViewItems.setOnItemClickListener((parent, view, position, id) -> {
            if (itemType == ItemType.COFFEE) {
                Coffee selectedCoffee = coffeeList.get(position);  // Get the selected coffee

                // Log the coffeeID
                Log.d("ViewItemsActivity", "Selected Coffee ID: " + selectedCoffee.getCoffeeID());

                // Create the CoffeeDetailFragment and pass the coffeeID
                CoffeeDetailFragment fragment = new CoffeeDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("coffeeID", selectedCoffee.getCoffeeID());  // Pass coffeeID to fragment
                fragment.setArguments(bundle);

                // Replace the fragment to display coffee details
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else if (itemType == ItemType.ROAST) {
                Roast selectedRoast = (Roast) parent.getItemAtPosition(position);

                // Create the RoastDetailFragment and pass the Roast object
                RoastDetailFragment fragment = new RoastDetailFragment();

                // Pass the selected Roast object via Bundle
                Bundle bundle = new Bundle();
                bundle.putInt("roastID", selectedRoast.getRoastID());  // Pass roastID, not the full Roast object
                fragment.setArguments(bundle);

                // Replace the fragment container with the details fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                FragmentTransaction fragmentTransaction = getFragmentTransaction(position);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private @NonNull FragmentTransaction getFragmentTransaction(int position) {
        Cupping selectedCupping = cuppingList.get(position);

        // Create the fragment and pass the cuppingID instead of the whole object
        CuppingDetailFragment fragment = new CuppingDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cuppingID", selectedCupping.getCuppingID());  // Pass cuppingID
        fragment.setArguments(bundle);

        // Replace the fragment to display the cupping details
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        return fragmentTransaction;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload the list to ensure the view is updated
        loadItemsList();
    }

    private void loadItemsList() {
        if (itemType == ItemType.COFFEE) {
            coffeeList = coffeeDao.getAllCoffees();
            CoffeeAdapter coffeeAdapter = new CoffeeAdapter(this, coffeeList);
            listViewItems.setAdapter(coffeeAdapter);
        } else if (itemType == ItemType.ROAST) {
            roastList = roastDao.getAllRoasts();
            RoastAdapter roastAdapter = new RoastAdapter(this, roastList);
            listViewItems.setAdapter(roastAdapter);
        } else {
            cuppingList = cuppingDao.shortListCuppings();
            CuppingAdapter cuppingAdapter = new CuppingAdapter(this, cuppingList);
            listViewItems.setAdapter(cuppingAdapter);
        }
    }

    private void handleAddButtonClick() {
        // Navigate to the appropriate activity based on ItemType
        if (itemType == ItemType.COFFEE) {
            Intent intent = new Intent(ViewItemsActivity.this, AddCoffeeActivity.class);
            startActivity(intent);
        } else if (itemType == ItemType.ROAST) {
            Intent intent = new Intent(ViewItemsActivity.this, AddRoastActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(ViewItemsActivity.this, AddCuppingActivity.class);
            startActivity(intent);
        }
    }

    void openDetailFragment(Object item) {
        // Handle the fragment transaction based on item type
        Fragment detailFragment = null;

        if (itemType == ItemType.COFFEE && item instanceof Coffee) {
            CoffeeDetailFragment coffeeDetailFragment = new CoffeeDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedCoffee", (Coffee) item);
            coffeeDetailFragment.setArguments(bundle);
            detailFragment = coffeeDetailFragment;
        } else if (itemType == ItemType.ROAST && item instanceof Roast) {
            RoastDetailFragment roastDetailFragment = new RoastDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedRoast", (Roast) item);
            roastDetailFragment.setArguments(bundle);
            detailFragment = roastDetailFragment;
        } else if (item instanceof Cupping) {
            CuppingDetailFragment cuppingDetailFragment = new CuppingDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedCupping", (Cupping) item);
            cuppingDetailFragment.setArguments(bundle);
            detailFragment = cuppingDetailFragment;
        }

        // Begin fragment transaction
        if (detailFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framelayout, detailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(this, UserDashboard.class));
                return true;
            case R.id.profile:
                replaceFragment(new EditProfile());
                return true;
            case R.id.logout:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_right, R.anim.slide_out_left,
                R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
