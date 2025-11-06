package com.example.eventapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.button.MaterialButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EventDetailsFragmentTest {

    private Bundle args;

    @Before
    public void setUp() {
        args = new Bundle();
        args.putString("title", "Community Meetup");
        args.putString("desc", "A fun meetup for developers");
        args.putString("date", "05/11/2025");
        args.putString("time", "12:00");
        args.putString("location", "Library Hall");
        args.putString("category", "Tech");
        args.putInt("capacity", 50);
    }

    @Test
    public void testEventDataBinding() {
        FragmentScenario<EventDetailsFragment> scenario =
                FragmentScenario.launchInContainer(EventDetailsFragment.class, args);

        scenario.onFragment(fragment -> {
            View view = fragment.getView();
            assertNotNull(view);

            TextView tvTitle = view.findViewById(R.id.tvEventTitle);
            TextView tvDesc = view.findViewById(R.id.tvEventDescription);
            TextView tvDate = view.findViewById(R.id.tvEventDate);
            TextView tvTime = view.findViewById(R.id.tvEventTime);
            TextView tvLocation = view.findViewById(R.id.tvEventLocation);
            TextView chipCategory = view.findViewById(R.id.chipCategory);
            TextView tvCapacityStatus = view.findViewById(R.id.tvCapacityStatus);
            View layoutCapacity = view.findViewById(R.id.layoutCapacity);

            assertEquals("Community Meetup", tvTitle.getText().toString());
            assertEquals("A fun meetup for developers", tvDesc.getText().toString());
            assertEquals("05/11/2025", tvDate.getText().toString());
            assertEquals("12:00", tvTime.getText().toString());
            assertEquals("Library Hall", tvLocation.getText().toString());

            assertEquals("Tech", chipCategory.getText().toString());
            assertEquals(View.VISIBLE, chipCategory.getVisibility());

            assertEquals(View.VISIBLE, layoutCapacity.getVisibility());
            assertEquals("Max capacity: 50 people", tvCapacityStatus.getText().toString());
        });
    }

    @Test
    public void testWaitingListButton() {
        FragmentScenario<EventDetailsFragment> scenario =
                FragmentScenario.launchInContainer(EventDetailsFragment.class, args);

        scenario.onFragment(fragment -> {
            View view = fragment.getView();
            assertNotNull(view);

            MaterialButton joinBtn = view.findViewById(R.id.btnJoinWaitingList);
            assertNotNull(joinBtn);

            // Simulate click
            joinBtn.performClick();

            // Button disabled and text changed
            assertTrue(!joinBtn.isEnabled());
            assertEquals("Added to Waiting List ✅", joinBtn.getText().toString());
        });
    }
}
