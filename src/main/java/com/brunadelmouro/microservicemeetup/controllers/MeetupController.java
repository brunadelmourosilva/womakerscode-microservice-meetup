package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupRequestDTO;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupResponseListDTO;
import com.brunadelmouro.microservicemeetup.models.dto.meetup.MeetupResponseUpdateDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationMeetupListResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationMeetupUpdateResponseDTO;
import com.brunadelmouro.microservicemeetup.services.EmailService;
import com.brunadelmouro.microservicemeetup.services.impl.EmailServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/meetups")
public class MeetupController {

    @Autowired
    private MeetupServiceImpl meetupService;

    @Autowired
    private RegistrationServiceImpl registrationService;

    @Autowired
    private EmailServiceImpl emailService;


    @ApiOperation(value = "Salvar Meetup")
    @PostMapping(value = "/registerMeetup")
    private ResponseEntity<MeetupResponseDTO> saveMeetup(@Valid @RequestBody MeetupRequestDTO meetupRequestDTO){
        Meetup meetup = meetupService.convertEntityToDto(meetupRequestDTO);
        meetupService.saveMeetup(meetup);

        return ResponseEntity.ok().body(meetupService.convertEntityToResponseDTO(meetup));
    }

    @ApiOperation(value = "Registrar usuário em um determinado Meetup")
    @PutMapping(value = "/registerRegistration/{meetupId}")
    private ResponseEntity<MeetupResponseUpdateDTO> updateMeetup(@PathVariable Integer meetupId, @RequestBody String number){
        Meetup meetup = meetupService.findMeetupById(meetupId);

        //number = number.substring(17, number.length()-4); //postman

        Registration registration = registrationService.findRegistrationByRegistrationNumber(number);



        //associação
        registration.getMeetups().add(meetup);
        meetup.getRegistrationsList().add(registration);

        registrationService.updateRegistration(registration);
        meetupService.updateMeetup(meetup);

        //email
        //emailService.sendEmail(meetup, registration);

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

    @ApiOperation(value = "Listar Registrations pelo id do Meetup")
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

    @ApiOperation(value = "Listar informações de um Meetup pelo id")
    @GetMapping(value = "/{id}")
    private ResponseEntity<MeetupResponseDTO> findMeetupById(@PathVariable Integer id){
        Meetup foundMeetup = meetupService.findMeetupById(id);

        return ResponseEntity
                .ok()
                .body(meetupService.convertEntityToResponseDTO(foundMeetup));
    }

    @ApiOperation(value = "Listar todos os Meetups cadastrados")
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

    @ApiOperation(value = "Atualizar informações de um Meetup")
    @PutMapping("/{id}")
    public ResponseEntity<MeetupResponseDTO> updateMeetupById(@PathVariable Integer id, @Valid @RequestBody MeetupRequestDTO meetupRequestDTO) {
        Meetup newMeetup = meetupService.convertEntityToDto(meetupRequestDTO);
        Meetup oldMeetup = meetupService.findMeetupById(id);

        newMeetup.setId(oldMeetup.getId());
        meetupService.updateMeetup(newMeetup);

        return ResponseEntity.ok().body(meetupService.convertEntityToResponseDTO(newMeetup));
    }

    @ApiOperation(value = "Deletar Meetup")
    @DeleteMapping("/{id}")
    public void deleteMeetupById(@PathVariable Integer id) {
        Meetup meetup = meetupService.findMeetupById(id);

        meetupService.deleteMeetup(meetup);
    }

}
