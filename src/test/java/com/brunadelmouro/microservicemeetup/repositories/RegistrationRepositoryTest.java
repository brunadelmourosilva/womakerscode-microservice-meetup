package com.brunadelmouro.microservicemeetup.repositories;

import com.brunadelmouro.microservicemeetup.models.Registration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class RegistrationRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    RegistrationRepository registrationRepository;

    @Test
    @DisplayName("Should return true when exists a registration already created")
    public void returnTrueWhenRegistrationByNumberExistsTest(){

        //cenário
        String number = "001";
        Registration registration = createNewRegistration(number);

        //execução
        testEntityManager.persist(registration);

        Registration exists = registrationRepository.findByNumber(number);

        boolean verify = false;
        if(exists != null)
            verify = true;

        //assert
        assertThat(verify).isTrue();
    }

    @Test
    @DisplayName("Should return false when doesn't exists a number with a registration already created")
    public void returnFalseWhenRegistrationNumberDoesntExistsTest() {

        //cenário
        String number = "001";

        //execução
        Registration exists = registrationRepository.findByNumber(number);
        System.out.println(exists);

        boolean verify = false;
        if(exists != null)
            verify = true;

        //assert
        assertThat(verify).isFalse();
    }

    @Test
    @DisplayName("Should return a registration by id with success")
    public void findRegistrationByIdTest() {

        //cenário
        Registration registration = createNewRegistration("323");

        //execução
        testEntityManager.persist(registration);

        Optional<Registration> foundRegistration = registrationRepository.findById(registration.getId());
        System.out.println(foundRegistration);

        //assert
        assertThat(foundRegistration.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should save a registration with success")
    public void saveRegistrationTest() {

        //cenário
        Registration registration = createNewRegistration("323");

        //execução
        Registration savedRegistration = registrationRepository.save(registration);

        //assert - id não nulo
        assertThat(savedRegistration.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should delete a registration with success")
    public void deleteRegistration() {

        //cenário
        Registration registration = createNewRegistration("323");

        //execução
        testEntityManager.persist(registration);

        Registration foundRegistration = testEntityManager.find(Registration.class, registration.getId());
        registrationRepository.delete(foundRegistration);
        System.out.println(foundRegistration);

        System.out.println("\n");

        //deve retornar o id null, demonstrando que o registro foi excluído
        Registration deletedRegistration = testEntityManager.find(Registration.class, registration.getId());
        System.out.println(deletedRegistration); //null

        //assert - id null
        assertThat(deletedRegistration).isNull();
    }

    public Registration createNewRegistration(String number){
        return new Registration(null, "Bruna", "bruna@gmail.com", "123", number);
    }
}
