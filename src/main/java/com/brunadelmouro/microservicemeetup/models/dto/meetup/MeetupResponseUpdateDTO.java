package com.brunadelmouro.microservicemeetup.models.dto.meetup;

import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationMeetupListResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationMeetupUpdateResponseDTO;

import java.util.List;

public class MeetupResponseUpdateDTO {

    private Integer id;
    private String event;
    private String meetupDate;
    private RegistrationMeetupUpdateResponseDTO registrationDTO;

    public MeetupResponseUpdateDTO() {
    }

    public MeetupResponseUpdateDTO(Integer id, String event, String meetupDate, RegistrationMeetupUpdateResponseDTO registrationDTO) {
        this.id = id;
        this.event = event;
        this.meetupDate = meetupDate;
        this.registrationDTO = registrationDTO;
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

    public RegistrationMeetupUpdateResponseDTO getRegistrationDTO() {
        return registrationDTO;
    }

    public void setRegistrationDTO(RegistrationMeetupUpdateResponseDTO registrationDTO) {
        this.registrationDTO = registrationDTO;
    }
}
