package com.brikton.lachacra.exceptions;

public class LoteNotFoundException extends Exception {

    public LoteNotFoundException(Throwable cause) {
        super("Lote no encontrado", cause);
    }
}
