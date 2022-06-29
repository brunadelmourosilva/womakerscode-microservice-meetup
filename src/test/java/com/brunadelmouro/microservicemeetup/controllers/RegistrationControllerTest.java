package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.MicroservicemeetupApplication;
import com.brunadelmouro.microservicemeetup.config.SecurityConfig;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationRequestDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationResponseDTO;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.security.JWTUtil;
import com.brunadelmouro.microservicemeetup.services.impl.EmailServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MicroservicemeetupApplication.class)
@ActiveProfiles("test")
public class RegistrationControllerTest {

    static final String REGISTRATION_API = "/api/registrations/";

    MockMvc mockMvc;

    @Autowired
    RegistrationController registrationController;

    RegistrationServiceImpl registrationService;

    ObjectMapper objectMapper;

    Registration registration;

    RegistrationRequestDTO registrationRequestDTO;

    RegistrationResponseDTO registrationResponseDTO;

    @MockBean
    RegistrationRepository registrationRepository;

    @MockBean
    BCryptPasswordEncoder encoder;

    @MockBean
    EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();

        registrationRequestDTO = new RegistrationRequestDTO();
        registration = new Registration(1, "Vitória", "vitoria@gmail.com", "123", "001");
        BeanUtils.copyProperties(registration, registrationRequestDTO);
        registrationResponseDTO = new RegistrationResponseDTO();
        registrationResponseDTO.setId(1);

        objectMapper = new ObjectMapper();
        registrationController = new RegistrationController(registrationService);
        registrationService = new RegistrationServiceImpl(registrationRepository, encoder, emailService);
    }

    @Test
    @DisplayName("Should get Registration information")
    public void saveRegistrationTest() throws Exception {
        given(registrationRepository.save(any())).willReturn(registration);

        mockMvc
                .perform(MockMvcRequestBuilders.post(REGISTRATION_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
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