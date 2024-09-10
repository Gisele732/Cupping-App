package com.example.cuppingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "CuppingApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_COFFEES = "coffees";

    // Columns for the Users table
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    // Columns for the Coffees table (example)
    public static final String COLUMN_COFFEE_ID = "id";
    public static final String COLUMN_COFFEE_NAME = "name";
    public static final String COLUMN_COFFEE_ORIGIN = "origin";

    // SQL queries to create tables
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_EMAIL + " TEXT NOT NULL,"
            + COLUMN_USER_NAME + " TEXT NOT NULL, "
            + COLUMN_USER_PASSWORD + " TEXT NOT NULL"
            + ");";

    private static final String CREATE_TABLE_COFFEES = "CREATE TABLE " + TABLE_COFFEES + "("
            + COLUMN_COFFEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_COFFEE_NAME + " TEXT NOT NULL, "
            + COLUMN_COFFEE_ORIGIN + " TEXT NOT NULL"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_COFFEES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database version upgrades
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COFFEES);
        onCreate(db);
    }
}
