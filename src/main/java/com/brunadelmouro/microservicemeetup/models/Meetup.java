package com.brunadelmouro.microservicemeetup.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Meetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //utilizada para indicar um primary key
    private Integer id;

    @Column(name = "event_name")
    private String event;

    @Column(name = "meetupDate")
    private String meetupDate;

    @Column
    private boolean registered;

    // muitos meetups para 1 registro
    @JoinColumn(name = "registration_id")
    @ManyToOne
    private Registration registrationNumber;

    public Meetup() {
    }

    public Meetup(Integer id, String event, String meetupDate) {
        this.id = id;
        this.event = event;
        this.meetupDate = meetupDate;
        this.registered = true;
    }

    public Registration getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Registration registrationNumber) {
        this.registrationNumber = registrationNumber;
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
