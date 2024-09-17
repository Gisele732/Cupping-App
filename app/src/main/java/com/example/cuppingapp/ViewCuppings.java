package com.example.cuppingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ViewCuppings extends AppCompatActivity {

    ListView listViewCuppings;
    List<String> cuppingList;
    ArrayAdapter<String> cuppingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cuppings);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewCuppings = findViewById(R.id.listViewCuppings);
        cuppingList = getCuppingsFromDatabase();

        cuppingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cuppingList);
        listViewCuppings.setAdapter(cuppingAdapter);

        listViewCuppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle clicking on a cupping
                Toast.makeText(ViewCuppings.this, "Clicked: " + cuppingList.get(position), Toast.LENGTH_SHORT).show();
                // You can navigate to a detailed view or edit/delete here
            }
        });
    }

    private List<String> getCuppingsFromDatabase() {
        // Simulate getting data from database
        List<String> cuppings = new ArrayList<>();
        cuppings.add("Cupping 1");
        cuppings.add("Cupping 2");
        cuppings.add("Cupping 3");
        return cuppings;
    }
}
