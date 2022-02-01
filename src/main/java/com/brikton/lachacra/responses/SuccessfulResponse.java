package com.brikton.lachacra.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessfulResponse {
    private final Object data;
    private final String message;

    public SuccessfulResponse(Object data) {
        this.data = data;
        this.message = null;
    }

    public SuccessfulResponse(String message) {
        this.message = message;
        this.data = null;
    }

    public SuccessfulResponse(String message, Object data) {
        this.data = data;
        this.message = message;
    }
}
