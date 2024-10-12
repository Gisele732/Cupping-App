package com.example.cuppingapp;

import java.io.Serializable;

public class Coffee implements Serializable {
    private int coffeeID;  // Auto-incremented by the database
    private String name;
    private String origin;
    private String process;  // Optional field
    private String varietal; // Optional field

    // Constructor without coffeeID for new entries
    public Coffee(String name, String origin, String process, String varietal) {
        this.name = name;
        this.origin = origin;
        this.process = process;
        this.varietal = varietal;
    }

    // Constructor with coffeeID for when retrieving from the database
    public Coffee(int coffeeID, String name, String origin, String process, String varietal) {
        this.coffeeID = coffeeID;
        this.name = name;
        this.origin = origin;
        this.process = process;
        this.varietal = varietal;
    }

    // Getters and Setters
    public int getCoffeeID() {
        return coffeeID;
    }

    public void setCoffeeID(int coffeeID) {
        this.coffeeID = coffeeID;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getVarietal() {
        return varietal;
    }

    public void setVarietal(String varietal) {
        this.varietal = varietal;
    }

    public void setName(String newCoffeeName) {
        this.name = newCoffeeName;
    }

    public void setOrigin(String newCoffeeOrigin) {
        this.origin = newCoffeeOrigin;
    }
}
