package com.brunadelmouro.microservicemeetup.config;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.repositories.MeetupRepository;
import com.brunadelmouro.microservicemeetup.repositories.RegistrationRepository;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class DBService {

    @Autowired
    RegistrationServiceImpl registrationService;

    @Autowired
	BCryptPasswordEncoder encoder;

    @Autowired
    MeetupServiceImpl meetupService;

    public void instantiateTestDatabase() throws ParseException {

        registrationService.saveRegistration(
				new Registration(
						null,
						"Bruna Delmouro",
						"brunadelmouro@gmail.com",
						encoder.encode("123"),
						"001")
		);

		meetupService.saveMeetup(
				new Meetup(
						null,
						"Palestra sobre microsserviços",
						"29/04/2022"
		));
    }
}
