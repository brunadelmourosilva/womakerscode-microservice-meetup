package com.brunadelmouro.microservicemeetup.services;

import com.brunadelmouro.microservicemeetup.models.Registration;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationMeetupListResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationMeetupUpdateResponseDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationRequestDTO;
import com.brunadelmouro.microservicemeetup.models.dto.registration.RegistrationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RegistrationService {

    Registration saveRegistration(Registration registration);

    Registration findRegistrationById(Integer id);

    Registration findRegistrationByRegistrationNumber(String registrationNumber);

    Page<Registration> findRegistrationPage(Registration filter, Pageable pageRequest);

    Registration updateRegistration(Registration registration);

    void deleteRegistration(Registration registration);

    void validateRegistrationExistsByEmail(String email);

    Registration validateRegistrationExists(Registration registration);

    RegistrationResponseDTO convertEntityToResponseDTO(Registration registration);

    List<RegistrationMeetupListResponseDTO> convertEntityToRegistrationMeetupListResponseDTO(List<Registration> registrationList);

    RegistrationMeetupUpdateResponseDTO convertEntityToRegistrationMeetupUpdateResponseDTO(Registration registration);

    Registration convertDtoToEntity(RegistrationRequestDTO registrationRequestDTO);

}
