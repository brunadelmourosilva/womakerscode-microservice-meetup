package com.brunadelmouro.microservicemeetup.models.dto;

import com.brunadelmouro.microservicemeetup.models.Registration;

import java.util.List;

public class MeetupRegistrationResponseDTO {

    private Integer id;
    private String event;
    private String meetupDate;

    private List<Registration> registrationsList;

    public MeetupRegistrationResponseDTO() {
    }

    public MeetupRegistrationResponseDTO(Integer id, String event, String meetupDate, List<Registration> registrationsList) {
        this.id = id;
        this.event = event;
        this.meetupDate = meetupDate;
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

    public List<Registration> getRegistrationsList() {
        return registrationsList;
    }

    public void setRegistrationsList(List<Registration> registrationsList) {
        this.registrationsList = registrationsList;
    }
}
