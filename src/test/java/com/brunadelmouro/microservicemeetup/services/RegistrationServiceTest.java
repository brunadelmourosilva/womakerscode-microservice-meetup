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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RegistrationServiceTest {

    @InjectMocks
    RegistrationServiceImpl registrationService;

    @Mock
    RegistrationRepository registrationRepository;

    @Mock
    EmailServiceImpl emailService;

    Registration registration;

    @BeforeEach
    public void setUp(){
        registration = new Registration(1, "Bruna", "brunadelmouro@gmail.com", "123", "001");
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

        //cenário
        Registration registration = createValidRegistration();
        PageRequest pageRequest = PageRequest.of(0,10);

        List<Registration> listRegistrations = Arrays.asList(registration);
        Page<Registration> page = new PageImpl<>(Arrays.asList(registration),
                PageRequest.of(0,10), 1);

        //execução
        Mockito.when(registrationRepository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);

        Page<Registration> result = registrationService.findRegistrationPage(registration, pageRequest);

        //assert
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(listRegistrations);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }


    //cenário de erro
    @Test
    @DisplayName("Should return empty when get Registration by id doesn't exists")
    public void registrationNotFoundByIdTest(){

        //cenário
        Integer id = 101;
        Mockito.when(registrationRepository.findById(id)).thenReturn(Optional.empty());

        //execução
        Registration registration = registrationService.findRegistrationById(id);
        Optional<Registration> foundRegistrationOp = Optional.ofNullable(registration);

        //assert
        assertThat(foundRegistrationOp.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should update a Registration")
    public void updateRegistration() {

        //cenário
        Integer id = 101;
        Registration updatingRegistration = createValidRegistration();


        //execução
        Registration updatedRegistration = createValidRegistration();
        updatedRegistration.setId(id);

        Mockito.when(registrationRepository.save(updatingRegistration)).thenReturn(updatedRegistration);

        Registration registration = registrationService.updateRegistration(updatingRegistration);

        //assert
        assertThat(registration.getId()).isEqualTo(updatedRegistration.getId());
        assertThat(registration.getName()).isEqualTo(updatedRegistration.getName());
        assertThat(registration.getEmail()).isEqualTo(updatedRegistration.getEmail());
        assertThat(registration.getPassword()).isEqualTo(updatedRegistration.getPassword());
        assertThat(registration.getNumber()).isEqualTo(updatedRegistration.getNumber());

    }

    @Test
    @DisplayName("Should delete a Registration")
    public void deleteRegistrationTest(){

        //cenário
        Registration registration = createValidRegistration();

        //assert
        assertDoesNotThrow(() -> registrationService.deleteRegistration(registration));

        verify(registrationRepository, times(1)).delete(registration);
    }

    public Registration createValidRegistration(){
        return new Registration(101, "Bruna", "bruna@inovags.com", "123", "001");
    }
}
