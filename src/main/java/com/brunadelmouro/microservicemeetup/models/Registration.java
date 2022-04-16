package com.brunadelmouro.microservicemeetup.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Registration {

    @Id
    @Column(name = "registration_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "person_email")
    private String email;

    @Column(name = "date_of_registration")
    private String dateOfRegistration;

    @Column
    private String registrationNumber; //substituir pelo id da tabela original

    @Column
    @OneToMany(mappedBy = "registrationNumber")
    private List<Meetup> meetups;

    public Registration() {
    }

    public Registration(Integer id, String name, String email, String dateOfRegistration, String registrationNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfRegistration = dateOfRegistration;
        this.registrationNumber = registrationNumber;
    }

    public List<Meetup> getMeetups() {
        return meetups;
    }

    public void setMeetups(List<Meetup> meetups) {
        this.meetups = meetups;
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
