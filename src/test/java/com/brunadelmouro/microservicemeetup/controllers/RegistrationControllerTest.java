package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationResponseDTO;
import com.brunadelmouro.microservicemeetup.services.RegistrationService;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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

import java.time.LocalDate;
import java.util.Optional;

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

    @Test
    @DisplayName("Should get registration information")
    public void getRegistrationTest() throws Exception {

        Integer id = 1;
        //cenário
        Registration registration = new Registration(
                id,
                createValidRegistration().getName(),
                createValidRegistration().getEmail(),
                createValidRegistration().getDateOfRegistration(),
                createValidRegistration().getRegistrationNumber());

        //execução
        BDDMockito.given(registrationService.findRegistrationById(id)).willReturn(registration);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(REGISTRATION_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        //assert
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("name").value(createValidRegistration().getName()))
                .andExpect(jsonPath("email").value(createValidRegistration().getEmail()))
                .andExpect(jsonPath("dateOfRegistration").value(createValidRegistration().getDateOfRegistration()))
                .andExpect(jsonPath("number").value(createValidRegistration().getRegistrationNumber()));

    }

    public RegistrationResponseDTO createValidRegistration() {
        return new RegistrationResponseDTO(1, "Vitória", LocalDate.now().toString(), "vitoria@gmail.com", "001");
    }

}