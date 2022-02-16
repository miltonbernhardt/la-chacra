package com.brikton.lachacra.exceptions;

public class NotFoundConflictException extends Exception {

    public NotFoundConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundConflictException(String message) {
        super(message);
    }

}
