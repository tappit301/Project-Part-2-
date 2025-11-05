package com.example.eventapp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class EventDataTest {

    @Before
    public void setUp() {
        // Clear before each test to start fresh
        EventData.clearEvents();
    }

    @Test
    public void testAddEvent() {
        Event event = new Event("Hackathon", "Coding event", "2025-12-01", "10:00 AM", "Tech Center");
        EventData.addEvent(event);

        List<Event> events = EventData.getEventList();

        assertEquals(1, events.size());
        assertEquals("Hackathon", events.get(0).getTitle());
    }

    @Test
    public void testGetEventListInitiallyEmpty() {
        List<Event> events = EventData.getEventList();
        assertTrue("Event list should start empty", events.isEmpty());
    }

    @Test
    public void testClearEvents() {
        // Add some dummy events
        EventData.addEvent(new Event("Event 1", "Desc 1", "2025-11-05", "10:00", "City Hall"));
        EventData.addEvent(new Event("Event 2", "Desc 2", "2025-12-10", "11:00", "Community Center"));

        assertFalse(EventData.getEventList().isEmpty());

        // Clear and verify
        EventData.clearEvents();
        assertTrue("Event list should be empty after clear()", EventData.getEventList().isEmpty());
    }

    @Test
    public void testAddMultipleEvents() {
        EventData.addEvent(new Event("A", "Desc", "2025-11-01", "12:00", "Place A"));
        EventData.addEvent(new Event("B", "Desc", "2025-11-02", "13:00", "Place B"));

        List<Event> events = EventData.getEventList();

        assertEquals(2, events.size());
        assertEquals("B", events.get(1).getTitle());
    }
}
