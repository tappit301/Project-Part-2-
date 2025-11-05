package com.example.eventapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented UI Tests for HomeActivity
 * Verifies toolbar setup, buttons, and menu actions.
 */
@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule =
            new ActivityScenarioRule<>(HomeActivity.class);

    /** Checking if all key UI elements are displayed */

    @Test
    public void testUIComponentsVisible() {
        onView(withId(R.id.topAppBar)).check(matches(isDisplayed()));
        onView(withText("Fair Chance for Everyone")).check(matches(isDisplayed()));
        onView(withText("Why Choose Tappit?")).check(matches(isDisplayed()));
        onView(withId(R.id.button_get_started)).check(matches(isDisplayed()));
        onView(withId(R.id.button_view_events)).check(matches(isDisplayed()));
    }

    /** Checking that toolbar title is correctly set */

    @Test
    public void testToolbarTitleDisplayed() {
        onView(withText("Tappit")).check(matches(isDisplayed()));
    }

    /** Test that clicking both buttons doesn’t crash */

    @Test
    public void testButtonClicks() {
        onView(withId(R.id.button_get_started)).perform(click());
        onView(withId(R.id.button_view_events)).perform(click());
    }

    /** Verifying info boxes and text are visible */

    @Test
    public void testInfoBoxesVisible() {
        onView(withText("Fair Lottery Process")).check(matches(isDisplayed()));
        onView(withText("Waiting List Management")).check(matches(isDisplayed()));
    }

    /** Checking that the menu inflates and responds with Snackbars */

    @Test
    public void testMenuOptionsShowSnackbars() {
        Context context = ApplicationProvider.getApplicationContext();

        // Open menu and click "Settings"
        openActionBarOverflowOrOptionsMenu(context);
        onView(withText("Settings")).perform(click());
        onView(withText("Settings clicked"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(getDecorView()))))
                .check(matches(isDisplayed()));

        // Open menu and click "Profile"
        openActionBarOverflowOrOptionsMenu(context);
        onView(withText("Profile")).perform(click());
        onView(withText("Profile clicked"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(getDecorView()))))
                .check(matches(isDisplayed()));
    }

    /**
     * Helper method to safely get the current activity decor view for Snackbar matching.
     */
    private static android.view.View getDecorView() {
        final android.view.View[] decorView = new android.view.View[1];
        new ActivityScenarioRule<>(HomeActivity.class)
                .getScenario()
                .onActivity(activity -> decorView[0] = activity.getWindow().getDecorView());
        return decorView[0];
    }
}
