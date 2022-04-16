package com.brunadelmouro.microservicemeetup.repositories;


import com.brunadelmouro.microservicemeetup.models.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetupRepository extends JpaRepository<Meetup, Integer> {
}
