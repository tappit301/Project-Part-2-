package com.example.eventapp;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.button.MaterialButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class EventDetailsFragmentTest {

    private Bundle args;

    @Before
    public void setup() {
        args = new Bundle();
        args.putString("title", "Community Swim Night");
        args.putString("date", "Nov 2");
        args.putString("time", "5:00 PM");
        args.putString("location", "Downtown Pool");
        args.putString("desc", "Family-friendly, all levels welcome.");
        args.putString("category", "Sports");
        args.putInt("capacity", 20);
    }

    @Test
    public void testEventDetailsDisplayed() {
        FragmentScenario<EventDetailsFragment> scenario =
                FragmentScenario.launchInContainer(EventDetailsFragment.class, args);

        // Check views display correct text
        onView(withId(R.id.tvEventTitle)).check(matches(withText("Community Swim Night")));
        onView(withId(R.id.tvEventDate)).check(matches(withText("Nov 2")));
        onView(withId(R.id.tvEventTime)).check(matches(withText("5:00 PM")));
        onView(withId(R.id.tvEventLocation)).check(matches(withText("Downtown Pool")));
        onView(withId(R.id.tvEventDescription)).check(matches(withText("Family-friendly, all levels welcome.")));
        onView(withId(R.id.chipCategory)).check(matches(isDisplayed()));
        onView(withId(R.id.tvCapacityStatus)).check(matches(isDisplayed()));
    }

    @Test
    public void testJoinWaitingListButton() {
        FragmentScenario<EventDetailsFragment> scenario =
                FragmentScenario.launchInContainer(EventDetailsFragment.class, args);

        // Click "Join Waiting List" button
        onView(withId(R.id.btnJoinWaitingList)).perform(click());

        // Button text should update
        onView(withId(R.id.btnJoinWaitingList))
                .check(matches(withText("Added to Waiting List ✅")));
    }

    @Test
    public void testBackButtonNavigation() {
        // Create a mock NavController
        NavController mockNavController = Mockito.mock(NavController.class);

        FragmentScenario<EventDetailsFragment> scenario =
                FragmentScenario.launchInContainer(EventDetailsFragment.class, args);

        scenario.onFragment(fragment -> {
            // Set mock NavController
            Navigation.setViewNavController(fragment.getView(), mockNavController);

            // Click the back button
            View btnBack = fragment.getView().findViewById(R.id.btnBack);
            btnBack.performClick();

            // Verify popBackStack() is called
            verify(mockNavController).popBackStack();
        });
    }
}
