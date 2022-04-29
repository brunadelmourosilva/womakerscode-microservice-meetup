package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.exceptions.StandardError;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
    @DisplayName("Should save a Registration")
    public void saveRegistrationTest(){

        //cenário
        Registration registration = createValidRegistration();

        //execução

        Mockito.when(registrationRepository.save(registration)).thenReturn(createValidRegistration());
        Mockito.when(registrationService.validateRegistrationExistsByEmail(Mockito.anyString())).thenReturn(false);

        //salvar os dados no service
        Registration savedRegistration = registrationService.saveRegistration(registration);

        //assert
        //garantir que os dados serão retornados de acordo com o que foi salvo pelo objeto
        Assertions.assertThat(savedRegistration.getId()).isEqualTo(101);
        Assertions.assertThat(savedRegistration.getName()).isEqualTo("Vitória");
        Assertions.assertThat(savedRegistration.getEmail()).isEqualTo("vitoria@gmail.com");
        Assertions.assertThat(savedRegistration.getPassword()).isEqualTo("123");
        Assertions.assertThat(savedRegistration.getNumber()).isEqualTo("001");
    }

    @Test
    @DisplayName("Should get a Registration by Id")
    public void getRegistrationByIdTest() {

        // cenário
        Integer id = 01;
        Registration registration = createValidRegistration();
        registration.setId(id);

        //encontre o id setado
        Mockito.when(registrationRepository.findById(id)).thenReturn(Optional.of(registration));

        // execução
        Registration foundRegistration = registrationService.findRegistrationById(id);

        System.out.println(foundRegistration);
        System.out.println();
        System.out.println();

        Optional<Registration> foundRegistrationOp = Optional.ofNullable(foundRegistration);

        //o objeto precisa estar presente e validado como true
        Assertions.assertThat(foundRegistrationOp.isPresent()).isTrue();

        Assertions.assertThat(foundRegistrationOp.get().getId()).isEqualTo(id);
        Assertions.assertThat(foundRegistrationOp.get().getName()).isEqualTo(registration.getName());
        Assertions.assertThat(foundRegistrationOp.get().getEmail()).isEqualTo(registration.getEmail());
        Assertions.assertThat(foundRegistrationOp.get().getPassword()).isEqualTo(registration.getPassword());
        Assertions.assertThat(foundRegistrationOp.get().getNumber()).isEqualTo(registration.getNumber());
    }

    @Test
    @DisplayName("Should get a Registration by number")
    public void getRegistrationByRegistration(){

        String numberAttribute = "1234";

        Mockito.when(registrationRepository.findByNumber(numberAttribute))
                .thenReturn(new Registration(1, numberAttribute));

        Registration registration = registrationService.findRegistrationByRegistrationNumber(numberAttribute);
        Optional<Registration> foundRegistrationOp = Optional.ofNullable(registration);

        Assertions.assertThat(foundRegistrationOp.isPresent()).isTrue();
        Assertions.assertThat(foundRegistrationOp.get().getId()).isEqualTo(1);
        Assertions.assertThat(foundRegistrationOp.get().getNumber()).isEqualTo(numberAttribute);
    }

    //cenário de erro
    @Test
    @DisplayName("Should return empty when get registration by id doesn't exists")
    public void registrationNotFoundByIdTest(){

        // cenário
        Integer id = 11;
        Mockito.when(registrationRepository.findById(id)).thenReturn(Optional.empty());

        // execução
        Registration registration = registrationService.findRegistrationById(id);

        Optional<Registration> foundRegistrationOp = Optional.ofNullable(registration);

        //assert
        Assertions.assertThat(foundRegistrationOp.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should delete a Registration")
    public void deleteRegistrationTest(){

        //cenário
        Registration registration = createValidRegistration();

        //assert
        assertDoesNotThrow(() -> registrationService.deleteRegistration(registration));

        Mockito.verify(registrationRepository, Mockito.times(1)).delete(registration);
    }




    public Registration createValidRegistration(){
        return new Registration(1, "Vitória", "vitoria@gmail.com", "123", "001");
    }
}
