package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupResponseListDTO;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupResponseUpdateDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationMeetupListResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationMeetupUpdateResponseDTO;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/meetups")
public class MeetupController {

    @Autowired
    private MeetupServiceImpl meetupService;

    @Autowired
    private RegistrationServiceImpl registrationService;


    @PostMapping(value = "/registerMeetup")
    private ResponseEntity<MeetupResponseDTO> saveMeetup(@RequestBody Meetup meetup){
        meetupService.saveMeetup(meetup);
        return ResponseEntity.ok().body(meetupService.convertEntityToResponseDTO(meetup));
    }

    @PutMapping(value = "/registerRegistration/{meetupId}")
    private ResponseEntity<MeetupResponseUpdateDTO> updateMeetup(@PathVariable Integer meetupId, @RequestBody String number){

        Meetup meetup = meetupService.findMeetupById(meetupId);

        number = number.substring(29, number.length()-4);
        Registration registration = registrationService.findRegistrationByRegistrationNumber(number);

        //associação
        registration.getMeetups().add(meetup);
        meetup.getRegistrationsList().add(registration);

        registrationService.updateRegistration(registration);
        meetupService.updateMeetup(meetup);

        //DTO
        RegistrationMeetupUpdateResponseDTO registrationDTO = registrationService.
                convertEntityToRegistrationMeetupUpdateResponseDTO(registration);

        MeetupResponseUpdateDTO meetupDto = new MeetupResponseUpdateDTO(
                meetup.getId(),
                meetup.getEvent(),
                meetup.getMeetupDate(),
                registrationDTO);

        return ResponseEntity.ok().body(meetupDto);
    }

    @GetMapping(value = "/listRegistrations/{id}")
    private ResponseEntity<MeetupResponseListDTO> listAllRegistrationsByMeetup(@PathVariable(name = "id") Integer meetupId){
        Meetup meetup = meetupService.findMeetupById(meetupId);

        List<RegistrationMeetupListResponseDTO> registrationDtoList = registrationService.
                convertEntityToRegistrationMeetupListResponseDTO(meetup.getRegistrationsList());

        MeetupResponseListDTO meetupDto = new MeetupResponseListDTO(
                meetup.getId(),
                meetup.getEvent(),
                meetup.getMeetupDate(),
                registrationDtoList);

        return ResponseEntity.ok().body(meetupDto);
    }

    @GetMapping(value = "/listMeetups")
    private ResponseEntity<List<MeetupResponseDTO>> listAllMeetups(){
        List<MeetupResponseDTO> list = meetupService.findMeetups()
                .stream().map(x -> new MeetupResponseDTO(
                        x.getId(),
                        x.getEvent(),
                        x.getMeetupDate()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(list);
    }

    //terminar update dos atributos do meetup com MeetupResponseDTO


}
