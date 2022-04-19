package com.brunadelmouro.microservicemeetup.services.impl;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupResponseDTO;
import com.brunadelmouro.microservicemeetup.repositories.MeetupRepository;
import com.brunadelmouro.microservicemeetup.services.MeetupService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class MeetupServiceImpl implements MeetupService {

    @Autowired
    MeetupRepository meetupRepository;

    @Override
    public Meetup saveMeetup(Meetup meetup) {
        validateEventDoesntExist(meetup.getEvent());
        return meetupRepository.save(meetup);
    }

    @Override
    public Meetup findMeetupById(Integer id) {
        Optional<Meetup> obj = meetupRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException(1, "Object not found"));
    }

    @Override
    public List<Meetup> findMeetups() {
        return meetupRepository.findAll();
    }

    @Override
    public Meetup updateMeetup(Meetup bodyMeetup) {
        validateMeetupExists(bodyMeetup);

        Meetup newMeetup = findMeetupById(bodyMeetup.getId());
        BeanUtils.copyProperties(bodyMeetup, newMeetup);

        return meetupRepository.save(newMeetup);
    }

    @Override
    public void deleteMeetup(Meetup meetup) {
        //deletar meetup se o mesmo estiver com  a lista de registrations vazia
        //caso contrário, retornar uma exceção personalizada
    }


    public MeetupResponseDTO convertEntityToResponseDTO(Meetup meetup){
        return new MeetupResponseDTO(meetup.getId(), meetup.getEvent(), meetup.getMeetupDate());
    }


    public void validateEventDoesntExist(String event){
        meetupRepository.findAll().forEach(
                x -> {
                    String comparsion = x.getEvent().toUpperCase();
                    if(event.toUpperCase().equals(comparsion))
                        throw new IllegalArgumentException("Event already exists");

                });
    }

    public Meetup validateMeetupExists(Meetup meetup){
        if (meetup == null || meetup.getId() == null) {
            throw new IllegalArgumentException("Meetup id cannot be null");
        }
        return meetup;
    }
}
