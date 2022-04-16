package com.brunadelmouro.microservicemeetup.models;

public class Meetup {

    private Integer id;
    private String event;
    private String meetupDate;
    private boolean registered;

    //private Registration registration;


    public Meetup() {
    }

    public Meetup(Integer id, String event, String meetupDate) {
        this.id = id;
        this.event = event;
        this.meetupDate = meetupDate;
        this.registered = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMeetupDate() {
        return meetupDate;
    }

    public void setMeetupDate(String meetupDate) {
        this.meetupDate = meetupDate;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
