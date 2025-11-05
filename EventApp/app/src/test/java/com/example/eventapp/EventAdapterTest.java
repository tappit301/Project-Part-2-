package com.example.eventapp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for EventAdapter (no emulator required).
 */
@RunWith(RobolectricTestRunner.class)
public class EventAdapterTest {

    private List<Event> sampleEvents;
    private EventAdapter adapter;
    private ViewGroup parent;

    @Before
    public void setup() {
        // Prepare a fake event list
        sampleEvents = new ArrayList<>();
        sampleEvents.add(new Event("Hackathon", "Coding challenge", "10/11/2025", "09:00", "Main Hall"));
        sampleEvents.add(new Event("Art Fair", "Gallery showcase", "12/12/2025", "18:30", "Community Center"));

        adapter = new EventAdapter(sampleEvents);

        // Mock parent view for inflation
        parent = new android.widget.FrameLayout(RuntimeEnvironment.getApplication());
    }

    @Test
    public void testGetItemCount_returnsCorrectSize() {
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void testOnBindViewHolder_bindsCorrectData() {
        // Create ViewHolder
        EventAdapter.EventViewHolder holder = adapter.onCreateViewHolder(parent, 0);

        // Bind first event
        adapter.onBindViewHolder(holder, 0);

        TextView titleView = holder.itemView.findViewById(R.id.tvEventTitle);
        TextView dateView = holder.itemView.findViewById(R.id.tvEventDate);

        assertEquals("Hackathon", titleView.getText().toString());
        assertTrue(dateView.getText().toString().contains("10/11/2025"));
        assertTrue(dateView.getText().toString().contains("09:00"));
    }

    @Test
    public void testItemClick_callsNavigationWithBundle() {
        // Create ViewHolder
        EventAdapter.EventViewHolder holder = adapter.onCreateViewHolder(parent, 0);

        // Mock NavController
        NavController mockNavController = mock(NavController.class);

        // Prepare navigation mock
        try (MockedStatic<Navigation> navStatic = Mockito.mockStatic(Navigation.class)) {
            navStatic.when(() -> Navigation.findNavController(holder.itemView)).thenReturn(mockNavController);

            // Bind event and simulate click
            adapter.onBindViewHolder(holder, 0);
            holder.itemView.performClick();

            // Verify that navigation was called with correct arguments
            verify(mockNavController, times(1))
                    .navigate(eq(R.id.action_organizerLandingFragment_to_eventDetailsFragment), any(Bundle.class));
        }
    }
}
