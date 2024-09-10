package com.example.cuppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {

    private DatabaseHelper dbHelper;

    public UserDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Insert a new user
    public long insertUser(String username, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Hash the password before storing it
        String hashedPassword = PasswordHasher.hashPassword(password);

        values.put(DatabaseHelper.COLUMN_USER_NAME, username);
        values.put(DatabaseHelper.COLUMN_USER_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, hashedPassword); // Store hashed password
        return db.insert(DatabaseHelper.TABLE_USERS, null, values);
    }

    // Check user credentials for login
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Hash the entered password before checking it
        String hashedPassword = PasswordHasher.hashPassword(password);

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_USERS +
                " WHERE " + DatabaseHelper.COLUMN_USER_NAME + "=? AND " +
                DatabaseHelper.COLUMN_USER_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, hashedPassword});

        boolean result = (cursor.getCount() > 0);
        cursor.close();
        return result;
    }
}

