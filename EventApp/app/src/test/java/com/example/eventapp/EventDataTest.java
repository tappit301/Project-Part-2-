package com.example.eventapp;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EventDataTest {

    @Before
    public void setUp() {
        // Ensure a clean list before each test
        EventData.clearEvents();
    }

    @Test
    public void testAddEvent_AddsEventToList() {
        Event event = new Event("Hackathon", "Coding event", "2025-11-10", "09:00", "Tech Hub");
        EventData.addEvent(event);

        List<Event> result = EventData.getEventList();

        assertEquals(1, result.size());
        assertEquals("Hackathon", result.get(0).getTitle());
    }

    @Test
    public void testGetEventList_ReturnsSameListReference() {
        List<Event> list1 = EventData.getEventList();
        List<Event> list2 = EventData.getEventList();

        assertSame(list1, list2);
    }

    @Test
    public void testClearEvents_RemovesAllEvents() {
        Event event1 = new Event("Music Fest", "Live concert", "2025-12-01", "20:00", "Main Square");
        Event event2 = new Event("Art Expo", "Gallery event", "2025-12-15", "10:00", "Art Center");

        EventData.addEvent(event1);
        EventData.addEvent(event2);

        assertEquals(2, EventData.getEventList().size());

        EventData.clearEvents();

        assertTrue(EventData.getEventList().isEmpty());
    }

    @Test
    public void testAddMultipleEvents_OrderIsPreserved() {
        Event e1 = new Event("Event 1", "Desc 1", "2025-10-01", "10:00", "Place A");
        Event e2 = new Event("Event 2", "Desc 2", "2025-10-02", "11:00", "Place B");
        Event e3 = new Event("Event 3", "Desc 3", "2025-10-03", "12:00", "Place C");

        EventData.addEvent(e1);
        EventData.addEvent(e2);
        EventData.addEvent(e3);

        List<Event> events = EventData.getEventList();
        assertEquals(3, events.size());
        assertEquals("Event 1", events.get(0).getTitle());
        assertEquals("Event 3", events.get(2).getTitle());
    }

    @Test
    public void testClearEventsOnEmptyList_NoCrash() {
        EventData.clearEvents();
        assertTrue(EventData.getEventList().isEmpty());
    }
}
