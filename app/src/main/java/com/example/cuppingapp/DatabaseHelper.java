package com.example.cuppingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "CuppingApp.db";
    private static final int DATABASE_VERSION = 4; // Increment if adding new tables or columns

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_COFFEES = "coffees";
    public static final String TABLE_ROASTS = "roasts";
    public static final String TABLE_CUPPINGS = "cuppings";

    // Columns for the Users table
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    // Columns for the Coffees table
    public static final String COLUMN_COFFEE_ID = "coffeeID";
    public static final String COLUMN_COFFEE_NAME = "name";
    public static final String COLUMN_COFFEE_ORIGIN = "origin";
    public static final String COLUMN_COFFEE_PROCESS = "process";
    public static final String COLUMN_COFFEE_VARIETAL = "varietal";

    // Columns for the Roasts table (updated with coffeeID as a foreign key)
    public static final String COLUMN_ROAST_ID = "roastID";
    public static final String COLUMN_ROAST_COFFEE_ID = "coffeeID"; // Foreign key to Coffees table
    public static final String COLUMN_ROAST_BATCH_NUMBER = "batchNumber";
    public static final String COLUMN_ROAST_ROASTER_NAME = "roasterName";
    public static final String COLUMN_ROAST_DATE = "date";

    // Columns for the Cuppings table
    public static final String COLUMN_CUPPING_ID = "cuppingID";
    public static final String COLUMN_CUPPING_COFFEE_ID = "coffeeID";
    public static final String COLUMN_CUPPING_ROAST_ID = "roastID";
    public static final String COLUMN_CUPPING_DATE = "date";
    public static final String COLUMN_CUPPING_ACIDITY = "acidity";
    public static final String COLUMN_CUPPING_FLAVOUR = "flavour";
    public static final String COLUMN_CUPPING_SWEETNESS = "sweetness";
    public static final String COLUMN_CUPPING_BITTERNESS = "bitterness";
    public static final String COLUMN_CUPPING_TACTILE = "tactile";
    public static final String COLUMN_CUPPING_BALANCE = "balance";
    public static final String COLUMN_CUPPING_TOTAL_SCORE = "totalScore";
    public static final String COLUMN_CUPPING_NOTES = "notes";

    // SQL query to create the Users table
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " TEXT NOT NULL, "
            + COLUMN_USER_EMAIL + " TEXT NOT NULL, "
            + COLUMN_USER_PASSWORD + " TEXT NOT NULL"
            + ");";

    // SQL query to create the Coffees table
    private static final String CREATE_TABLE_COFFEES = "CREATE TABLE " + TABLE_COFFEES + "("
            + COLUMN_COFFEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_COFFEE_NAME + " TEXT NOT NULL, "
            + COLUMN_COFFEE_ORIGIN + " TEXT NOT NULL, "
            + COLUMN_COFFEE_PROCESS + " TEXT NOT NULL, "
            + COLUMN_COFFEE_VARIETAL + " TEXT NOT NULL"
            + ");";

    // SQL query to create the Roasts table (updated with coffeeID as a foreign key)
    private static final String CREATE_TABLE_ROASTS = "CREATE TABLE " + TABLE_ROASTS + "("
            + COLUMN_ROAST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ROAST_COFFEE_ID + " INTEGER, "  // Foreign key to Coffees table
            + COLUMN_ROAST_BATCH_NUMBER + " VARCHAR(100) NOT NULL, "
            + COLUMN_ROAST_ROASTER_NAME + " VARCHAR(30) NOT NULL, "
            + COLUMN_ROAST_DATE + " TEXT NOT NULL, "  // Assuming date is stored as a string in the format YYYY-MM-DD
            + "FOREIGN KEY (" + COLUMN_ROAST_COFFEE_ID + ") REFERENCES " + TABLE_COFFEES + "(" + COLUMN_COFFEE_ID + ")"
            + ");";

    // SQL query to create the Cuppings table
    private static final String CREATE_TABLE_CUPPINGS = "CREATE TABLE " + TABLE_CUPPINGS + "("
            + COLUMN_CUPPING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CUPPING_COFFEE_ID + " INTEGER, "
            + COLUMN_CUPPING_ROAST_ID + " INTEGER, "
            + COLUMN_CUPPING_DATE + " TEXT, "
            + COLUMN_CUPPING_ACIDITY + " INTEGER, "
            + COLUMN_CUPPING_FLAVOUR + " INTEGER, "
            + COLUMN_CUPPING_SWEETNESS + " INTEGER, "
            + COLUMN_CUPPING_BITTERNESS + " INTEGER, "
            + COLUMN_CUPPING_TACTILE + " INTEGER, "
            + COLUMN_CUPPING_BALANCE + " INTEGER, "
            + COLUMN_CUPPING_TOTAL_SCORE + " REAL, "
            + COLUMN_CUPPING_NOTES + " VARCHAR(100), "
            + "FOREIGN KEY (" + COLUMN_CUPPING_COFFEE_ID + ") REFERENCES " + TABLE_COFFEES + "(" + COLUMN_COFFEE_ID + "), "
            + "FOREIGN KEY (" + COLUMN_CUPPING_ROAST_ID + ") REFERENCES " + TABLE_ROASTS + "(" + COLUMN_ROAST_ID + ")"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_COFFEES);
        db.execSQL(CREATE_TABLE_ROASTS);
        db.execSQL(CREATE_TABLE_CUPPINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COFFEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROASTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUPPINGS);

        // Recreate tables with new schema
        onCreate(db);
    }

}
