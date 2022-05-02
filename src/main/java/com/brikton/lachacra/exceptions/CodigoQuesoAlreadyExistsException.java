package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class CodigoQuesoAlreadyExistsException extends AlreadyExistsException {
    public CodigoQuesoAlreadyExistsException() {
        super(ErrorMessages.MSG_CODIGO_QUESO_ALREADY_EXIST);
    }
}
