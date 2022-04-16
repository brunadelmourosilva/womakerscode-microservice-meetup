package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.models.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RegistrationService {

    Registration saveRegistration(Registration registration);

    Registration findRegistrationById(Integer id);

    Registration findRegistrationByNumber(String registrationNumber);

    Page<Registration> findRegistrationPage(Registration filter, Pageable pageRequest);

    Registration updateRegistration(Registration registration);

    void deleteRegistration(Registration registration);
}