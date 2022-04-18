package com.brunadelmouro.microservicemeetup.repositories;

import com.brunadelmouro.microservicemeetup.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

    boolean existsByEmail(String email);

    Optional<Registration> findByNumber(String number);

}
