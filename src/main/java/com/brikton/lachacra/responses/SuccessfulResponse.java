package com.brikton.lachacra.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class SuccessfulResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    private SuccessfulResponse(String message, Object data) {
        this.data = data;
        this.message = message;
    }

    public static SuccessfulResponse set(String message) {
        return new SuccessfulResponse(message, null);
    }

    public static SuccessfulResponse set(Object data) {
        return new SuccessfulResponse(null, data);
    }

    public static SuccessfulResponse set(String message, Object data) {
        return new SuccessfulResponse(message, data);
    }
}
