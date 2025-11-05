package com.example.eventapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CreateEventFragmentTest {

    @Before
    public void setUp() {
        // Launch the fragment in isolation
        FragmentScenario.launchInContainer(CreateEventFragment.class);
    }

    @Test
    public void testUIElementsDisplayed() {
        onView(withId(R.id.etEventTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.etEventDescription)).check(matches(isDisplayed()));
        onView(withId(R.id.etEventDate)).check(matches(isDisplayed()));
        onView(withId(R.id.etEventTime)).check(matches(isDisplayed()));
        onView(withId(R.id.btnPublishEvent)).check(matches(isDisplayed()));
        onView(withId(R.id.btnCancel)).check(matches(isDisplayed()));
    }

    @Test
    public void testUserInputFields() {
        onView(withId(R.id.etEventTitle)).perform(replaceText("Yoga Class"));
        onView(withId(R.id.etEventDescription)).perform(replaceText("Morning yoga for all levels"));
        onView(withId(R.id.etEventLocation)).perform(replaceText("Community Park"));
        onView(withId(R.id.etEventCategory)).perform(replaceText("Health"));
        onView(withId(R.id.etEventCapacity)).perform(replaceText("25"));
        onView(withId(R.id.etEventTitle)).check(matches(withText("Yoga Class")));
    }

    @Test
    public void testDateTimePickersClickable() {
        onView(withId(R.id.etEventDate)).perform(click());
        onView(withId(R.id.etEventTime)).perform(click());
    }

    @Test
    public void testButtonClicks() {
        onView(withId(R.id.btnPublishEvent)).perform(click());
        onView(withId(R.id.btnCancel)).perform(click());
    }
}
