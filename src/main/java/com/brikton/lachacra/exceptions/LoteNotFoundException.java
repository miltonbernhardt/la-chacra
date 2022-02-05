package com.brikton.lachacra.exceptions;

public class LoteNotFoundException extends Exception {

    public LoteNotFoundException(String message) {
        super(message);
    }

    public LoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoteNotFoundException(Throwable cause) {
        super(cause);
    }

    public LoteNotFoundException() {
        super("Lote no encontrado");
    }
}
