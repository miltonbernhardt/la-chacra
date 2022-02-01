package com.brikton.lachacra.exceptions;

public class DatabaseException extends Exception {

    public DatabaseException(Throwable cause, String message) {
        super(message, cause);
    }
}
