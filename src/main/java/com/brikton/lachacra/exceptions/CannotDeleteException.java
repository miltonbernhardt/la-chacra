package com.brikton.lachacra.exceptions;

public abstract class CannotDeleteException extends RuntimeException {
    public CannotDeleteException(String message) {
        super(message);
    }
}
