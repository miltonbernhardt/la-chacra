package com.brikton.lachacra.exceptions;

public abstract class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}
