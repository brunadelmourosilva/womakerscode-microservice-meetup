package com.brunadelmouro.microservicemeetup.services.impl;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendEmail(Registration registration) {
        SimpleMailMessage message = prepareSimpleMailMessageFromRegistration(registration);
        mailSender.send(message);

        logger.info("E-mail sent!");
    }

    public void sendEmail(Meetup meetup, Registration registration){
        SimpleMailMessage message = prepareSimpleMailMessageFromMeetup(meetup, registration);
        mailSender.send(message);

        logger.info("E-mail sent!");
    }

    @Override
    public SimpleMailMessage prepareSimpleMailMessageFromRegistration(Registration registration) {
        SimpleMailMessage sm =new SimpleMailMessage();

        sm.setFrom(sender);

        sm.setTo(registration.getEmail());

        sm.setSubject("Your registration was created with success!");

        sm.setText("Hello, " + registration.getName() + "! " +
                   "\n\nYour account has been successfully created on WoMakersCode's meetup platform." +
                   "\n\nBest regards!");

        return sm;
    }

    public SimpleMailMessage prepareSimpleMailMessageFromMeetup(Meetup meetup, Registration registration){
        SimpleMailMessage sm = new SimpleMailMessage();

        sm.setFrom(sender);

        sm.setTo(registration.getEmail());

        sm.setSubject("Event confirmation");

        sm.setText("Hello " + registration.getName() + "! \n\n" +
                   "Here is your confirmation for the following event: " + meetup.getEvent() + "\n\n" +
                   "*** Details *** \n\n" +
                   "Event: " + meetup.getEvent() + "\n" +
                   "Meetup date: " + meetup.getMeetupDate() + "\n" +
                   "Registration number: " + registration.getNumber() +
                   "\n\nBest regards!" + "\n");

        return sm;
    }
}
