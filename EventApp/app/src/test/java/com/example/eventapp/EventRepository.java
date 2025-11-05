package com.example.eventapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class EventRepositoryTest {

    private EventRepository repository;

    @Before
    public void setUp() {
        // Reset singleton instance by reflection or just get instance
        repository = EventRepository.getInstance();
        repository.getEvents().clear(); // clear previous events
    }

    @Test
    public void testAddEvent() {
        Event event = new Event("Test Event", "Description", "01/01/2025", "10:00", "Test Location");
        repository.addEvent(event);

        List<Event> events = repository.getEvents();
        assertEquals(1, events.size());
        assertEquals("Test Event", events.get(0).getTitle());
        assertEquals("Description", events.get(0).getDescription());
    }

    @Test
    public void testMultipleEvents() {
        Event event1 = new Event("Event 1", "Desc 1", "01/01/2025", "10:00", "Loc 1");
        Event event2 = new Event("Event 2", "Desc 2", "02/01/2025", "11:00", "Loc 2");

        repository.addEvent(event1);
        repository.addEvent(event2);

        List<Event> events = repository.getEvents();
        assertEquals(2, events.size());
        assertTrue(events.contains(event1));
        assertTrue(events.contains(event2));
    }

    @Test
    public void testEmptyInitially() {
        List<Event> events = repository.getEvents();
        assertEquals(0, events.size());
    }
}
