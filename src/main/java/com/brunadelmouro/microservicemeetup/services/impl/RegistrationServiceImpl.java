package com.brunadelmouro.microservicemeetup.services.impl;

import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.services.RegistrationService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    RegistrationRepository registrationRepository;

    @Override
    public Registration saveRegistration(Registration registration) {
        //if(registrationRepository.existsByRegistrationNumber(registration.getRegistrationNumber()))
        //implementar exception para validar email
        return registrationRepository.save(registration);
    }

    @Override
    public Registration findRegistrationById(Integer id) {
        Optional<Registration> obj = registrationRepository.findById(id);
        //implementar exception de id
        return obj.orElseThrow(() -> new ObjectNotFoundException(1, "Object not found"));
    }

    @Override
    public Registration findRegistrationByNumber(String registrationNumber) {
        Optional<Registration> obj = registrationRepository.findByRegistrationNumber(registrationNumber);
        //implementar exception de rNumber
        return obj.orElseThrow(() -> new ObjectNotFoundException(1, "Object not found"));
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
    public Registration updateRegistration(Registration registration) {
//        if (registration == null || registration.getId() == null) {
//            throw new IllegalArgumentException("Registration id cannot be null");
//        }
        //implementar exception de update
        return registrationRepository.save(registration);
    }

    @Override
    public void deleteRegistration(Registration registration) {
        if (registration == null || registration.getId() == null) {
            throw new IllegalArgumentException("Registration id cannot be null");
        }

        this.registrationRepository.delete(registration);
    }
}
