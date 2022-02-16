package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

import javax.persistence.EntityNotFoundException;

public class QuesoNotFoundException extends EntityNotFoundException {
    public QuesoNotFoundException() {
        super(ErrorMessages.MSG_QUESO_NOT_FOUND);
    }
}
