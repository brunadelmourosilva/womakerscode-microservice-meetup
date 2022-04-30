package com.brunadelmouro.microservicemeetup.models.enums;

public enum Profile {

    ADMIN(1, "ROLE_ADMIN"),
    REGISTRATION(2, "ROLE_REGISTRATION");

    private int cod;
    private String description;

    Profile(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    //return object - enum(convert Integer to enum)
    public static Profile toEnum(Integer cod){

        if(cod == null) return null;

        for (Profile ct : Profile.values()) {
            if(cod.equals(ct.getCod()))
                return ct;
        }
        throw new IllegalArgumentException("Invalid id " + cod);
    }
}

