package com.brunadelmouro.microservicemeetup.controllers;

import com.brunadelmouro.microservicemeetup.exceptions.ObjectNotFoundException;
import com.brunadelmouro.microservicemeetup.exceptions.StandardError;
import com.brunadelmouro.microservicemeetup.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e){
        return new ResponseEntity<>(
                new StandardError(e.getMessage(),
                        DateUtils.convertSystemTimeMillisToString(System.currentTimeMillis()),
                        HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException e){
        return ResponseEntity.ok(
                new StandardError(e.getMessage(),
                        DateUtils.convertSystemTimeMillisToString(System.currentTimeMillis()),
                        HttpStatus.NOT_FOUND.value()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<StandardError>> beanValidation(MethodArgumentNotValidException e){

        List<StandardError> errors = e.getAllErrors().stream().map(
                x -> new StandardError(x.getDefaultMessage(),
                        DateUtils.convertSystemTimeMillisToString(System.currentTimeMillis()),
                        HttpStatus.BAD_REQUEST.value())
                        ).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }


}
