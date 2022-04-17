package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.dto.MeetupResponseDTO;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
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

    @PostMapping
    private ResponseEntity<MeetupResponseDTO> saveMeetup(@RequestBody Meetup meetup){
        meetupService.saveMeetup(meetup);
        return ResponseEntity.ok().body(meetupService.convertEntityToResponseDTO(meetup));
    }
}
