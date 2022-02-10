package com.brikton.lachacra.exceptions;

// todo se podria hacer que las exceptions extiendan de una NotFoundExc y en el Exception com.brikton.lachacra.controller usar esa en vez de cada una§§
public class QuesoNotFoundException extends Exception {
    public QuesoNotFoundException() {
        super("Queso no encontrado");
    }
}
