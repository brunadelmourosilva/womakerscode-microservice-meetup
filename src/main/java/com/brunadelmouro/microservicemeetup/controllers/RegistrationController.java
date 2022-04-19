package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.MeetupResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.RegistrationResponseDTO;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationServiceImpl registrationService;

    private ModelMapper modelMapper;

    @PostMapping
    private ResponseEntity<RegistrationResponseDTO> saveRegistration(@RequestBody Registration registration) {
        registrationService.saveRegistration(registration);
        return ResponseEntity.ok().body(registrationService.convertEntityToResponseDTO(registration));
    }

    @GetMapping(value = "/{id}")
    private ResponseEntity<RegistrationResponseDTO> getRegistrationById(@PathVariable Integer id) {
        Registration registration = registrationService.findRegistrationById(id);

        return ResponseEntity.ok().body(registrationService.convertEntityToResponseDTO(registration));
    }

    //ERRADO
    @PutMapping(value = "/{id}")
    private ResponseEntity<Registration> updateRegistration(@PathVariable Integer id, @RequestBody Registration registration) {
        Registration newRegistration = registrationService.findRegistrationById(id);

        newRegistration.setId(id);
        newRegistration = registrationService.updateRegistration(registration);

        return ResponseEntity.ok().body(newRegistration);
    }

    //OK
    @DeleteMapping("/{id}")
    public void deleteRegistrationById(@PathVariable Integer id) {
        Registration registration = registrationService.findRegistrationById(id);

        registrationService.deleteRegistration(registration);
    }




//    @GetMapping
//    public Page<RegistrationResponseDTO> findRegistrationByPage(RegistrationResponseDTO objDTO, Pageable pageRequest){
//        Registration filter = modelMapper.map(objDTO, Registration.class);
//        Page<Registration> result = registrationService.findRegistrationPage(filter, pageRequest);
//
//        List<RegistrationResponseDTO> list = result.getContent()
//                .stream()
//                .map(entity -> modelMapper.map(entity, RegistrationResponseDTO.class))
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(list, pageRequest, result.getTotalElements());
//    }

}


