package com.brunadelmouro.microservicemeetup.exceptions;

public class ObjectNotFoundException extends RuntimeException{

    private String msg;

    public ObjectNotFoundException(String msg) {
        super(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
