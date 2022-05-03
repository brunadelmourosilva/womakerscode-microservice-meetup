package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.repositories.MeetupRepository;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class MeetupServiceTest {

    MeetupService meetupService;

    @MockBean
    MeetupRepository meetupRepository;

    //setup padrão para os testes
    @BeforeEach
    public void setUp(){
        this.meetupService = new MeetupServiceImpl(meetupRepository);
    }

    @Test
    @DisplayName("Should save a Meetup")
    public void saveMeetupTest(){

        //cenário
        Meetup meetup = createValidMeetup();

        //execução
        Mockito.when(meetupRepository.existsByEvent(Mockito.anyString())).thenReturn(false);

        //assert
        assertThat(meetup.getId()).isEqualTo(1);
        assertThat(meetup.getEvent()).isEqualTo("Evento de testes unitários");
        assertThat(meetup.getMeetupDate()).isEqualTo(LocalDate.now().toString());
    }

    @Test
    @DisplayName("Should return a Meetup by id")
    public void findMeetupByIdTest(){

        //cenário
        Meetup meetup = createValidMeetup();

        //execução
        Mockito.when(meetupRepository.findById(1)).thenReturn(Optional.of(meetup));

        //assert
        assertThat(meetupService.findMeetupById(1)).isEqualTo(meetup);
    }

    @Test
    @DisplayName("Should return a Meetup by id")
    public void findMeetupsTest(){

        //cenário
        Meetup meetup1 = createValidMeetup();
        Meetup meetup2 = createValidMeetup();

        //execução
        Mockito.when(meetupRepository.findAll()).thenReturn((Arrays.asList(meetup1, meetup2)));

        //assert
        assertThat(meetupService.findMeetups()).isEqualTo(Arrays.asList(meetup1, meetup2));
    }

    @Test
    @DisplayName("Should update a Meetup")
    public void updateMeetup() {

        //cenário
        Integer id = 101;
        Meetup updatingMeetup = createValidMeetup();


        //execução
        Meetup updatedMeetup = createValidMeetup();
        updatedMeetup.setId(id);

        Mockito.when(meetupRepository.save(updatingMeetup)).thenReturn(updatedMeetup);

        Meetup meetup = meetupService.updateMeetup(updatingMeetup);

        //assert
        assertThat(meetup.getId()).isEqualTo(updatedMeetup.getId());
        assertThat(meetup.getEvent()).isEqualTo(updatedMeetup.getEvent());
        assertThat(meetup.getMeetupDate()).isEqualTo(updatedMeetup.getMeetupDate());
    }

    @Test
    @DisplayName("Should delete a Meetup")
    public void deleteMeetupTest(){

        //cenário
        Meetup meetup = createValidMeetup();

        //assert
        assertDoesNotThrow(() -> meetupService.deleteMeetup(meetup));

        verify(meetupRepository, times(1)).delete(meetup);
    }


    public Meetup createValidMeetup(){
        return new Meetup(101, "Evento de testes unitários", LocalDate.now().toString());
    }
}
