package com.brikton.lachacra.exceptions;

public class DatabaseException extends Exception {

    public DatabaseException(Throwable cause) {
        super("Error en la base de datos", cause);
    }
}
