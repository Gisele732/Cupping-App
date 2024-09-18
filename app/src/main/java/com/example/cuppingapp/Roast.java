package com.example.cuppingapp;

public class Roast {
    private int roastID;
    private int coffeeID;
    private String batchNumber;
    private String roasterName;
    private String date;

    public Roast(int roastID, int coffeeID, String batchNumber, String roasterName, String date) {
        this.roastID = roastID;
        this.coffeeID = coffeeID;
        this.batchNumber = batchNumber;
        this.roasterName = roasterName;
        this.date = date;
    }

    public String getBatchNumber() { return batchNumber; }

    public int getRoastID() { return roastID; }

    // Getters and Setters
}
