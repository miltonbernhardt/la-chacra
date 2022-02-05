package com.brikton.lachacra.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {

    private final String message;

    @JsonProperty("invalid_fields")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Map<String, String> invalidFields;

    private ErrorResponse(String message, Map<String, String> invalidFields) {
        this.message = message;
        this.invalidFields = invalidFields;
    }

    public static ErrorResponse set(String message) {
        return new ErrorResponse(message, null);
    }

    public static ErrorResponse set(String message, Map<String, String> invalidFields) {
        return new ErrorResponse(message, invalidFields);
    }
}
