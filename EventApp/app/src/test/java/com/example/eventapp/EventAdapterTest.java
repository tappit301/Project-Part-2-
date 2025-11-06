package com.example.eventapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34) // Match your compileSdk
public class EventAdapterTest {

    private List<Event> mockEvents;
    private EventAdapter adapter;
    private ViewGroup parent;

    @Before
    public void setUp() {
        // Prepare sample data
        mockEvents = new ArrayList<>();
        mockEvents.add(new Event("Music Fest", "A fun concert", "10/12/2025", "18:00", "City Park"));
        mockEvents.add(new Event("Tech Meetup", "Developers unite", "15/12/2025", "10:00", "Campus Hall"));

        adapter = new EventAdapter(mockEvents);

        // Use a dummy parent for inflating the layout
        parent = new RecyclerView(RuntimeEnvironment.getApplication());
    }

    @Test
    public void testItemCount_MatchesEventList() {
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void testOnCreateViewHolder_CreatesValidViewHolder() {
        EventAdapter.EventViewHolder holder = adapter.onCreateViewHolder(parent, 0);

        assertNotNull(holder);
        assertNotNull(holder.tvTitle);
        assertNotNull(holder.tvDate);
    }

    @Test
    public void testOnBindViewHolder_BindsCorrectData() {
        EventAdapter.EventViewHolder holder = adapter.onCreateViewHolder(parent, 0);
        adapter.onBindViewHolder(holder, 0);

        assertEquals("Music Fest", holder.tvTitle.getText().toString());
        assertEquals("10/12/2025 • 18:00", holder.tvDate.getText().toString());
    }

    @Test
    public void testGetItemCount_WithEmptyList_ReturnsZero() {
        EventAdapter emptyAdapter = new EventAdapter(new ArrayList<>());
        assertEquals(0, emptyAdapter.getItemCount());
    }

    @Test
    public void testEventClickListener_DoesNotCrash() {
        EventAdapter.EventViewHolder holder = adapter.onCreateViewHolder(parent, 0);
        adapter.onBindViewHolder(holder, 0);

        // Simulate click
        holder.itemView.performClick();

        // Assert item is clickable
        assertTrue(holder.itemView.isClickable());
    }
}
