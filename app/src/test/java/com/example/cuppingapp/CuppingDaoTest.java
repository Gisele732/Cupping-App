package com.example.cuppingapp;

import org.junit.Test;
import static org.junit.Assert.*;

import android.content.Context;

import org.junit.Before;

public class CuppingDaoTest {

    private CuppingDao cuppingDao;

    @Before
    public void setUp() {
        cuppingDao = new CuppingDao((Context) null);  // Passing null, we are only testing the logic and not the database here
    }

    @Test
    public void testCalculateTotalScore() {
        int acidity = 4;
        int flavour = 5;
        int sweetness = 5;
        int bitterness = 3;
        int tactile = 4;
        int balance = 5;

        // Expected average of all attributes
        float expectedTotalScore = (acidity + flavour + sweetness + bitterness + tactile + balance) / 6.0f;

        float totalScore = cuppingDao.calculateTotalScore(acidity, flavour, sweetness, bitterness, tactile, balance);

        assertEquals(expectedTotalScore, totalScore, 0.01);  // Using a delta for floating-point comparison
    }

}
