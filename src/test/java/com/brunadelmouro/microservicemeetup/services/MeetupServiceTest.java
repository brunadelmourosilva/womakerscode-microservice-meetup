package com.brunadelmouro.microservicemeetup.services;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class MeetupServiceTest {

    @InjectMocks
    private MeetupServiceImpl meetupService;

    @Mock
    private MeetupRepository meetupRepository;

    private Meetup meetup;

    //setup padrão para os testes
    @BeforeEach
    void setUp(){
        meetup = new Meetup(1, "Teste", "20/07/2022");
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
    void findMeetupByIdTest(){

        //cenário
        Meetup meetup = createValidMeetup();

        //execução
        Mockito.when(meetupRepository.findById(1)).thenReturn(Optional.of(meetup));

        //assert
        assertThat(meetupService.findMeetupById(1)).isEqualTo(meetup);
    }

    @Test
    @DisplayName("Should return a Meetup by id")
    void findMeetupsTest(){

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
    void updateMeetup() {

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
    void deleteMeetupTest(){

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
