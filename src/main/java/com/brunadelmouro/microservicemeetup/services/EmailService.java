package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmail(Registration registration);

    SimpleMailMessage prepareSimpleMailMessageFromRegistration(Registration registration);

    SimpleMailMessage prepareSimpleMailMessageFromMeetup(Meetup meetup, Registration registration);
}
