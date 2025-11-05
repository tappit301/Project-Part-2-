package com.example.eventapp;

import java.util.ArrayList;
import java.util.List;

public class EventData {
    private static final List<Event> eventList = new ArrayList<>();

    public static List<Event> getEventList() {
        return eventList;
    }

    public static void addEvent(Event event) {
        eventList.add(event);
    }

    public static void clearEvents() {
        eventList.clear();
    }
}
