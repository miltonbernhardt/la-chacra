package com.brikton.lachacra.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class InvalidLoteException extends Exception {

    private Map<String, String> invalidFields = new HashMap<>();

    public InvalidLoteException(Map<String, String> invalidFields) {
        super("Lote inválido");
        this.invalidFields = invalidFields;
    }
}
