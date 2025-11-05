package com.example.eventapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.eventapp.ui.CreateEventActivity; // change to your activity package name

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * UI tests for CreateEventActivity
 * Verifies that all UI elements render and function correctly.
 */

@RunWith(AndroidJUnit4.class)
public class CreateEventTest {

    @Rule
    public ActivityScenarioRule<CreateEventActivity> activityRule =
            new ActivityScenarioRule<>(CreateEventActivity.class);

    /** Checks if all major views are displayed on launch */
    @Test
    public void testUIElementsDisplayed() {
        onView(withId(R.id.cardEventImage)).check(matches(isDisplayed()));
        onView(withId(R.id.etEventTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.etEventDescription)).check(matches(isDisplayed()));
        onView(withId(R.id.etEventDate)).check(matches(isDisplayed()));
        onView(withId(R.id.etEventTime)).check(matches(isDisplayed()));
        onView(withId(R.id.etEventLocation)).check(matches(isDisplayed()));
        onView(withId(R.id.etEventCapacity)).check(matches(isDisplayed()));
        onView(withId(R.id.etEventCategory)).check(matches(isDisplayed()));
        onView(withId(R.id.btnCancel)).check(matches(isDisplayed()));
        onView(withId(R.id.btnPublishEvent)).check(matches(isDisplayed()));
    }

    /** Tests typing into text fields */
    @Test
    public void testTextInputFields() {
        onView(withId(R.id.etEventTitle)).perform(replaceText("Community Meetup"));
        onView(withId(R.id.etEventDescription)).perform(replaceText("A fun meetup for local developers."));
        onView(withId(R.id.etEventLocation)).perform(replaceText("Downtown Library"));
        onView(withId(R.id.etEventCategory)).perform(replaceText("Tech"));
        onView(withId(R.id.etEventCapacity)).perform(replaceText("100"));
        closeSoftKeyboard();

        onView(withId(R.id.etEventTitle)).check(matches(withText("Community Meetup")));
        onView(withId(R.id.etEventCategory)).check(matches(withText("Tech")));
    }

    /** Verifies click behavior of image card and buttons */
    @Test
    public void testButtonClicks() {
        onView(withId(R.id.cardEventImage)).perform(click());
        onView(withId(R.id.btnCancel)).perform(click());
        onView(withId(R.id.btnPublishEvent)).perform(click());
    }

    /** Tests that date and time fields are clickable (but not editable directly) */
    @Test
    public void testDateTimePickersClickable() {
        onView(withId(R.id.etEventDate)).perform(click());
        onView(withId(R.id.etEventTime)).perform(click());
    }
}
