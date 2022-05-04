package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupRequestDTO;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupResponseDTO;

import java.util.List;

public interface MeetupService {

    Meetup saveMeetup(Meetup meetup);

    Meetup findMeetupById(Integer id);

    List<Meetup> findMeetups();

    Meetup updateMeetup(Meetup meetup);

    void deleteMeetup(Meetup meetup);

    MeetupResponseDTO convertEntityToResponseDTO(Meetup meetup);

    void validateRegistrationDoesntExistOnMeetup(Integer meetupId, String number);

    void validateEventDoesntExist(String event);

    Meetup validateMeetupExists(Meetup meetup);

    Meetup convertEntityToDto(MeetupRequestDTO meetupRequestDTO);
}
