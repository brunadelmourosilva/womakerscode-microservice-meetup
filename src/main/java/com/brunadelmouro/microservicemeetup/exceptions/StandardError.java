package com.brunadelmouro.microservicemeetup.exceptions;

public class StandardError {

    private String msg;
    private String timestamp;
    private Integer status;

    public StandardError(String msg, String timestamp, Integer status) {
        this.msg = msg;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
