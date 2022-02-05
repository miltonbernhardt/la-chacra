package com.brikton.lachacra.exceptions;

public class DatabaseException extends Exception {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause, String message) {
        super(message, cause);
    }
}
