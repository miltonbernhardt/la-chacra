package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class QuesoAlreadyExistsException extends AlreadyExistsException {
    public QuesoAlreadyExistsException() {
        super(ErrorMessages.MSG_QUESO_ALREADY_EXIST);
    }
}
