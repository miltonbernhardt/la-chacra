package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class PrecioAlreadyExistsException extends AlreadyExistsException {
    public PrecioAlreadyExistsException() {
        super(ErrorMessages.MSG_PRECIO_ALREADY_EXISTS);
    }
}
