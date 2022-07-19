package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.MicroservicemeetupApplication;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationRequestDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationResponseDTO;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.services.impl.EmailServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.assertj.core.api.Assertions;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

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

    @Autowired
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
    @DisplayName("Should save a Registration information")
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
    void getRegistrationByIdTest() throws Exception {
        given(registrationRepository.findById(anyInt())).willReturn(Optional.of(registration));

        mockMvc
                .perform(MockMvcRequestBuilders.get(REGISTRATION_API.concat("1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationResponseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Vitória"));
    }

    //TODO this method isn't working
    @Test
    void getRegistrationByIdWhenIdWasNotFoundTest() throws Exception {
        given(registrationRepository.findById(anyInt())).willReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(NestedServletException.class)
            .isThrownBy(() ->
                mockMvc
                        .perform(MockMvcRequestBuilders.get(REGISTRATION_API.concat("454545")))
                        .andExpect(status().isNotFound())
                ).withMessageContaining("Registration not found.");
    }

    @Test
    void getRegistrationByRegistrationNumberTest() throws Exception {
        given(registrationRepository.findByNumber(anyString())).willReturn(registration);

        mockMvc
                .perform(MockMvcRequestBuilders.get(REGISTRATION_API.concat("registrationNumber/001"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationResponseDTO))) //enter on service class
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Vitória"));
    }

    @Test
    void givenNullRegistration_ThrowsException() {
        given(registrationRepository.findByNumber(registration.getNumber())).willReturn(null);

        Assertions.assertThatExceptionOfType(NestedServletException.class)
                .isThrownBy(() ->
                        mockMvc.perform(MockMvcRequestBuilders.get(REGISTRATION_API.concat("registrationNumber/001")))
                                .andExpect(status().isNotFound())
                ).withMessageContaining("Registration id cannot be null.");
    }

    //TODO make a test to testing a page endpoint





    //    @Test
//    @DisplayName("Should delete the Registration")
//    public void deleteRegistration() throws Exception {
//
//        Registration registration = createValidRegistration();
//
//        given(registrationService
//                        .findRegistrationById(anyInt()))
//                .willReturn(registration);
//
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
//                .delete(REGISTRATION_API.concat("/" + 1))
//                .accept(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isNoContent());
//    }

    public Registration createValidRegistration() {
        return new Registration(101, "Vitória", "vitoria@gmail.com", "123", "001");
    }

}