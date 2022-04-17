package com.brunadelmouro.microservicemeetup.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Meetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //utilizada para indicar um primary key
    private Integer id;

    @Column(name = "event_name")
    private String event;

    @Column(name = "meetupDate")
    private String meetupDate;

    @ManyToMany(mappedBy="meetups")
    private List<Registration> registrationNumbers;

    public Meetup() {
    }

    public Meetup(Integer id, String event, String meetupDate) {
        this.id = id;
        this.event = event;
        this.meetupDate = meetupDate;
    }

    public List<Registration> getRegistrationNumbers() {
        return registrationNumbers;
    }

    public void setRegistrationNumbers(List<Registration> registrationNumbers) {
        this.registrationNumbers = registrationNumbers;
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

}
