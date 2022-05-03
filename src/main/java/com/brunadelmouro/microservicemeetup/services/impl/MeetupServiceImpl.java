package com.brunadelmouro.microservicemeetup.services.impl;

import com.brunadelmouro.microservicemeetup.exceptions.ObjectNotFoundException;
import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupRequestDTO;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupResponseDTO;
import com.brunadelmouro.microservicemeetup.repositories.MeetupRepository;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.services.MeetupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeetupServiceImpl implements MeetupService {


    private MeetupRepository meetupRepository;

    public MeetupServiceImpl(MeetupRepository meetupRepository) {
        this.meetupRepository = meetupRepository;
    }

    @Override
    public Meetup saveMeetup(Meetup meetup) {
        validateEventDoesntExist(meetup.getEvent());
        return meetupRepository.save(meetup);
    }

    @Override
    public Meetup findMeetupById(Integer id) {
        Optional<Meetup> obj = meetupRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Meetup not found"));
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
        validateMeetupExists(meetup);
        if(!meetup.getRegistrationsList().isEmpty())
            throw new IllegalArgumentException("Meetup cannot be deleted. There are registrations.");
        else
            this.meetupRepository.delete(meetup);
    }


    public MeetupResponseDTO convertEntityToResponseDTO(Meetup meetup){
        return new MeetupResponseDTO(meetup.getId(), meetup.getEvent(), meetup.getMeetupDate());
    }

    public void validateRegistrationDoesntExistOnMeetup(Integer meetupId, String number){
        List<Registration> registrationList = meetupRepository.findById(meetupId).get().getRegistrationsList();

        registrationList.forEach(
                x -> {
                    String numberOnList = x.getNumber();
                    if(number.equals(numberOnList))
                        throw new IllegalArgumentException("Registration already exists on Meetup");
                }
        );
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

    public Meetup convertEntityToDto(MeetupRequestDTO meetupRequestDTO){
        return new Meetup(null,
                            meetupRequestDTO.getEvent(),
                            meetupRequestDTO.getMeetupDate());
    }
}
