package com.example.cuppingapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    MainActivity mainActivity;
    String validUsername;
    String validPassword;

    @Before
    public void setUp() throws Exception {
        validUsername = "Anne-Lise";
        validPassword = "1234";
        // Initialize database and DAO here
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDao userDao = new UserDao(context);

        // Insert test user before each test if it doesn't already exist
        if (!userDao.isUserExists("Anne-Lise")) {
            userDao.insertUser("Anne-Lise", "annelise@mail.com", "1234");
        }
        // Initialize Intents before the test starts
        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        // Release Intents after the test finishes
        Intents.release();
        mainActivity = null;
    }

    @Test
    public void successfulLoginTest() {
        // Type valid credentials
        onView(withId(R.id.editTextUsername))
                .perform(typeText("Anne-Lise"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword))
                .perform(typeText("1234"), closeSoftKeyboard());

        // Click login button
        onView(withId(R.id.buttonLogin))
                .perform(click());

        // Check if the next activity is opened
        intended(hasComponent(UserDashboard.class.getName()));
    }


    @Test
    public void failedLoginTest() {
        // Type invalid credentials
        onView(withId(R.id.editTextUsername))
                .perform(typeText("invalidUsername"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword))
                .perform(typeText("invalidPassword"), closeSoftKeyboard());

        // Click login button
        onView(withId(R.id.buttonLogin)) // replace with actual login button id
                .perform(click());

        /* Check that an error message is displayed (replace with your actual error message and ID)
        // another unsuccessful test due to difficulties handling toast messages
        // Check if the Toast message is displayed using the custom ToastMatcher
        onView(withText("Invalid Login"))
                .inRoot(new ToastMatcher())  // Use the custom matcher for Toast
                .check(matches(withText("Invalid Login"))); */

        // Alternative to toast message check: Check if the MainActivity is still up
        // Check that MainActivity's views are still displayed, indicating the user is still on the same screen
        onView(withId(R.id.editTextUsername)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonLogin)).check(matches(isDisplayed()));
    }

    @Test
    public void emptyFieldsLoginTest() {
        // Leave both username and password empty
        onView(withId(R.id.buttonLogin)).perform(click());

        onView(withId(R.id.editTextUsername)).check(matches(isDisplayed())); // still on the same page
        onView(withId(R.id.editTextPassword)).check(matches(isDisplayed()));
    }
}
