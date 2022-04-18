package com.brunadelmouro.microservicemeetup.models.dto;

public class RegistrationResponseDTO {

    private Integer id;
    private String name;
    private String email;
    private String dateOfRegistration;
    private String registrationNumber;

    public RegistrationResponseDTO() {
    }

    public RegistrationResponseDTO(Integer id, String name, String email, String dateOfRegistration, String registrationNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfRegistration = dateOfRegistration;
        this.registrationNumber = registrationNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
