package com.brunadelmouro.microservicemeetup.repositories;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class MeetupRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    private MeetupRepository meetupRepository;

    @Test
    @DisplayName("Should return true when exists a Meetup by id already created")
    public void findMeetupByIdTest(){

        //cenário
        Integer id = 1;
        Meetup meetup = createNewMeetup();

        //execução
        testEntityManager.persist(meetup);

        Optional<Meetup> exists = meetupRepository.findById(id);

        //assert
        assertThat(exists.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should return true when exists a Meetup by event already created")
    public void findMeetupByEventTest(){

        //cenário
        String event = "Palestra 1";
        Meetup meetup = createNewMeetup();

        //execução
        testEntityManager.persist(meetup);

        boolean exists = meetupRepository.existsByEvent(event);

        //assert
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should save a Meetup with success")
    public void saveMeetupTest() {

        //cenário
        Meetup meetup = createNewMeetup();

        //execução
        Meetup savedMeetup = meetupRepository.save(meetup);

        //assert - id não nulo
        assertThat(savedMeetup.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should delete a Meetup with success")
    public void deleteMeetup() {

        //cenário
        Meetup meetup = createNewMeetup();

        //execução
        testEntityManager.persist(meetup);

        Meetup foundMeetup = testEntityManager.find(Meetup.class, meetup.getId());
        meetupRepository.delete(foundMeetup);
        System.out.println(foundMeetup);

        System.out.println("\n");

        Meetup deletedMeetup = testEntityManager.find(Meetup.class, meetup.getId());

        System.out.println(deletedMeetup); //null

        //assert - id null
        assertThat(deletedMeetup).isNull();
    }

    public Meetup createNewMeetup(){
        return new Meetup(null, "Palestra 1", LocalDate.now().toString());
    }
}
