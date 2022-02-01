package com.brikton.lachacra.responses;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class ErrorResponse {
    private final String message;
    private final HashMap<String, String> invalidFields;

    private ErrorResponse(String message, HashMap<String, String> invalidFields) {
        this.message = message;
        this.invalidFields = invalidFields;
    }

    public static ErrorResponse set(String message) {
        return new ErrorResponse(message, null);
    }

    public static ErrorResponse set(String message, HashMap<String, String> invalidFields) {
        return new ErrorResponse(message, invalidFields);
    }
}
