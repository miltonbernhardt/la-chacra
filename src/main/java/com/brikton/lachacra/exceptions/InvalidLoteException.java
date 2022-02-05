package com.brikton.lachacra.exceptions;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class InvalidLoteException extends Exception {
    private final HashMap<String, String> invalidFields;

    public InvalidLoteException(Throwable cause, String message, HashMap<String, String> invalidFields) {
        super(message, cause);
        this.invalidFields = invalidFields;
    }
}
