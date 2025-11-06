package com.example.eventapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {

    private Event event;

    @Before
    public void setUp() {
        event = new Event(
                "Hackathon 2025",
                "24-hour coding event",
                "05/11/2025",
                "10:00",
                "Campus Hall"
        );
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("Hackathon 2025", event.getTitle());
        assertEquals("24-hour coding event", event.getDescription());
        assertEquals("05/11/2025", event.getDate());
        assertEquals("10:00", event.getTime());
        assertEquals("Campus Hall", event.getLocation());
    }

    @Test
    public void testDifferentEventObjects() {
        Event anotherEvent = new Event(
                "Music Fest",
                "Annual cultural festival",
                "10/12/2025",
                "18:00",
                "Main Ground"
        );

        assertNotEquals(event.getTitle(), anotherEvent.getTitle());
        assertNotEquals(event.getDate(), anotherEvent.getDate());
    }

    @Test
    public void testNonNullFields() {
        assertNotNull(event.getTitle());
        assertNotNull(event.getDate());
        assertNotNull(event.getTime());
    }
}
