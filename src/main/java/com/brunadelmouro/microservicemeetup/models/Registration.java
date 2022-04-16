package com.brunadelmouro.microservicemeetup.models;

public class Registration {

    private Integer id;
    private String name;
    private String email;
    private String dateOfRegistration;
    private String registration; //substituir pelo id da tabela original

    //private List<Meetup> meetups;

    public Registration() {
    }

    public Registration(Integer id, String name, String email, String dateOfRegistration, String registration) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfRegistration = dateOfRegistration;
        this.registration = registration;
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

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }
}
