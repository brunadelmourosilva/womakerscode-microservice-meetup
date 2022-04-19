package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.MeetupRegistrationResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.MeetupResponseDTO;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/meetups")
public class MeetupController {

    @Autowired
    private MeetupServiceImpl meetupService;

    @Autowired
    private RegistrationServiceImpl registrationService;

    @GetMapping
    private ResponseEntity<List<MeetupResponseDTO>> listAllMeetups(){
        List<MeetupResponseDTO> list = meetupService.findMeetups()
                .stream().map(x -> new MeetupResponseDTO(
                        x.getId(),
                        x.getEvent(),
                        x.getMeetupDate()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(list);
    }

    //post dos atributos do meetup
    @PostMapping
    private ResponseEntity<MeetupResponseDTO> saveMeetup(@RequestBody Meetup meetup){
        meetupService.saveMeetup(meetup);
        return ResponseEntity.ok().body(meetupService.convertEntityToResponseDTO(meetup));
    }

    //update do meetup para adicionar um registration
    @PutMapping(value = "/{meetupId}")
    private ResponseEntity<Meetup> updateMeetup(@PathVariable Integer meetupId, @RequestBody String number){

        Meetup meetup = meetupService.findMeetupById(meetupId);

        number = number.substring(29, number.length()-4);
        Registration registration = registrationService.findRegistrationByRegistrationNumber(number);
        registration.getMeetups().add(meetup);
        meetup.getRegistrationsList().add(registration);

        registrationService.updateRegistration(registration);
        meetupService.updateMeetup(meetup);

        System.out.println("VAI SALVAR!!!!!!!!!!!!!!");
        return ResponseEntity.ok().body(meetup);
    }

}
