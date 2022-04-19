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

@SpringBootApplication
public class MicroservicemeetupApplication implements CommandLineRunner {


	private MeetupServiceImpl meetupService;
	private RegistrationServiceImpl registrationService;

	@Autowired
	public MicroservicemeetupApplication(MeetupServiceImpl meetupService, RegistrationServiceImpl registrationService){
		this.meetupService = meetupService;
		this.registrationService = registrationService;
	}

	public static void main(String[] args) {
		SpringApplication.run(MicroservicemeetupApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		registrationService.saveRegistration(
				new Registration(
						null,
						"Alex",
						"alex@gmail.com",
						"123",
						"18/04/2022",
						"001")
		);

		meetupService.saveMeetup(
				new Meetup(
						null,
						"Festa com tequila",
						"20/04/2022"
		));
	}
}
