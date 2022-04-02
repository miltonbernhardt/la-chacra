package com.brikton.lachacra.exceptions;

public abstract class NotFoundConflictException extends RuntimeException {

    public NotFoundConflictException(String message) {
        super(message);
    }

}
