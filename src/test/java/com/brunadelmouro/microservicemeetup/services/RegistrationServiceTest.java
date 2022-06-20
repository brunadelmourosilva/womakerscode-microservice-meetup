package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.exceptions.ObjectNotFoundException;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.services.impl.EmailServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@MockitoSettings(strictness = Strictness.LENIENT)
public class RegistrationServiceTest {

    @InjectMocks
    RegistrationServiceImpl registrationService;

    @Mock
    RegistrationRepository registrationRepository;

    @Mock
    EmailServiceImpl emailService;

    Registration registration;
    Page<Registration> registrationPage;

    @BeforeEach
    public void setUp(){
        registration = new Registration(1, "Bruna", "brunadelmouro@gmail.com", "123", "001");

        registrationPage = new PageImpl<>(List.of(registration), PageRequest.of(1, 1), 1);
    }

    @Test
    @DisplayName("Should save a Registration")
    public void saveRegistrationWithSuccessTest(){

        //given
        given(registrationRepository.existsByEmail(registration.getEmail())).willReturn(false);
        given(registrationRepository.existsByNumber(registration.getNumber())).willReturn(false);
        //emailService.sendEmail(registration);
        given(registrationRepository.save(registration)).willReturn(registration);

        //when
        Registration response = registrationService.saveRegistration(registration);

        //then
        then(registrationRepository).should().existsByEmail(registration.getEmail());
        then(registrationRepository).should().existsByNumber(registration.getNumber());
        then(registrationRepository).should().save(registration);

        assertEquals("Bruna", response.getName());
        assertEquals("brunadelmouro@gmail.com", response.getEmail());
    }

    //TODO create a test to not save a registration

    @Test
    @DisplayName("Should return a Registration by id")
    public void findRegistrationByIdWithSuccessTest(){
        //given
        given(registrationRepository.findById(registration.getId())).willReturn(Optional.of(registration));

        //when
        Registration response = registrationService.findRegistrationById(registration.getId());

        //then
        then(registrationRepository).should().findById(registration.getId());

        assertEquals("brunadelmouro@gmail.com", response.getEmail());
    }

    @Test
    public void findRegistrationByIdWhenIdWasNotFoundTest(){
        //given
        given(registrationRepository.findById(anyInt())).willThrow(new ObjectNotFoundException("Registration not found."));

        //when
        assertThrows(ObjectNotFoundException.class, () -> {
            registrationService.findRegistrationById(anyInt());
        });

        //then
        then(registrationRepository).should().findById(anyInt());
    }


    @Test
    @DisplayName("Should get a Registration by number")
    public void findRegistrationByRegistrationNumberWithSuccessTest(){
        //given
        given(registrationRepository.findByNumber(registration.getNumber())).willReturn(registration);

        //when
        Registration response = registrationService.findRegistrationByRegistrationNumber(registration.getNumber());

        //then
        then(registrationRepository).should().findByNumber(registration.getNumber());

        assertEquals("Bruna", response.getName());
        assertEquals("001", response.getNumber());
    }

    @Test
    @DisplayName("Should get a Registration by number")
    public void findRegistrationByRegistrationNumberWhenNumberWasNotFoundTest(){
        //given
        given(registrationRepository.findByNumber(anyString())).willThrow(IllegalArgumentException.class);

        //when
        assertThrows(IllegalArgumentException.class, () -> {
            registrationService.findRegistrationByRegistrationNumber(anyString());
        });

        //then
        then(registrationRepository).should().findByNumber(anyString());
    }


    @Test
    @DisplayName("Should filter Registrations by Page")
    public void findRegistrationByPageTest() {

        //given
        given(registrationRepository.findAll(any(Example.class), any(PageRequest.class))).willReturn(registrationPage);

        //when
        Page<Registration> responsePage = registrationService.findRegistrationPage(registration, registrationPage.getPageable());

        //then
        then(registrationRepository).should().findAll(any(Example.class), any(PageRequest.class));

        assertEquals(1, responsePage.getSize());
        assertEquals("Bruna", responsePage.getContent().get(0).getName());
    }

    @Test
    @DisplayName("Should update a Registration")
    public void updateRegistrationWithSuccessTest() {
        //given
        given(registrationRepository.findById(registration.getId())).willReturn(Optional.of(registration)); //retornar o antigo registration
        given(registrationRepository.save(registration)).willReturn(registration);

        //when
        Registration response = registrationService.updateRegistration(registration);

        //then
        then(registrationRepository).should().findById(registration.getId());
        then(registrationRepository).should().save(registration);

        assertEquals("123", response.getPassword());

    }

    @Test
    @DisplayName("Should delete a Registration")
    public void deleteRegistrationTest(){

        //given
        given(registrationRepository.findById(1)).willReturn(Optional.of(registration));

        //when
        assertDoesNotThrow(() -> registrationService.deleteRegistration(registration));

        then(registrationRepository).should().delete(registration);
    }
}
