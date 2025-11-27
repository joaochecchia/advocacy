package com.advocacychat.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptions {

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleNotFindObjectByIdentifier(BadCredentialsException ex){
        Map<String, String> response = new HashMap<>();
        response.put("Error: ", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
