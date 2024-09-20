package com.example.cuppingapp;

import static com.example.cuppingapp.PasswordHasher.hashPassword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        String hashedPassword = hashPassword(password);

        values.put(DatabaseHelper.COLUMN_USER_NAME, username);
        values.put(DatabaseHelper.COLUMN_USER_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, hashedPassword); // Store hashed password
        return db.insert(DatabaseHelper.TABLE_USERS, null, values);
    }

    // Method to update user information (including password if provided)
    public boolean updateUser(String username, String email, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_NAME, username);
        values.put(DatabaseHelper.COLUMN_USER_EMAIL, email);

        // Only update the password if a new one is provided
        if (newPassword != null && !newPassword.isEmpty()) {
            String hashedNewPassword = hashPassword(newPassword);
            values.put(DatabaseHelper.COLUMN_USER_PASSWORD, hashedNewPassword);
        }

        int rows = db.update(DatabaseHelper.TABLE_USERS, values,
                DatabaseHelper.COLUMN_USER_NAME + " = ?",
                new String[]{username});
        Log.d("UserDao", "Rows affected: " + rows);
        return rows > 0;
    }


    // Check if the current password matches for the user
    public boolean checkUser(String userName, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String hashedInputPassword = hashPassword(password);
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_USERS +
                " WHERE " + DatabaseHelper.COLUMN_USER_NAME + "=? AND " +
                DatabaseHelper.COLUMN_USER_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{userName, hashedInputPassword});

        Log.d("checkUser", "Query Result Count: " + cursor.getCount()); // Log the result count

        boolean result = (cursor.getCount() > 0);
        cursor.close();
        return result;
    }

    // Get user data by ID
    public User getUserById(String userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, "id = ?", new String[]{userId}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            // Check if the columns exist
            int usernameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_NAME);
            int emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL);

            if (usernameIndex == -1 || emailIndex == -1) {
                // Log error and return null if column is not found
                Log.e("UserDao", "Column not found in the database");
                cursor.close();
                return null;
            }

            // Fetch values
            String username = cursor.getString(usernameIndex);
            String email = cursor.getString(emailIndex);
            cursor.close();

            return new User(userId, username, email);
        }
        return null;
    }

    // Get user data by username
    public User getUserByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_USERS + " WHERE " + DatabaseHelper.COLUMN_USER_NAME + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        if (cursor != null && cursor.moveToFirst()) {
            // Check if the columns exist
            int userIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID);
            int emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL);

            if (userIdIndex == -1 || emailIndex == -1) {
                // Log error and return null if column is not found
                Log.e("UserDao", "Column not found in the database");
                cursor.close();
                return null;
            }

            // Fetch values
            String userId = cursor.getString(userIdIndex);
            String email = cursor.getString(emailIndex);
            cursor.close();
            return new User(userId, username, email);
        }
        return null;
    }

    public boolean isUserExists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS,
                new String[]{DatabaseHelper.COLUMN_USER_NAME},
                DatabaseHelper.COLUMN_USER_NAME + " = ?",
                new String[]{username},
                null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public void insertDummyData() {
        insertUser("Anne-Lise", "annelise@mail.com", "1234");
    }
}

