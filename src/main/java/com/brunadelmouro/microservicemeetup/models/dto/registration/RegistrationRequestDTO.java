package com.brunadelmouro.microservicemeetup.models.dto.registration;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RegistrationRequestDTO {

    @NotNull(message = "The field NAME cannot be null")
    @Length(min = 3, max = 100, message = "The field NAME needs to be between 3 and 100")
    private String name;

    @NotNull(message = "The field EMAIL cannot be null")
    @Pattern(regexp = "^(.+)@(.+)$", message = "The field EMAIL needs to be on the following pattern? xxx@xxx")
    private String email;

    @NotNull(message = "The field PASSWORD cannot be null")
    @Length(min = 4, max = 10, message =  "The field PASSWORD needs to be between 4 and 10")
    private String password;

    @NotNull(message = "The field NUMBER cannot be null")
    private String number;

    public RegistrationRequestDTO(String name, String email, String password, String number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.number = number;
    }

    public RegistrationRequestDTO() {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
