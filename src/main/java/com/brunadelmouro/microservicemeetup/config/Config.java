package com.brunadelmouro.microservicemeetup.config;

import com.brunadelmouro.microservicemeetup.services.impl.EmailServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public MeetupServiceImpl meetupServiceImpl(){
        return new MeetupServiceImpl();
    }

    @Bean
    public RegistrationServiceImpl registrationServiceImpl(){
        return new RegistrationServiceImpl();
    }

    @Bean
    public EmailServiceImpl emailServiceImpl(){
        return new EmailServiceImpl();
    }
}
