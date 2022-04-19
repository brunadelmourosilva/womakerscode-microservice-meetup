package com.brunadelmouro.microservicemeetup.services.impl;

import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationMeetupListResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationMeetupUpdateResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationResponseDTO;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.services.RegistrationService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    RegistrationRepository registrationRepository;

    @Override
    public Registration saveRegistration(Registration registration) {
        validateRegistrationExistsByEmail(registration.getEmail());
        return registrationRepository.save(registration);
    }

    public RegistrationResponseDTO convertEntityToResponseDTO(Registration registration) {
        return new RegistrationResponseDTO(registration.getId(), registration.getName(), registration.getEmail(), registration.getDateOfRegistration(), registration.getNumber());
    }

    public void validateRegistrationExistsByEmail(String email){
        if(registrationRepository.existsByEmail(email))
            throw new IllegalArgumentException("Objeto já cadastrado");
    }

    public Registration validateRegistrationExists(Registration registration){
        if (registration == null || registration.getId() == null) {
            throw new IllegalArgumentException("Registration id cannot be null");
        }
        return registration;
    }

    @Override
    public Registration findRegistrationById(Integer id) {
        Optional<Registration> obj = registrationRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(1, "Object not found"));
    }

    @Override
    public Registration findRegistrationByRegistrationNumber(String registrationNumber) {
        Registration registration = registrationRepository.findByNumber(registrationNumber);
        validateRegistrationExists(registration);
        return registration;
    }

    @Override
    public Page<Registration> findRegistrationPage(Registration filter, Pageable pageRequest) {
        Example<Registration> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return registrationRepository.findAll(example, pageRequest);
    }

    @Override
    public Registration updateRegistration(Registration bodyRegistration) {
        validateRegistrationExists(bodyRegistration);
        Registration newRegistration = findRegistrationById(bodyRegistration.getId());
        BeanUtils.copyProperties(bodyRegistration, newRegistration, "id");
        return registrationRepository.save(newRegistration);
    }

    @Override
    public void deleteRegistration(Registration registration) {
        validateRegistrationExists(registration);
        this.registrationRepository.delete(registration);
    }

    public List<RegistrationMeetupListResponseDTO> convertEntityToRegistrationMeetupListResponseDTO(List<Registration> registrationList){
        return registrationList.stream().map(registration ->
            new RegistrationMeetupListResponseDTO(registration.getName(), registration.getEmail())
        ).collect(Collectors.toList());
    }

    public RegistrationMeetupUpdateResponseDTO convertEntityToRegistrationMeetupUpdateResponseDTO(Registration registration){
        return new RegistrationMeetupUpdateResponseDTO(registration.getId(),
                                                       registration.getName(),
                                                       registration.getEmail(),
                                                       registration.getNumber());
    }
}
