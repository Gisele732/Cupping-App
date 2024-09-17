package com.example.cuppingapp;

import java.io.Serializable;

public class Cupping implements Serializable {
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

    @Override
    public String toString() {
        return date + " - " + coffeeName + " (Score: " + totalScore + ")";
    }


    public String getDate() {
        return date;
    }

    public int getCoffeeID() {
        return coffeeID;
    }

    public int getCuppingID() {
        return cuppingID;
    }

    public int getRoastID() { return roastID; }

    public float getTotalScore() {
        return totalScore;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public int getAcidity() { return acidity;}

    public int getFlavour() { return flavour;}

    public int getSweetness() { return sweetness;}

    public int getBitterness() { return bitterness;}

    public int getTactile() { return tactile;}

    public int getBalance() { return balance;}

    public String getNotes() { return notes;}

    public void setAcidity(int i) {
        this.acidity = i;
    }
    public void setFlavour(int i) {
        this.flavour = i;
    }
    public void setBitterness(int i) {
        this.bitterness = i;
    }
    public void setSweetness(int i) {
        this.sweetness = i;
    }
    public void setTactile(int i) {
        this.tactile = i;
    }
    public void setBalance(int i) {
        this.balance = i;
    }
    public void setNotes(String i) {
        this.notes = i;
    }
    public void setDate(String newDate) { this.date = newDate; }
    public void setTotalScore(float totalScore) { this.totalScore = totalScore; }
}
