package com.advocacychat.backend.exceptions;

public class PasswordDontMatchException extends RuntimeException {
    public PasswordDontMatchException(String message) {
      super(message);
    }
}
