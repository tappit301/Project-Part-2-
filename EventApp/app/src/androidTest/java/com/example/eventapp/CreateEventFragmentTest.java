package com.example.eventapp;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CreateEventFragmentTest {

    @Test
    public void testCreateEvent_showsToast() {
        // Launch the fragment in isolation
        FragmentScenario<CreateEventFragment> scenario =
                FragmentScenario.launchInContainer(CreateEventFragment.class);

        // Enter event title
        onView(withId(R.id.etEventTitle))
                .perform(typeText("Test Event"));

        // Click the Publish Event button
        onView(withId(R.id.btnPublishEvent))
                .perform(click());

        // Check if toast message is displayed
        onView(withText("Event created successfully"))  // Replace with your actual toast text
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
