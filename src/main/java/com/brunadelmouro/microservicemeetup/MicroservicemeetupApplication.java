package com.brunadelmouro.microservicemeetup;

import com.brunadelmouro.microservicemeetup.models.Meetup;
import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.services.impl.MeetupServiceImpl;
import com.brunadelmouro.microservicemeetup.services.impl.RegistrationServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class MicroservicemeetupApplication {


//	@Autowired
//	private MeetupServiceImpl meetupService;
//
//	@Autowired
//	private RegistrationServiceImpl registrationService;

	public static void main(String[] args) {
		SpringApplication.run(MicroservicemeetupApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		registrationService.saveRegistration(
//				new Registration(
//						null,
//						"Bruna Delmouro",
//						"brunadelmouro@gmail.com",
//						"123",
//						"001")
//		);
//
//		meetupService.saveMeetup(
//				new Meetup(
//						null,
//						"Palestra sobre microservices",
//						"25/04/2022"
//		));
//	}
}
