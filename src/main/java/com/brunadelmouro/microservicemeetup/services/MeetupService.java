package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MeetupService {

    Meetup saveMeetup(Meetup meetup);

    Meetup findMeetupById(Integer id);

    //Meetup findMeetupByRegistrationNumber(String registrationNumber);

    List<Meetup> findMeetups();

    Meetup updateMeetup(Meetup meetup);

    void deleteMeetup(Meetup meetup);
}
