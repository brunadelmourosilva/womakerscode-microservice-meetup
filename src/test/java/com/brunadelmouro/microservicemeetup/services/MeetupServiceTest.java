package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.exceptions.ObjectNotFoundException;
import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.repositories.MeetupRepository;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@MockitoSettings(strictness = Strictness.LENIENT)
class MeetupServiceTest {

    @InjectMocks
    private MeetupServiceImpl meetupService;

    @Mock
    private MeetupRepository meetupRepository;

    private Meetup meetup;
    private Meetup meetup2;
    List<Meetup> meetups;

    //setup padrão para os testes
    @BeforeEach
    void setUp(){
        meetup = new Meetup(1, "Teste", "20/07/2022");
        meetup2 = new Meetup(2, "Teste 2", "20/07/2022");
        meetups = List.of(meetup, meetup2);
    }

    @Test
    @DisplayName("Should save a Meetup")
    void saveMeetupWithSuccessTest(){
        //given
        given(meetupRepository.save(meetup)).willReturn(meetup);

        //when
        Meetup response = meetupService.saveMeetup(meetup);

        //then
        then(meetupRepository).should(times(1)).save(meetup);

        assertEquals("Teste", response.getEvent());
    }

    @Test
    void saveMeetupWhenMeetupAlreadyExistsTest(){
        //given
        doThrow(IllegalArgumentException.class).when(meetupRepository).findAll();

        //when
        assertThrows(IllegalArgumentException.class, () -> {
            meetupService.saveMeetup(meetup);
        });

        //then
        then(meetupRepository).should(times(0)).save(meetup);
    }

    @Test
    @DisplayName("Should return a Meetup by id")
    void findMeetupByIdWithSuccessTest(){
        //given
        given(meetupRepository.findById(anyInt())).willReturn(Optional.of(meetup));

        //when
        final var response = meetupService.findMeetupById(meetup.getId());

        //then
        then(meetupRepository).should().findById(anyInt());

        assertEquals("Teste", response.getEvent());
    }

    @Test
    @DisplayName("Should return a Meetup by id")
    void findMeetupByIdWhenIdWasNotFoundTest(){
        //given
        given(meetupRepository.findById(anyInt())).willThrow(ObjectNotFoundException.class);

        //when
        assertThrows(ObjectNotFoundException.class, () -> {
            meetupService.findMeetupById(anyInt());
        });

        //then
        then(meetupRepository).should(times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Should return a Meetup by id")
    void findMeetupsWithSuccessTest(){

        //given
        given(meetupRepository.findAll()).willReturn(meetups);

        //when
        final var response = meetupService.findMeetups();

        //then
        then(meetupRepository).should().findAll();

        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("Should update a Meetup")
    void updateMeetupWithSuccessTest() {
        //given
        given(meetupRepository.findById(meetup2.getId())).willReturn(Optional.of(meetup2));
        given(meetupRepository.save(meetup2)).willReturn(meetup2);

        //when
        final var response = meetupService.updateMeetup(meetup2);

        //then
        then(meetupRepository).should().findById(meetup2.getId());
        then(meetupRepository).should().save(meetup2);

        assertEquals("Teste 2", response.getEvent());
    }

    @Test
    void updateMeetupWhenMeetupIdNotFoundTest(){
        //given
        doThrow(ObjectNotFoundException.class).when(meetupRepository).findById(anyInt());

        //when
        assertThrows(ObjectNotFoundException.class, () -> {
            meetupService.updateMeetup(meetup);
        });

        //then
        then(meetupRepository).should(times(0)).save(meetup);
    }

    @Test
    @DisplayName("Should delete a Meetup")
    void deleteMeetupTest(){
        //given
        //doNothing().when(meetupRepository).delete(meetup);
        //when
        assertDoesNotThrow(() -> meetupService.deleteMeetup(meetup));

        then(meetupRepository).should().delete(meetup);
    }

}
