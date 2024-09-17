package com.example.cuppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CuppingDao {
    private DatabaseHelper dbHelper;

    public CuppingDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Insert a new cupping
    public long insertCupping(int coffeeID, int roastID, String date, int acidity, int flavour, int sweetness, int bitterness, int tactile, int balance, String notes) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Calculate the totalScore by averaging the attributes
        float totalScore = (acidity + flavour + sweetness + bitterness + tactile + balance) / 6;

        values.put(DatabaseHelper.COLUMN_CUPPING_COFFEE_ID, coffeeID);
        values.put(DatabaseHelper.COLUMN_CUPPING_ROAST_ID, roastID);
        values.put(DatabaseHelper.COLUMN_CUPPING_DATE, date);
        values.put(DatabaseHelper.COLUMN_CUPPING_ACIDITY, acidity);
        values.put(DatabaseHelper.COLUMN_CUPPING_FLAVOUR, flavour);
        values.put(DatabaseHelper.COLUMN_CUPPING_SWEETNESS, sweetness);
        values.put(DatabaseHelper.COLUMN_CUPPING_BITTERNESS, bitterness);
        values.put(DatabaseHelper.COLUMN_CUPPING_TACTILE, tactile);
        values.put(DatabaseHelper.COLUMN_CUPPING_BALANCE, balance);
        values.put(DatabaseHelper.COLUMN_CUPPING_TOTAL_SCORE, totalScore);  // Store the calculated totalScore
        values.put(DatabaseHelper.COLUMN_CUPPING_NOTES, notes);

        return db.insert(DatabaseHelper.TABLE_CUPPINGS, null, values);
    }

    // Method to insert dummy data
    public void insertDummyData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Dummy data for testing
        for (int i = 1; i <= 10; i++) {
            insertCupping(i, i, "2024-09-13",
                    (int)(Math.random() * 10),  // Random Acidity score (0-9)
                    (int)(Math.random() * 10),  // Random Flavour score (0-9)
                    (int)(Math.random() * 10),  // Random Sweetness score (0-9)
                    (int)(Math.random() * 10),  // Random Bitterness score (0-9)
                    (int)(Math.random() * 10),  // Random Tactile score (0-9)
                    (int)(Math.random() * 10),  // Random Balance score (0-9)
                     "Cupping Test Notes " + i); // Test Notes
        }
    }

    // Fetch all cuppings
    public List<Cupping> getAllCuppings() {
        List<Cupping> cuppings = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT c.cuppingID, c.date, c.totalScore, cf.name AS coffeeName FROM cuppings c JOIN coffees cf ON c.coffeeID = cf.coffeeID ORDER BY c.date DESC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int cuppingID = cursor.getInt(cursor.getColumnIndexOrThrow("cuppingID"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                float totalScore = cursor.getFloat(cursor.getColumnIndexOrThrow("totalScore"));
                String coffeeName = cursor.getString(cursor.getColumnIndexOrThrow("coffeeName"));

                Cupping cupping = new Cupping(cuppingID, coffeeName, date, totalScore);
                cuppings.add(cupping);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cuppings;
    }

    public Cupping getCuppingById(int cuppingID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("cuppings", null, "cuppingID = ?", new String[]{String.valueOf(cuppingID)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Get cupping details
            int coffeeID = cursor.getInt(cursor.getColumnIndexOrThrow("coffeeID"));
            int roastID = cursor.getInt(cursor.getColumnIndexOrThrow("roastID"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            int acidity = cursor.getInt(cursor.getColumnIndexOrThrow("acidity"));
            int flavour = cursor.getInt(cursor.getColumnIndexOrThrow("flavour"));
            int sweetness = cursor.getInt(cursor.getColumnIndexOrThrow("sweetness"));
            int bitterness = cursor.getInt(cursor.getColumnIndexOrThrow("bitterness"));
            int tactile = cursor.getInt(cursor.getColumnIndexOrThrow("tactile"));
            int balance = cursor.getInt(cursor.getColumnIndexOrThrow("balance"));
            float totalScore = cursor.getFloat((cursor.getColumnIndexOrThrow("totalScore")));
            String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
            String coffeeName = cursor.getString(cursor.getColumnIndexOrThrow("coffeeName"));

            cursor.close();
            return new Cupping(cuppingID, coffeeID, roastID, date, acidity, flavour, sweetness, bitterness, tactile, balance, totalScore, notes, coffeeName);
        }
        return null;
    }

    public List<Cupping> getLastFiveCuppings() {
        List<Cupping> cuppingList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Correct SQL join query to fetch coffeeName from the Coffees table
        String query = "SELECT c.cuppingID, c.date, c.totalScore, cf.name AS coffeeName " +
                "FROM " + DatabaseHelper.TABLE_CUPPINGS + " c " +
                "JOIN " + DatabaseHelper.TABLE_COFFEES + " cf ON c.coffeeID = cf.coffeeID " +
                "ORDER BY c.date DESC LIMIT 5";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int cuppingID = cursor.getInt(cursor.getColumnIndexOrThrow("cuppingID"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                float totalScore = cursor.getFloat(cursor.getColumnIndexOrThrow("totalScore"));
                String coffeeName = cursor.getString(cursor.getColumnIndexOrThrow("coffeeName"));

                Cupping cupping = new Cupping(cuppingID, coffeeName, date, totalScore);
                cuppingList.add(cupping);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cuppingList;
    }

    public boolean updateCupping(Cupping cupping) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Set the updated values
        values.put(DatabaseHelper.COLUMN_COFFEE_ID, cupping.getCoffeeID());
        values.put(DatabaseHelper.COLUMN_ROAST_ID, cupping.getRoastID());
        values.put(DatabaseHelper.COLUMN_CUPPING_DATE, cupping.getDate());
        values.put(DatabaseHelper.COLUMN_CUPPING_ACIDITY, cupping.getAcidity());
        values.put(DatabaseHelper.COLUMN_CUPPING_FLAVOUR, cupping.getFlavour());
        values.put(DatabaseHelper.COLUMN_CUPPING_SWEETNESS, cupping.getSweetness());
        values.put(DatabaseHelper.COLUMN_CUPPING_BITTERNESS, cupping.getBitterness());
        values.put(DatabaseHelper.COLUMN_CUPPING_TACTILE, cupping.getTactile());
        values.put(DatabaseHelper.COLUMN_CUPPING_BALANCE, cupping.getBalance());
        values.put(DatabaseHelper.COLUMN_CUPPING_TOTAL_SCORE, cupping.getTotalScore());
        values.put(DatabaseHelper.COLUMN_CUPPING_NOTES, cupping.getNotes());

        // Update the cupping in the database, targeting the row by cuppingID
        int rowsAffected = db.update(
                DatabaseHelper.TABLE_CUPPINGS,
                values,
                DatabaseHelper.COLUMN_CUPPING_ID + " = ?",
                new String[]{String.valueOf(cupping.getCuppingID())}
        );

        // Return true if the update was successful
        return rowsAffected > 0;
    }



}
