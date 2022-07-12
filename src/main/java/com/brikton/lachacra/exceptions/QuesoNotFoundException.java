package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class QuesoNotFoundException extends NotFoundException {
    public QuesoNotFoundException() {
        super(ErrorMessages.MSG_QUESO_NOT_FOUND);
    }
}
