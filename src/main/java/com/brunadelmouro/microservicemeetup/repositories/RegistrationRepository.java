package com.brunadelmouro.microservicemeetup.repositories;

import com.brunadelmouro.microservicemeetup.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
}
