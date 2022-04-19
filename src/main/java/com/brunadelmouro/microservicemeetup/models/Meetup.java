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
    private List<Registration> registrationsList;

    public Meetup() {
    }

    public Meetup(Integer id, String event, String meetupDate) {
        this.id = id;
        this.event = event;
        this.meetupDate = meetupDate;
    }

    @Override
    public String toString() {
        return "Meetup{" +
                "id=" + id +
                ", event='" + event + '\'' +
                ", meetupDate='" + meetupDate + '\'' +
                ", registrationsList=" + registrationsList +
                '}';
    }

    public List<Registration> getRegistrationsList() {
        return registrationsList;
    }

    public void setRegistrationsList(List<Registration> registrationsList) {
        this.registrationsList = registrationsList;
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
