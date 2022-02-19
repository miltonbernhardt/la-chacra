package com.brikton.lachacra.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorResponse {

    private final String message;
    private final String path;
    private final Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Map<String, String> errors;

    public ErrorResponse() {
        this.message = null;
        this.errors = null;
        this.path = null;
        this.status = null;
    }

    private ErrorResponse(String message, Map<String, String> errors, String path, Integer status) {
        this.message = message;
        this.errors = errors;
        this.path = path;
        this.status = status;
    }

    public static ErrorResponse set(String message, String path, Integer status) {
        return new ErrorResponse(message, null, path, status);
    }

    public static ErrorResponse set(String message, Map<String, String> invalidFields, String path, Integer status) {
        return new ErrorResponse(message, invalidFields, path, status);
    }
}
