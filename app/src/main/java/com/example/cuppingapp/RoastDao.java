package com.example.cuppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RoastDao {
    private DatabaseHelper dbHelper;

    public RoastDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Insert a new roast
    public long insertRoast(int coffeeID, String batchNumber, String roasterName, String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ROAST_COFFEE_ID, coffeeID);
        values.put(DatabaseHelper.COLUMN_ROAST_BATCH_NUMBER, batchNumber);
        values.put(DatabaseHelper.COLUMN_ROAST_ROASTER_NAME, roasterName);
        values.put(DatabaseHelper.COLUMN_ROAST_DATE, date);
        return db.insert(DatabaseHelper.TABLE_ROASTS, null, values);
    }

    // Insert 10 rows of dummy data for testing
    public void insertDummyRoasts() {
        String[] batchNumbers = {"Batch001", "Batch002", "Batch003", "Batch004", "Batch005", "Batch006", "Batch007", "Batch008", "Batch009", "Batch010"};
        String[] roasterNames = {"Roaster A", "Roaster B", "Roaster C", "Roaster D", "Roaster E", "Roaster F", "Roaster G", "Roaster H", "Roaster I", "Roaster J"};
        String[] roastDates = {"2024-01-01", "2024-01-02", "2024-01-03", "2024-01-04", "2024-01-05", "2024-01-06", "2024-01-07", "2024-01-08", "2024-01-09", "2024-01-10"};
        int[] coffeeIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Assume Coffee IDs are sequential

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i < batchNumbers.length; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ROAST_BATCH_NUMBER, batchNumbers[i]);
            values.put(DatabaseHelper.COLUMN_ROAST_ROASTER_NAME, roasterNames[i]);
            values.put(DatabaseHelper.COLUMN_ROAST_DATE, roastDates[i]);
            values.put(DatabaseHelper.COLUMN_ROAST_COFFEE_ID, coffeeIds[i]);
            db.insert(DatabaseHelper.TABLE_ROASTS, null, values);
        }
    }

    // Fetch all roasts
    public Cursor getAllRoasts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(DatabaseHelper.TABLE_ROASTS, null, null, null, null, null, null);
    }
}
