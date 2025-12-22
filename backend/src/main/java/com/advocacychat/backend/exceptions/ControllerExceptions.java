package com.advocacychat.backend.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptions {

    @ExceptionHandler(NotFindObjectByIdentifierException.class)
    public ResponseEntity<Map<String, String>> handleNotFindObjectByIdentifier(NotFindObjectByIdentifierException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordDontMatchException.class)
    public ResponseEntity<Map<String, String>> passwordDontMatch(PasswordDontMatchException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullFieldException.class)
    public ResponseEntity<Map<String, String>> nullField(NullFieldException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessRuleException(BusinessRuleException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", ex.getStatus().value());
        response.put("error", ex.getMessage());

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(UnauthorizedRoleException.class)
    public ResponseEntity<Map<String, String>> unauthorizedRole(UnauthorizedRoleException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Error");
        response.put("fields", fieldErrors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(
            ConstraintViolationException ex
    ) {
        Map<String, String> violations = new HashMap<>();

        ex.getConstraintViolations().forEach(v ->
                violations.put(v.getPropertyPath().toString(), v.getMessage())
        );

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Error");
        response.put("violations", violations);

        return ResponseEntity.badRequest().body(response);
    }
}
