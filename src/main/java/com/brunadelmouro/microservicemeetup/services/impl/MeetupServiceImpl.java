package com.brunadelmouro.microservicemeetup.services.impl;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.MeetupResponseDTO;
import com.brunadelmouro.microservicemeetup.repositories.MeetupRepository;
import com.brunadelmouro.microservicemeetup.services.MeetupService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    public void validateEventDoesntExist(String event){
        meetupRepository.findAll().forEach(
                x -> {
                    String comparsion = x.getEvent().toUpperCase();
                    if(event.toUpperCase().equals(comparsion))
                        throw new IllegalArgumentException("Evento já cadastrado");

                });
    }

    //mostrar meetup e lista de pessoas
    @Override
    public Meetup findMeetupById(Integer id) {
        Optional<Meetup> obj = meetupRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(1, "Object not found"));
    }

    //buscar todos os meetups cadastrados
    @Override
    public List<Meetup> findMeetups() {
        return meetupRepository.findAll();
    }

    public MeetupResponseDTO convertEntityToResponseDTO(Meetup meetup){
        return new MeetupResponseDTO(meetup.getId(), meetup.getEvent(), meetup.getMeetupDate());
    }



    @Override
    public Meetup updateMeetup(Meetup meetup) {
        //BRUNA IMPLEMENTAR
        return null;
    }

    @Override
    public void deleteMeetup(Meetup meetup) {
        //BRUNA IMPLEMENTAR
    }
}
