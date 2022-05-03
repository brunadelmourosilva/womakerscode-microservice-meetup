package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RegistrationServiceTest {

    RegistrationService registrationService;

    @MockBean
    RegistrationRepository registrationRepository;

    //setup padrão para os testes
    @BeforeEach
    public void setUp(){
        this.registrationService = new RegistrationServiceImpl(registrationRepository);
    }

    @Test
    @DisplayName("Should save a Registration")
    public void saveRegistrationTest(){

        //cenário
        Registration registration = createValidRegistration();

        //execução
        Mockito.when(registrationRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        //assert
        assertThat(registration.getId()).isEqualTo(101);
        assertThat(registration.getName()).isEqualTo("Bruna");
        assertThat(registration.getEmail()).isEqualTo("bruna@inovags.com");
        assertThat(registration.getPassword()).isEqualTo("123");
        assertThat(registration.getNumber()).isEqualTo("001");
    }

    @Test
    @DisplayName("Should return a Registration by id")
    public void findRegistrationByIdTest(){
        Registration registration = createValidRegistration();

        Mockito.when(registrationRepository.findById(1)).thenReturn(Optional.of(registration));

        assertThat(registrationService.findRegistrationById(1)).isEqualTo(registration);
    }


    @Test
    @DisplayName("Should get a Registration by number")
    public void findRegistrationByRegistrationNumber(){

        String numberAttribute = "001";

        Mockito.when(registrationRepository.findByNumber(numberAttribute))
                .thenReturn(new Registration(1, numberAttribute));

        Registration registration = registrationService.findRegistrationByRegistrationNumber(numberAttribute);
        Optional<Registration> foundRegistrationOp = Optional.ofNullable(registration);

        assertThat(foundRegistrationOp.isPresent()).isTrue();
        assertThat(foundRegistrationOp.get().getId()).isEqualTo(1);
        assertThat(foundRegistrationOp.get().getNumber()).isEqualTo(numberAttribute);
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
