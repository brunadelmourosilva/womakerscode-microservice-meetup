package com.brunadelmouro.microservicemeetup.models;

import com.brunadelmouro.microservicemeetup.models.enums.Profile;
import com.brunadelmouro.microservicemeetup.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Registration implements Serializable {

    @Id
    @Column(name = "registration_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "person_email")
    private String email;

    @Column(name = "person_password")
    private String password;

    @Column(name = "date_of_registration")
    private String dateOfRegistration;

    @Column
    private String number; //substituir pelo id da tabela original

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="PROFILES")
    private Set<Integer> profiles = new HashSet<>();

    @JsonIgnore
    @Column
    @ManyToMany
    @JoinTable(name = "REGISTRATION_HAS_MEETUP",
            joinColumns = @JoinColumn(name = "registration_id"),
            inverseJoinColumns = @JoinColumn(name = "meetup_id"))
    private List<Meetup> meetups;

    public Registration() {
        addProfiles(Profile.REGISTRATION);
    }

    public Registration(String name, String email, String password, String number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfRegistration = DateUtils.convertSystemTimeMillisToString(System.currentTimeMillis());
        this.number = number;
        addProfiles(Profile.REGISTRATION);
    }

    public Registration(Integer id, String number) {
        this.id = id;
        this.number = number;
        addProfiles(Profile.REGISTRATION);
    }

    public Registration(Integer id, String name, String email, String password, String registrationNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfRegistration = DateUtils.convertSystemTimeMillisToString(System.currentTimeMillis());
        this.number = registrationNumber;
        addProfiles(Profile.REGISTRATION);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Set<Profile> getProfile() {
        return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfiles(Profile profile) {
        profiles.add(profile.getCod());
    }
}
