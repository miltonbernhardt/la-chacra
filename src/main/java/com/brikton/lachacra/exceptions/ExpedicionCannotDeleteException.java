package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class ExpedicionCannotDeleteException extends CannotDeleteException {
    public ExpedicionCannotDeleteException() {
        super(ErrorMessages.MSG_EXPEDICION_CANNOT_BE_DELETED);
    }
}
