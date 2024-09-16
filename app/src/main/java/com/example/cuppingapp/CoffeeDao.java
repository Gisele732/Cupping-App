package com.example.cuppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CoffeeDao {
    private DatabaseHelper dbHelper;

    public CoffeeDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Insert a new coffee
    public long insertCoffee(String name, String origin, String process, String varietal) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_COFFEE_NAME, name);
        values.put(DatabaseHelper.COLUMN_COFFEE_ORIGIN, origin);
        values.put(DatabaseHelper.COLUMN_COFFEE_PROCESS, process);
        values.put(DatabaseHelper.COLUMN_COFFEE_VARIETAL, varietal);
        return db.insert(DatabaseHelper.TABLE_COFFEES, null, values);
    }

    // Insert 10 rows of dummy data for testing
    public void insertDummyCoffees() {
        String[] coffeeNames = {"Peru El Chaupe", "Ethiopia Yirgacheffe", "Colombia Huila", "Brazil Santos", "Kenya AA",
                "Sumatra Mandheling", "Guatemala Antigua", "Costa Rica Tarrazu", "Panama Geisha", "Mexico Chiapas"};
        String[] origins = {"Peru", "Ethiopia", "Colombia", "Brazil", "Kenya", "Indonesia", "Guatemala", "Costa Rica", "Panama", "Mexico"};
        String[] processes = {"Washed", "Natural", "Honey", "Washed", "Natural", "Wet Hulled", "Washed", "Washed", "Washed", "Washed"};
        String[] varietals = {"Bourbon", "Heirloom", "Caturra", "Bourbon", "SL28", "Typica", "Bourbon", "Caturra", "Geisha", "Bourbon"};

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i < coffeeNames.length; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_COFFEE_NAME, coffeeNames[i]);
            values.put(DatabaseHelper.COLUMN_COFFEE_ORIGIN, origins[i]);
            values.put(DatabaseHelper.COLUMN_COFFEE_PROCESS, processes[i]);
            values.put(DatabaseHelper.COLUMN_COFFEE_VARIETAL, varietals[i]);
            db.insert(DatabaseHelper.TABLE_COFFEES, null, values);
        }
    }

    // Fetch all coffees
    public Cursor getAllCoffees() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(DatabaseHelper.TABLE_COFFEES, null, null, null, null, null, null);
    }

    public List<String> getAllCoffeeNames() {
        List<String> coffeeNames = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_COFFEES, new String[]{"name"}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                coffeeNames.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return coffeeNames;
    }

}
