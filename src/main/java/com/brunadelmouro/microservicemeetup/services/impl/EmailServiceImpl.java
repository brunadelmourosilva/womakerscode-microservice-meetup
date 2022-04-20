package com.brunadelmouro.microservicemeetup.services.impl;

import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EmailServiceImpl implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendEmail(Registration registration) {
        SimpleMailMessage message =prepareSimpleMailMessageFromRegistration(registration);
        mailSender.send(message);
        System.out.println("Enviou e-mail!");
    }

    @Override
    public SimpleMailMessage prepareSimpleMailMessageFromRegistration(Registration registration) {
        SimpleMailMessage sm =new SimpleMailMessage();

        //INSERIR HTML E CUSTOMIZAR MENSAGEM
        sm.setFrom(sender);
        sm.setTo(registration.getEmail());
        sm.setSubject("Your registration was created with success!");
        sm.setText("Hello " + registration.getName() + "! \n You already subscribed on meetup!");

        return sm;
    }
}
