package com.example.cuppingapp;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CuppingTest {

    private Cupping cupping;

    @Before
    public void setUp() {
        // Initialize a new cupping object for each test
        cupping = new Cupping(1, "2023-10-11", "Sample Coffee", 4.2F);
    }

    @Test
    public void testTotalScoreCalculation() {
        cupping.setAcidity(4);
        cupping.setFlavour(5);
        cupping.setSweetness(4);
        cupping.setBitterness(3);
        cupping.setTactile(4);
        cupping.setBalance(5);

        float expectedTotalScore = (4 + 5 + 4 + 3 + 4 + 5) / 6.0f;
        expectedTotalScore = Math.round(expectedTotalScore * 10) / 10.0f;
        assertEquals(expectedTotalScore, cupping.getTotalScore(), 0.01);
    }

    @Test
    public void testCuppingGettersSetters() {
        // Create a new Cupping object with the constructor that accepts int, String, String, float
        Cupping cupping = new Cupping(1, "Sample Coffee","2023-10-11", 4.0f);

        // Set other values
        cupping.setAcidity(4);
        cupping.setFlavour(5);
        cupping.setSweetness(3);
        cupping.setBitterness(2);
        cupping.setTactile(4);
        cupping.setBalance(5);

        // Check getters
        assertEquals("2023-10-11", cupping.getDate());
        assertEquals(4, cupping.getAcidity());
        assertEquals(5, cupping.getFlavour());
        assertEquals(3, cupping.getSweetness());
        assertEquals(2, cupping.getBitterness());
        assertEquals(4, cupping.getTactile());
        assertEquals(5, cupping.getBalance());
        assertEquals("Sample Coffee", cupping.getCoffeeName());
    }
}


