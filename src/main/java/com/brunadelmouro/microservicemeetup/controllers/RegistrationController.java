package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.MeetupResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.RegistrationResponseDTO;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationServiceImpl registrationService;

    @PostMapping
    private ResponseEntity<RegistrationResponseDTO> saveRegistration(@RequestBody Registration registration){
        registrationService.saveRegistration(registration);
        return ResponseEntity.ok().body(registrationService.convertEntityToResponseDTO(registration));
    }

    @GetMapping(value = "/{id}")
    private ResponseEntity<RegistrationResponseDTO> getRegistrationById(@PathVariable Integer id){
        Registration registration = registrationService.findRegistrationById(id);

        return ResponseEntity.ok().body(registrationService.convertEntityToResponseDTO(registration));
    }


}
