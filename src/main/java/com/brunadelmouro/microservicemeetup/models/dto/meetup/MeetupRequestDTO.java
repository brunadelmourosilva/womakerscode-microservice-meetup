package com.brunadelmouro.microservicemeetup.models.dto.meetup;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MeetupRequestDTO {

    @NotNull(message = "The field EVENT cannot be null")
    @Length(min = 4, max = 50, message = "The field EVENT needs to be between 4 and 50 characters")
    private String event;

    @NotNull(message = "The field MEETUPDATE cannot be null")
    @Pattern(regexp = "\\d{1,2}/\\d{1,2}/\\d{4}", message = "THe field MEETUPDATE needs to be on following the pattern: dd/mm/yyyy")
    private String meetupDate;

    public MeetupRequestDTO() {
    }

    public MeetupRequestDTO(String event, String meetupDate) {
        this.event = event;
        this.meetupDate = meetupDate;
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
