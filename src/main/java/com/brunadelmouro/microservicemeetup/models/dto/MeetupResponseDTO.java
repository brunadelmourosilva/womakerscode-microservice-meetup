package com.brunadelmouro.microservicemeetup.models.dto;

public class MeetupResponseDTO {
    private Integer id;
    private String event;
    private String meetupDate;

    public MeetupResponseDTO(Integer id, String event, String meetupDate) {
        this.id = id;
        this.event = event;
        this.meetupDate = meetupDate;
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
