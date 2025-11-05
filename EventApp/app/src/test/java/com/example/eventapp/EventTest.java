package com.example.eventapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the Event model class.
 */
public class EventTest {

    private Event event;

    @Before
    public void setUp() {
        event = new Event(
                "Community Picnic",
                "A local get-together for families",
                "10/11/2025",
                "12:30",
                "Central Park"
        );
    }

    @Test
    public void testGetTitle() {
        assertEquals("Community Picnic", event.getTitle());
    }

    @Test
    public void testGetDescription() {
        assertEquals("A local get-together for families", event.getDescription());
    }

    @Test
    public void testGetDate() {
        assertEquals("10/11/2025", event.getDate());
    }

    @Test
    public void testGetTime() {
        assertEquals("12:30", event.getTime());
    }

    @Test
    public void testGetLocation() {
        assertEquals("Central Park", event.getLocation());
    }

    @Test
    public void testConstructorAssignsAllFields() {
        Event e = new Event("Yoga", "Morning session", "05/12/2025", "08:00", "Studio A");
        assertEquals("Yoga", e.getTitle());
        assertEquals("Morning session", e.getDescription());
        assertEquals("05/12/2025", e.getDate());
        assertEquals("08:00", e.getTime());
        assertEquals("Studio A", e.getLocation());
    }
}
