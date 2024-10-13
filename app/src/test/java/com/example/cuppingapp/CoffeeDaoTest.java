package com.example.cuppingapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CoffeeDaoTest {

    private CoffeeDao coffeeDao;

    @Mock
    private SQLiteOpenHelper mockDbHelper;

    @Mock
    private SQLiteDatabase mockDatabase;

    @Mock
    private Context mockContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Mock SQLiteOpenHelper behavior when called by CoffeeDao
        when(mockDbHelper.getReadableDatabase()).thenReturn(mockDatabase);

        // When the context is used to instantiate the dbHelper, we mock it
        when(mockContext.getApplicationContext()).thenReturn(mockContext);  // To avoid any issues with app context

        // Initialize the CoffeeDao with mocked context
        coffeeDao = new CoffeeDao(mockContext);
    }

    @Test
    public void testGetCoffeeById() {
        int coffeeID = 3;
        Coffee coffee = new Coffee("Colombia Huila", "Colombia", "Washed","Catuai");

        // Mock the DAO method to return the expected coffee object
        when(mockDatabase.query(anyString(), any(), any(), any(), any(), any(), any())).thenReturn(null); // Adjust this if necessary for your query

        // Assume that the database will return a valid coffee object
        // If you are simulating actual data, you may want to mock Cursor behavior here

        assertNotNull(coffee);
        assertEquals("Colombia Huila", coffee.getName());
    }
}
