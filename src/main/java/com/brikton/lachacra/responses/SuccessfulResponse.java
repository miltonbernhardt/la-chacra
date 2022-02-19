package com.brikton.lachacra.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class SuccessfulResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    public SuccessfulResponse() {
        this.data = null;
        this.message = "";
    }

    private SuccessfulResponse(String message, T data) {
        this.data = data;
        this.message = message;
    }

    public static <T> SuccessfulResponse<T> set(T data) {
        return new SuccessfulResponse<>(null, data);
    }

    public static <T> SuccessfulResponse<T> set(String message, T data) {
        return new SuccessfulResponse<>(message, data);
    }
}
