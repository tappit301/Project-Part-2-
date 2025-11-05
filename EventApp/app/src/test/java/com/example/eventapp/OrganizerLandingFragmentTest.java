package com.example.eventapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentScenario;
import androidx.recyclerview.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class OrganizerLandingFragmentTest {

    @Before
    public void setUp() {
        EventRepository.getInstance().getEvents().clear();
    }

    @Test
    public void testEmptyStateVisibleWhenNoEvents() {
        FragmentScenario<OrganizerLandingFragment> scenario =
                FragmentScenario.launchInContainer(OrganizerLandingFragment.class);

        scenario.onFragment(fragment -> {
            LinearLayout emptyState = fragment.getView().findViewById(R.id.emptyStateLayout);
            RecyclerView rvEvents = fragment.getView().findViewById(R.id.rvEvents);

            assertNotNull(emptyState);
            assertNotNull(rvEvents);

            assertEquals(View.VISIBLE, emptyState.getVisibility());
            assertEquals(View.GONE, rvEvents.getVisibility());
        });
    }

    @Test
    public void testRecyclerViewVisibleWhenEventsExist() {
        // Add dummy events
        EventRepository.getInstance().addEvent(new Event(
                "Test Event 1", "Desc", "01/01/2025", "10:00", "Location 1"));
        EventRepository.getInstance().addEvent(new Event(
                "Test Event 2", "Desc", "02/01/2025", "12:00", "Location 2"));

        FragmentScenario<OrganizerLandingFragment> scenario =
                FragmentScenario.launchInContainer(OrganizerLandingFragment.class);

        scenario.onFragment(fragment -> {
            LinearLayout emptyState = fragment.getView().findViewById(R.id.emptyStateLayout);
            RecyclerView rvEvents = fragment.getView().findViewById(R.id.rvEvents);

            assertNotNull(emptyState);
            assertNotNull(rvEvents);

            assertEquals(View.GONE, emptyState.getVisibility());
            assertEquals(View.VISIBLE, rvEvents.getVisibility());

            assertNotNull(rvEvents.getAdapter());
            assertEquals(2, rvEvents.getAdapter().getItemCount());
        });
    }
}
