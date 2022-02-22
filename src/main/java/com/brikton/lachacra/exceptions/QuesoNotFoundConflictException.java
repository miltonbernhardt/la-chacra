package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class QuesoNotFoundConflictException extends NotFoundConflictException {

    public QuesoNotFoundConflictException(Throwable cause) {
        super(ErrorMessages.MSG_QUESO_NOT_FOUND, cause);
    }
    public QuesoNotFoundConflictException() {
        super(ErrorMessages.MSG_QUESO_NOT_FOUND);
    }
}
