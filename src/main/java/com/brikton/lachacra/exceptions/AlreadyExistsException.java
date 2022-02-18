package com.brikton.lachacra.exceptions;

public abstract class AlreadyExistsException extends Exception {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
