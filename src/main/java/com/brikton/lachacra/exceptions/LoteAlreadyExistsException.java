package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class LoteAlreadyExistsException extends AlreadyExistsException {
    public LoteAlreadyExistsException() {
        super(ErrorMessages.MSG_LOTE_ALREADY_EXIST);
    }
}
