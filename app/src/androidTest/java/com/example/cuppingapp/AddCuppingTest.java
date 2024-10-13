package com.example.cuppingapp;

import static androidx.test.InstrumentationRegistry.getContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

public class AddCuppingTest {

    private CuppingDao cuppingDao;

    @Before
    public void setUp() {
        // Get the context for the test
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // Initialize the CuppingDao with the context
        cuppingDao = new CuppingDao(context);
    }

    @Test
    public void addCuppingTest() {
        // Launch the activity manually
        ActivityScenario<AddCuppingActivity> scenario = ActivityScenario.launch(AddCuppingActivity.class);

        // Example test using getCuppingByDate
        String testDate = "2024-09-13";

        // Fill out the form
        // Select a coffee from the Coffee Spinner
        onView(withId(R.id.spinnerCoffees)) // Replace with the actual Spinner ID for coffee
                .perform(click()); // Open the spinner
        onView(withText("Colombia Huila"))
                .perform(click()); // Select the coffee

        // Select a roast from the Roast Spinner
        onView(withId(R.id.spinnerRoasts)) // Replace with the actual Spinner ID for roast
                .perform(click()); // Open the spinner
        onView(withText("Batch003"))
                .perform(click()); // Select the roast

        onView(withId(R.id.editTextDate))  // Set a date
                .perform(ViewActions.typeText(testDate), ViewActions.closeSoftKeyboard());
        // Set ratings for the attributes
        onView(withId(R.id.ratingBarAcidity)).perform(ViewActions.click());
        onView(withId(R.id.ratingBarFlavour)).perform(ViewActions.click());
        onView(withId(R.id.ratingBarSweetness)).perform(ViewActions.click());
        onView(withId(R.id.ratingBarBitterness)).perform(ViewActions.swipeUp(), ViewActions.click());
        onView(withId(R.id.ratingBarTactile)).perform(ViewActions.swipeUp(), ViewActions.click());
        onView(withId(R.id.ratingBarBalance)).perform(ViewActions.swipeUp(), ViewActions.click());

        // Save the cupping
        onView(withId(R.id.buttonSaveCupping)).perform(click());

        /* Code for toast check that I couldn't make work.
        // Check if the Toast message is displayed using the custom ToastMatcher
        onView(withText("Cupping added successfully!"))
            .inRoot(new ToastMatcher())  // Use the custom matcher for Toast
            .check(matches(withText("Cupping added successfully!"))); */

        // Fetch the cupping by date
        Cupping cupping = cuppingDao.getCuppingByDate(testDate);

        // Check if the cupping is not null and assert your conditions
        assertNotNull("Cupping should not be null", cupping);
        assertEquals(testDate, cupping.getDate());
    }

    @Test
    public void testCancelButton() {
        ActivityScenario<AddCuppingActivity> scenario = ActivityScenario.launch(AddCuppingActivity.class);

        // Fill some fields
        onView(withId(R.id.editTextDate)).perform(typeText("2024-09-13"),
                ViewActions.closeSoftKeyboard());

        /* struggling with this test as it won't scroll down to the cancel button, even with
        an extra swipe up step added */
        // Scroll to the Cancel button and then click it
        onView(withId(R.id.ratingBarBalance)).perform(ViewActions.swipeUp());

        onView(withId(R.id.buttonCancel))
                .perform(ViewActions.swipeUp(),
                        ViewActions.click());

        // Verify that we're back on the ViewItemsActivity activity
        intended(hasComponent(ViewItemsActivity.class.getName()));

        // Optional: Verify that no cupping was saved
        assertNull("Cupping should not be saved", cuppingDao.getCuppingByDate("2024-09-13"));
    }
}
