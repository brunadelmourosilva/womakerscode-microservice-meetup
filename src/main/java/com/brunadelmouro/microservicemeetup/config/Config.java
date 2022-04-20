package com.brunadelmouro.microservicemeetup.config;

import com.brunadelmouro.microservicemeetup.services.MeetupService;
import com.brunadelmouro.microservicemeetup.services.impl.EmailServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public MeetupServiceImpl meetupService(){
        return new MeetupServiceImpl();
    }

    @Bean
    public RegistrationServiceImpl registrationService(){
        return new RegistrationServiceImpl();
    }

    @Bean
    public EmailServiceImpl emailService(){
        return new EmailServiceImpl();
    }

}
