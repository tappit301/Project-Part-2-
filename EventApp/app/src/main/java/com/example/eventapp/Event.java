package com.example.eventapp;

/**
 * This is a simple model class that represents an Event object stored in Firestore.

 * Each event contains details like title, date, time and organizer info.
 * This class includes a no-argument constructor required by Firestore,
 * along with standard getters and setters.
 *
 *
 * @author tappit
 */
public class Event {

    /** Unique Firestore document ID for the event. */
    private String id;

    /** Title of the event. */
    private String title;

    /** Description of the event (optional). */
    private String description;

    /** Date of the event . */
    private String date;

    /** Time of the event . */
    private String time;

    /** Location where the event will take place. */
    private String location;

    /** Organizer's Firebase user ID . */
    private String organizerId;

    /** Organizer's email address . */
    private String organizerEmail;

    /**
     * Empty constructor required by Firestore for data mapping.
     */
    public Event() {
    }

    /**
     * Conveniene constructor for quickly creating an Event object.
     *
     * @param title       the name of the event
     * @param description short details about the event
     * @param date        the event date
     * @param time        the event time
     * @param location    where the event will be held
     */
    public Event(String title, String description, String date,
                 String time, String location) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    /**
     * @return the Firestore document ID for this event
     */
    public String getId() {
        return id;
    }

    /**
     * @return the event title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the event description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the event date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return the event time
     */
    public String getTime() {
        return time;
    }

    /**
     * @return the event location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the organizer's Firebase user ID
     */
    public String getOrganizerId() {
        return organizerId;
    }

    /**
     * @return the organizer's email
     */
    public String getOrganizerEmail() {
        return organizerEmail;
    }

    /**
     * Sets the Firestore document ID.
     *
     * @param id unique Firestore document ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the event title.
     *
     * @param title title of the event
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the event description.
     *
     * @param description details of the event
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the event date.
     *
     * @param date date of the event
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the event time.
     *
     * @param time time of the event
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Sets the event location.
     *
     * @param location location of the event
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the organizer's Firebase user ID.
     *
     * @param organizerId Firebase UID of the organizer
     */
    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    /**
     * Sets the organizer's email.
     *
     * @param organizerEmail email of the organizer
     */
    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }
}
