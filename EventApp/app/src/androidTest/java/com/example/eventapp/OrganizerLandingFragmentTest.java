package com.example.eventapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.button.MaterialButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(AndroidJUnit4.class)
public class OrganizerLandingFragmentTest {

    @Before
    public void setUp() {
        // Clear repository before each test
        EventRepository.getInstance().getEvents().clear();
    }

    @Test
    public void testEmptyStateIsVisibleWhenNoEvents() {
        FragmentScenario<OrganizerLandingFragment> scenario =
                FragmentScenario.launchInContainer(OrganizerLandingFragment.class);

        scenario.onFragment(fragment -> {
            View emptyState = fragment.getView().findViewById(R.id.emptyStateLayout);
            View rvEvents = fragment.getView().findViewById(R.id.rvEvents);

            // Empty state should be visible
            assert(emptyState.getVisibility() == View.VISIBLE);
            // RecyclerView should be gone
            assert(rvEvents.getVisibility() == View.GONE);
        });
    }

    @Test
    public void testRecyclerViewVisibleWhenEventsExist() {
        // Add sample event
        EventRepository.getInstance().addEvent(new Event("Test Event", "Desc", "05/11/2025", "12:00", "Location"));

        FragmentScenario<OrganizerLandingFragment> scenario =
                FragmentScenario.launchInContainer(OrganizerLandingFragment.class);

        scenario.onFragment(fragment -> {
            View emptyState = fragment.getView().findViewById(R.id.emptyStateLayout);
            View rvEvents = fragment.getView().findViewById(R.id.rvEvents);

            // Empty state should be gone
            assert(emptyState.getVisibility() == View.GONE);
            // RecyclerView should be visible
            assert(rvEvents.getVisibility() == View.VISIBLE);
        });
    }

    @Test
    public void testCreateEventButtonsNavigate() {
        FragmentScenario<OrganizerLandingFragment> scenario =
                FragmentScenario.launchInContainer(OrganizerLandingFragment.class);

        scenario.onFragment(fragment -> {
            NavController mockNavController = Mockito.mock(NavController.class);
            Navigation.setViewNavController(fragment.getView(), mockNavController);

            MaterialButton btnAddEventEmpty = fragment.getView().findViewById(R.id.btnAddEventEmpty);
            MaterialButton btnCreateEventTop = fragment.getView().findViewById(R.id.btnCreateEventTop);
            View btnAddEvent = fragment.getView().findViewById(R.id.btnAddEvent);

            // Simulate clicks
            if (btnAddEventEmpty != null) btnAddEventEmpty.performClick();
            if (btnCreateEventTop != null) btnCreateEventTop.performClick();
            if (btnAddEvent != null) btnAddEvent.performClick();

            // Verify navigation called three times
            Mockito.verify(mockNavController, Mockito.times(3))
                    .navigate(R.id.action_organizerLandingFragment_to_createEventFragment);
        });
    }
}
