package com.example.cuppingapp;

public class Cupping {
    private String coffeeName;
    private int cuppingID;
    private int coffeeID;
    private int roastID;
    private String date;
    private int acidity;
    private int flavour;
    private int sweetness;
    private int bitterness;
    private int tactile;
    private int balance;
    private float totalScore;
    private String notes;

    public Cupping(int cuppingID, int coffeeID, int roastID, String date, int acidity, int flavour, int sweetness, int bitterness, int tactile, int balance, float totalScore, String notes, String coffeeName) {
        this.cuppingID = cuppingID;
        this.coffeeID = coffeeID;
        this.roastID = roastID;
        this.date = date;
        this.acidity = acidity;
        this.flavour = flavour;
        this.sweetness = sweetness;
        this.bitterness = bitterness;
        this.tactile = tactile;
        this.balance = balance;
        this.totalScore = totalScore;
        this.notes = notes;
        this.coffeeName = coffeeName;  // Set coffee name
    }

    // Add a constructor with only 4 arguments (for when you don't need all fields)
    public Cupping(int cuppingID, String coffeeName, String date, float totalScore) {
        this.cuppingID = cuppingID;
        this.coffeeName = coffeeName;
        this.date = date;
        this.totalScore = totalScore;
    }

    public String getDate() {
        return date;
    }

    public int getCoffeeID() {
        return coffeeID;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }
}
