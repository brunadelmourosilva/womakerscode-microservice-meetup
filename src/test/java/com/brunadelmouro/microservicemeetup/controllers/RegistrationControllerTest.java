package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.config.SecurityConfig;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({RegistrationController.class})
@ActiveProfiles("test")
public class RegistrationControllerTest {

    static String REGISTRATION_API = "/api/registrations/";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegistrationServiceImpl registrationService;

    @Autowired
    ObjectMapper objectMapper;

    Registration registration;

    @AfterEach
    void tearDown() {
        reset(registrationService);
    }

    @BeforeEach
    void setUp() {
        registration = new Registration(1, "Vitória", "vitoria@gmail.com", "123", "001");
    }

    @Test
    @DisplayName("Should get Registration information")
    public void saveRegistrationTest() throws Exception {

        // given
        given(registrationService.saveRegistration(any(Registration.class))).willReturn(registration);

        //when - then
        mockMvc
                .perform(MockMvcRequestBuilders.post(REGISTRATION_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(objectMapper.writeValueAsString(registration)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    @DisplayName("Should delete the Registration")
    public void deleteRegistration() throws Exception {

        Registration registration = createValidRegistration();

        given(registrationService
                        .findRegistrationById(anyInt()))
                .willReturn(registration);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(REGISTRATION_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    public Registration createValidRegistration() {
        return new Registration(101, "Vitória", "vitoria@gmail.com", "123", "001");
    }

}