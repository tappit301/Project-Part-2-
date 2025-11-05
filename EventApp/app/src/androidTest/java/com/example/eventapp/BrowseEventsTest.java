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

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * UI Tests for BrowseEventsActivity
 * Verifies search, chips, RecyclerView, empty state, and FAB.
 */

@RunWith(AndroidJUnit4.class)
public class BrowseEventsTest {

    @Rule
    public ActivityScenarioRule<BrowseEvents> activityRule =
            new ActivityScenarioRule<>(BrowseEvents.class);

    /** Toolbar and hero section */

    @Test
    public void testToolbarAndHeroVisible() {
        onView(withId(R.id.toolbarBrowse)).check(matches(isDisplayed()));
        onView(withId(R.id.textTitle)).check(matches(isDisplayed()));
        onView(withText("Browse Events")).check(matches(isDisplayed()));

        onView(withId(R.id.textHeroTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.textHeroSub)).check(matches(isDisplayed()));
    }

    /** Search input works */

    @Test
    public void testSearchInput() {
        onView(withId(R.id.etSearch)).perform(replaceText("Music Festival"));
        closeSoftKeyboard();
        onView(withId(R.id.etSearch)).check(matches(withText("Music Festival")));
    }

    /** ChipGroup visibility and click */

    @Test
    public void testChipsVisibleAndClickable() {
        onView(withId(R.id.chipAll)).check(matches(isDisplayed())).perform(click());
        onView(withText("All")).check(matches(isDisplayed()));
    }

    /** Sort spinner visible */

    @Test
    public void testSortSpinnerVisible() {
        onView(withId(R.id.spinnerSort)).check(matches(isDisplayed()));
    }

    /** RecyclerView and empty state */

    @Test
    public void testRecyclerViewAndEmptyState() {
        onView(withId(R.id.recyclerBrowseEvents)).check(matches(isDisplayed()));
        onView(withId(R.id.emptyStateBrowse)).check(matches(isDisplayed()));
    }

    /** Floating Action Button visibility and click */

    @Test
    public void testFabVisibleAndClickable() {
        onView(withId(R.id.fabToTop)).check(matches(isDisplayed())).perform(click());
    }
}
