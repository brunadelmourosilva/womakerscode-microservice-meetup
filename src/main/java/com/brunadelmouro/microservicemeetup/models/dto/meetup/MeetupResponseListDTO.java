package com.brunadelmouro.microservicemeetup.models.dto.meetup;

import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationMeetupListResponseDTO;

import javax.persistence.*;
import java.util.List;

public class MeetupResponseListDTO {

    private Integer id;
    private String event;
    private String meetupDate;
    private List<RegistrationMeetupListResponseDTO> registrationsList;

    public MeetupResponseListDTO(Integer id, String event, String meetupDate, List<RegistrationMeetupListResponseDTO> registrationsList) {
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

    public List<RegistrationMeetupListResponseDTO> getRegistrationsList() {
        return registrationsList;
    }

    public void setRegistrationsList(List<RegistrationMeetupListResponseDTO> registrationsList) {
        this.registrationsList = registrationsList;
    }
}
