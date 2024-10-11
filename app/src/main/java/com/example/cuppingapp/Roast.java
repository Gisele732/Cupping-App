package com.example.cuppingapp;

import java.io.Serializable;

public class Roast implements Serializable {
    private int roastID;    // Auto-incremented by the database
    private int coffeeID;   // Foreign key to Coffee table
    private String batchNumber;
    private String roasterName;
    private String date;    // Assuming date is stored as a string

    // Constructor without roastID for new roasts
    public Roast(int coffeeID, String batchNumber, String roasterName, String date) {
        this.coffeeID = coffeeID;
        this.batchNumber = batchNumber;
        this.roasterName = roasterName;
        this.date = date;
    }

    // Constructor with roastID (for fetching from the database)
    public Roast(int roastID, int coffeeID, String batchNumber, String roasterName, String date) {
        this.roastID = roastID;
        this.coffeeID = coffeeID;
        this.batchNumber = batchNumber;
        this.roasterName = roasterName;
        this.date = date;
    }

    // Getters and Setters
    public int getRoastID() {
        return roastID;
    }

    public void setRoastID(int roastID) {
        this.roastID = roastID;
    }

    public int getCoffeeID() {
        return coffeeID;
    }

    public void setCoffeeID(int coffeeID) {
        this.coffeeID = coffeeID;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getRoasterName() {
        return roasterName;
    }

    public void setRoasterName(String roasterName) {
        this.roasterName = roasterName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
