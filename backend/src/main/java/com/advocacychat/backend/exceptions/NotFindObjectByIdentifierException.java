package com.advocacychat.backend.exceptions;

public class NotFindObjectByIdentifierException extends RuntimeException {
    public NotFindObjectByIdentifierException(String message){
        super(message);
    }
}
