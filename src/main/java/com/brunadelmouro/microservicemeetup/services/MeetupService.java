package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MeetupService {

    Meetup saveMeetup(Meetup meetup);

    Meetup findMeetupById(Integer id);

    boolean findRegistrationById(Integer id);

    List<Meetup> findMeetups();

    Page<Meetup> findRegistrationsByMeetup(Registration registration, Pageable pageable);

    Meetup updateMeetup(Meetup meetup);

    void deleteMeetup(Meetup meetup);
}