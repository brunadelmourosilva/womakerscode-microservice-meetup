package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.repositories.MeetupRepository;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {RegistrationController.class})
@AutoConfigureMockMvc
public class MeetupControllerTest {

    static String REGISTRATION_API = "/api/registrations";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MeetupServiceImpl meetupService;

    @Autowired
    MeetupRepository meetupRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Should get Meetup information")
    public void saveMeetupTest() throws Exception {

        // cenario
        Meetup meetup = createValidMeetup();

        Meetup savedMeetup = createValidMeetup();


        // execucao
        BDDMockito.given(meetupService.saveMeetup(any(Meetup.class))).willReturn(savedMeetup);


        String json  = new ObjectMapper().writeValueAsString(meetup);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(REGISTRATION_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //assert
        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(101))
                .andExpect(jsonPath("event").value(meetup.getEvent()))
                .andExpect(jsonPath("meetupDate").value(meetup.getMeetupDate()));
    }

    @Test
    @DisplayName("Should delete the Registration")
    public void deleteRegistration() throws Exception {

        Meetup meetup = createValidMeetup();

        BDDMockito.given(meetupService
                        .findMeetupById(anyInt()))
                .willReturn(meetup);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(REGISTRATION_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    public Meetup createValidMeetup() {
        return new Meetup(101, "Evento de testes", "29/05/2022");
    }

}