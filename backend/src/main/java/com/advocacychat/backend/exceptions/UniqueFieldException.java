package com.advocacychat.backend.exceptions;

public class UniqueFieldException extends RuntimeException {
    public UniqueFieldException(String message) {
        super(message);
    }
}
