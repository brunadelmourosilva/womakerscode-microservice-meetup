package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationRequestDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationResponseDTO;
import com.brunadelmouro.microservicemeetup.services.impl.EmailServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/registrations")
public class RegistrationController {

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private RegistrationServiceImpl registrationService;

    @ApiOperation(value = "Salvar Registration")
    @PostMapping
    private ResponseEntity<RegistrationResponseDTO> saveRegistration(@Valid @RequestBody RegistrationRequestDTO registrationDto) {
        Registration entityRegistration = registrationService.convertDtoToEntity(registrationDto);
        registrationService.saveRegistration(entityRegistration);

        logger.info("Registration saved");

        return ResponseEntity.ok().body(registrationService.convertEntityToResponseDTO(entityRegistration));
    }

    @ApiOperation(value = "Listar informações de um Registration pelo id")
    @GetMapping(value = "/{id}")
    private ResponseEntity<RegistrationResponseDTO> getRegistrationById(@PathVariable Integer id) {
        Registration registration = registrationService.findRegistrationById(id);

        return ResponseEntity.ok().body(registrationService.convertEntityToResponseDTO(registration));
    }

    @ApiOperation(value = "Listar informações de um Registration pelo atributo number")
    @GetMapping(value = "registrationNumber/{id}")
    private ResponseEntity<RegistrationResponseDTO> getRegistrationByRegistrationNumber(@PathVariable("id") String number){
        Registration registration = registrationService.findRegistrationByRegistrationNumber(number);

        return ResponseEntity.ok().body(registrationService.convertEntityToResponseDTO(registration));
    }

    @ApiOperation(value = "Listar informações de Registrations por Pageable")
    @GetMapping
    public Page<RegistrationResponseDTO> findRegistrationsByPage(Registration registration, Pageable pageRequest){
        Page<Registration> result = registrationService.findRegistrationPage(registration, pageRequest);

        List<RegistrationResponseDTO> list = result.getContent()
                .stream()
                .map(entity -> registrationService.convertEntityToResponseDTO(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageRequest, result.getTotalElements());
    }

    @ApiOperation(value = "Atualizar informações de um Registration")
    @PutMapping(value = "/{id}")
    private ResponseEntity<Registration> updateRegistration(@PathVariable Integer id, @Valid @RequestBody RegistrationRequestDTO registrationDto) {
        Registration newRegistration = registrationService.convertDtoToEntity(registrationDto);
        Registration oldRegistration = registrationService.findRegistrationById(id);

        newRegistration.setId(oldRegistration.getId());
        registrationService.updateRegistration(newRegistration);

        logger.info("Registration updated");

        return ResponseEntity.ok().body(newRegistration);
    }

    @ApiOperation(value = "Deletar Registration")
    @DeleteMapping("/{id}")
    public void deleteRegistrationById(@PathVariable Integer id) {
        Registration registration = registrationService.findRegistrationById(id);

        registrationService.deleteRegistration(registration);

        logger.info("Registration deleted");
    }
}


