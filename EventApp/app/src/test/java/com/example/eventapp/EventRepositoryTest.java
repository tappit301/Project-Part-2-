package com.example.eventapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class EventRepositoryTest {

    private EventRepository repository;

    @Before
    public void setUp() {
        repository = EventRepository.getInstance();
        repository.getEvents().clear(); // Clear any previous events
    }

    @Test
    public void testSingletonInstance() {
        EventRepository repo2 = EventRepository.getInstance();
        assertSame(repository, repo2); // Should return the same instance
    }

    @Test
    public void testAddEvent() {
        Event event = new Event("Title", "Desc", "05/11/2025", "12:00", "Location");
        repository.addEvent(event);

        List<Event> events = repository.getEvents();
        assertEquals(1, events.size());
        assertEquals("Title", events.get(0).getTitle());
        assertEquals("Desc", events.get(0).getDescription());
        assertEquals("05/11/2025", events.get(0).getDate());
        assertEquals("12:00", events.get(0).getTime());
        assertEquals("Location", events.get(0).getLocation());
    }

    @Test
    public void testGetEventsReturnsList() {
        List<Event> events = repository.getEvents();
        assertNotNull(events);
        assertEquals(0, events.size()); // Initially empty
    }

    @Test
    public void testMultipleEvents() {
        Event event1 = new Event("Event1", "Desc1", "01/01/2025", "10:00", "Loc1");
        Event event2 = new Event("Event2", "Desc2", "02/02/2025", "14:00", "Loc2");

        repository.addEvent(event1);
        repository.addEvent(event2);

        List<Event> events = repository.getEvents();
        assertEquals(2, events.size());
        assertEquals("Event1", events.get(0).getTitle());
        assertEquals("Event2", events.get(1).getTitle());
    }
}
