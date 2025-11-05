package com.example.eventapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * UI tests for LoginActivity without testing Toast messages.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    /** Checks that all main UI elements are displayed */
    @Test
    public void testUIElementsDisplayed() {
        onView(withId(R.id.editTextEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.btnSignIn)).check(matches(isDisplayed()));
        onView(withId(R.id.btnSignUpToggle)).check(matches(isDisplayed()));
    }

    /** Test typing into input fields */
    @Test
    public void testTextInputFields() {
        onView(withId(R.id.editTextEmail)).perform(replaceText("test@gmail.com"));
        onView(withId(R.id.editTextPassword)).perform(replaceText("123456"));
        closeSoftKeyboard();

        onView(withId(R.id.editTextEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextPassword)).check(matches(isDisplayed()));
    }

    /** Test clicking Sign In and Sign Up buttons */
    @Test
    public void testButtonClicks() {
        // Type credentials
        onView(withId(R.id.editTextEmail)).perform(replaceText("test@gmail.com"));
        onView(withId(R.id.editTextPassword)).perform(replaceText("123456"));
        closeSoftKeyboard();

        // Click Sign In
        onView(withId(R.id.btnSignIn)).perform(click());

        // Click Sign Up toggle
        onView(withId(R.id.btnSignUpToggle)).perform(click());
    }

    /** Test empty input behavior (fields can be empty) */
    @Test
    public void testEmptyInputClick() {
        // Clear any text
        onView(withId(R.id.editTextEmail)).perform(replaceText(""));
        onView(withId(R.id.editTextPassword)).perform(replaceText(""));
        closeSoftKeyboard();

        // Click Sign In with empty fields
        onView(withId(R.id.btnSignIn)).perform(click());
    }
}
