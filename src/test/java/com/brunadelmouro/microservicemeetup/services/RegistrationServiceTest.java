package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RegistrationServiceTest {

    RegistrationService registrationService;

    @MockBean
    RegistrationRepository registrationRepository;

    //setup padrão para os testes
    @BeforeEach
    public void setUp(){
        this.registrationService = new RegistrationServiceImpl();
    }

    @Test
    @DisplayName("Should save a registration")
    public void saveRegistrationTest(){

        //cenário
        Registration registration = createValidRegistration();

        //execução
        Mockito.when(registrationRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Mockito.when(registrationRepository.save(registration)).thenReturn(createValidRegistration());

        //salvar os dados no service
        Registration savedRegistration = registrationService.saveRegistration(registration);

        //assert
        //garantir que os dados serão retornados de acordo com o que foi salvo pelo objeto
        Assertions.assertThat(savedRegistration.getId()).isEqualTo(101);
        Assertions.assertThat(savedRegistration.getName()).isEqualTo("Bruna Delmouro");
        Assertions.assertThat(savedRegistration.getEmail()).isEqualTo("vitoria@gmail.com");
        Assertions.assertThat(savedRegistration.getPassword()).isEqualTo("123");
        Assertions.assertThat(savedRegistration.getNumber()).isEqualTo("001");
    }

    public Registration createValidRegistration(){
        return new Registration(101, "Bruna", "vitoria@gmail.com", "123", "001");
    }
}
