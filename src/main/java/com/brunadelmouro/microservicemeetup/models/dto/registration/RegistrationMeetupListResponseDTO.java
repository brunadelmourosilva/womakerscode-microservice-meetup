package com.brunadelmouro.microservicemeetup.models.dto.registration;

public class RegistrationMeetupListResponseDTO {

    private String name;
    private String email;

    public RegistrationMeetupListResponseDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
