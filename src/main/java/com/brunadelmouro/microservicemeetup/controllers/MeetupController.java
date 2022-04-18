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
    @PutMapping(value = "/{id}")
    private ResponseEntity<Meetup> updateMeetup(@PathVariable Integer id, @RequestBody String registrationNumber){
        // procurar no banco de dados o id do meetup, guardar objeto
        System.out.println("entrou na função");
        Meetup meetup = meetupService.findMeetupById(id);
        System.out.println("encontrou meetup!");
        // procurar registrationNumber da pessoa, guardar pessoa/objeto encontrado
        Registration registration = registrationService.findRegistrationByRegistrationNumber(registrationNumber);
        System.out.println("encontrou registration!");
        // chamar um update, enviando para o método:
        // o objeto meetup
        // o objeto registration
        meetup.setRegistrationsList(Arrays.asList(registration));
        System.out.println("setou registration!");
        System.out.println(registration);
        System.out.println();
        System.out.println(meetup);
        meetupService.updateMeetup(meetup);
        System.out.println("atualizou!");

        //retornar meetup + registration (dto novo com registration)
        return ResponseEntity.ok().body(meetup);
    }

}
