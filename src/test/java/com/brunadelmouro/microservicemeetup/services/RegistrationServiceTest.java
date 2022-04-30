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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.assertj.core.api.Assertions.assertThat;


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

    //consertar
//    @Test
//    @DisplayName("Should return true when create a Registration with unique e-mail")
//    public void testUniqueEmailWhenCreatingNewRegistration(){
//
//        //cenário
//        String email = "brunadelmouro@gmail.com";
//
//        //execução
//        Mockito.doNothing().when(registrationRepository.existsByEmail(email));
//
//        registrationService.validateRegistrationExistsByEmail(email);
//
//        //assert
//        verify(registrationService,times(1)).validateRegistrationExistsByEmail(email);
//    }

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

    //consertar
//    @Test
//    @DisplayName("Should return a Registration by id")
//    public void findRegistrationByIdTest(){
//        Registration registration = createValidRegistration();
//
//        Mockito.when(registrationRepository.findById(1)).thenReturn(Optional.of(registration));
//
//        assertThat(registrationService.findRegistrationById(1)).isEqualTo(registration);
//    }


//
//    @Test
//    @DisplayName("Should get a Registration by number")
//    public void getRegistrationByRegistration(){
//
//        String numberAttribute = "1234";
//
//        Mockito.when(registrationRepository.findByNumber(numberAttribute))
//                .thenReturn(new Registration(1, numberAttribute));
//
//        Registration registration = registrationService.findRegistrationByRegistrationNumber(numberAttribute);
//        Optional<Registration> foundRegistrationOp = Optional.ofNullable(registration);
//
//        Assertions.assertThat(foundRegistrationOp.isPresent()).isTrue();
//        Assertions.assertThat(foundRegistrationOp.get().getId()).isEqualTo(1);
//        Assertions.assertThat(foundRegistrationOp.get().getNumber()).isEqualTo(numberAttribute);
//    }
//
//    //cenário de erro
//    @Test
//    @DisplayName("Should return empty when get registration by id doesn't exists")
//    public void registrationNotFoundByIdTest(){
//
//        // cenário
//        Integer id = 11;
//        Mockito.when(registrationRepository.findById(id)).thenReturn(Optional.empty());
//
//        // execução
//        Registration registration = registrationService.findRegistrationById(id);
//
//        Optional<Registration> foundRegistrationOp = Optional.ofNullable(registration);
//
//        //assert
//        Assertions.assertThat(foundRegistrationOp.isPresent()).isFalse();
//    }
//
//    @Test
//    @DisplayName("Should delete a Registration")
//    public void deleteRegistrationTest(){
//
//        //cenário
//        Registration registration = createValidRegistration();
//
//        //assert
//        assertDoesNotThrow(() -> registrationService.deleteRegistration(registration));
//
//        Mockito.verify(registrationRepository, Mockito.times(1)).delete(registration);
//    }

    public Registration createValidRegistration(){
        return new Registration(101, "Bruna", "bruna@inovags.com", "123", "001");
    }
}
