package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationResponseDTO;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.services.RegistrationService;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {RegistrationController.class})
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    static String REGISTRATION_API = "/api/registrations";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegistrationServiceImpl registrationService;

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Should get registration information")
    public void saveRegistrationTest() throws Exception {

        Integer id = 1;
        //cenário
        Registration saveRegistration =
                new Registration(
                        createValidRegistration().getName(),
                        createValidRegistration().getEmail(),
                        createValidRegistration().getPassword(),
                        createValidRegistration().getNumber()
                );

        Registration savedRegistration = createValidRegistration();

        //execução
        Mockito.when(registrationRepository.save(saveRegistration)).thenReturn(savedRegistration);


        //assert
        mockMvc.perform(post(REGISTRATION_API)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(saveRegistration))
                )
                .andExpect(status().isOk());
    }

    public Registration createValidRegistration() {
        return new Registration(1, "Vitória", "vitoria@gmail.com", "123", "001");
    }

}