package com.example.eventapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * UI Tests for a single Event Card item
 */
@RunWith(AndroidJUnit4.class)
public class ItemEventTest {

    @Test
    public void testEventCardUIElements() {
        // Launch a lightweight activity hosting the card for testing
        ActivityScenario<TestEventCardActivity> scenario =
                ActivityScenario.launch(TestEventCardActivity.class);

        // Banner section
        onView(withId(R.id.bannerContainer)).check(matches(isDisplayed()));
        onView(withId(R.id.imageBanner)).check(matches(isDisplayed()));

        // Category chip and favorite button
        onView(withId(R.id.chipCategory)).check(matches(isDisplayed()));
        onView(withId(R.id.btnFavorite)).check(matches(isDisplayed()));

        // Event title overlay
        onView(withId(R.id.textTitle)).check(matches(isDisplayed()))
                .check(matches(withText("Community Swim Night")));

        // Meta information: date/time/location
        onView(withId(R.id.textMeta)).check(matches(isDisplayed()))
                .check(matches(withText("Sat, Nov 2 • 5:00 PM • Downtown Community Centre")));

        // Status and capacity chips
        onView(withId(R.id.chipStatus)).check(matches(isDisplayed()))
                .check(matches(withText("Open")));
        onView(withId(R.id.chipCapacity)).check(matches(isDisplayed()))
                .check(matches(withText("Capacity: 20")));

        // Short description
        onView(withId(R.id.textShortDesc)).check(matches(isDisplayed()))
                .check(matches(withText("Family-friendly, all levels welcome.")));

        // Details button
        onView(withId(R.id.btnView)).check(matches(isDisplayed()))
                .perform(click()); // Check click action
    }
}
