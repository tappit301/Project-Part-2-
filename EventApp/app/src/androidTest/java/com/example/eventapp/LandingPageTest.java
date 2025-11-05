package com.example.eventapp;

import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class LandingPageTest {

    // 👇 Replace with your actual activity that uses this layout
    @Rule
    public ActivityTestRule<EventsActivity> activityRule =
            new ActivityTestRule<>(EventsActivity.class);

    // Test 1: Check if the main toolbar and elements are visible
    @Test
    public void testAppBarElementsDisplayed() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.AppName)).check(matches(withText("tappit")));
        onView(withId(R.id.btnExplore)).check(matches(isDisplayed()));
        onView(withId(R.id.btnNotifications)).check(matches(isDisplayed()));
        onView(withId(R.id.btnProfile)).check(matches(isDisplayed()));
    }

    // Test 2: Check if event header and toggle buttons are visible
    @Test
    public void testEventHeaderAndToggleDisplayed() {
        onView(withText("Events")).check(matches(isDisplayed()));
        onView(withId(R.id.toggleEventType)).check(matches(isDisplayed()));
        onView(withId(R.id.btnUpcoming)).check(matches(isDisplayed()));
        onView(withId(R.id.btnPast)).check(matches(isDisplayed()));
    }

    // Test 3: Verify Upcoming/Past toggle works (simple click)
    @Test
    public void testToggleButtonClicks() {
        onView(withId(R.id.btnUpcoming)).perform(click());
        onView(withId(R.id.btnPast)).perform(click());
        // If toggle logic changes UI, verify new state visibility
    }

    // Test 4: Verify Empty State layout is visible when no events
    @Test
    public void testEmptyStateVisible() {
        onView(withId(R.id.emptyStateLayout))
                .check(matches(isDisplayed()));

        onView(withText("No Events"))
                .check(matches(isDisplayed()));
        onView(withText("This calendar has no upcoming events."))
                .check(matches(isDisplayed()));
        onView(withId(R.id.btnAddEventEmpty))
                .check(matches(withText("+ Add Event")));
    }

    // Test 5: Verify clicking "+ Add Event" button works
    @Test
    public void testAddEventButtonClick() {
        onView(withId(R.id.btnAddEventEmpty)).perform(click());
        // Replace below with your navigation / Toast check
        // onView(withText("Open Create Event")).check(matches(isDisplayed()));
    }

    // Test 6: Verify RecyclerView hidden when empty state visible
    @Test
    public void testRecyclerViewIsHiddenInitially() {
        onView(withId(R.id.rvEvents)).check(matches(withEffectiveVisibility(Visibility.GONE)));
    }

    // Test 7: Click toolbar buttons (Explore, Notifications, Profile)
    @Test
    public void testToolbarButtonsClickable() {
        onView(withId(R.id.btnExplore)).perform(click());
        onView(withId(R.id.btnNotifications)).perform(click());
        onView(withId(R.id.btnProfile)).perform(click());
        // Replace with intents or Toast assertions as needed
    }

    // Test 8: Scroll behavior (NestedScrollView)
    @Test
    public void testScrollBehavior() {
        onView(withId(R.id.btnAddEventEmpty)).perform(swipeUp());
        onView(withId(R.id.AppName)).perform(swipeDown());
    }

    // Test 9: Visibility toggle simulation (switch from empty to list)
    @Test
    public void testRecyclerViewVisibilityChange() {
        // Simulate the case where RecyclerView becomes visible
        activityRule.getActivity().runOnUiThread(() -> {
            View recyclerView = activityRule.getActivity().findViewById(R.id.rvEvents);
            View emptyLayout = activityRule.getActivity().findViewById(R.id.emptyStateLayout);
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        });

        // Check updated visibility
        onView(withId(R.id.rvEvents)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.emptyStateLayout)).check(matches(withEffectiveVisibility(Visibility.GONE)));
    }

    // Test 10: Ensure toggle defaults to Upcoming (if required by logic)
    @Test
    public void testDefaultToggleSelection() {
        // Uncomment if default selection is expected
        // onView(allOf(withId(R.id.btnUpcoming), isChecked())).check(matches(isDisplayed()));
    }
}
