package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class PrecioNotFoundException extends NotFoundException {
    public PrecioNotFoundException() {
        super(ErrorMessages.MSG_PRECIO_NOT_FOUND);
    }
}
