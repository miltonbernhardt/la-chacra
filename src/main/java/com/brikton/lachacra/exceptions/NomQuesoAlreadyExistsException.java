package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class NomQuesoAlreadyExistsException extends AlreadyExistsException {
    public NomQuesoAlreadyExistsException() {
        super(ErrorMessages.MSG_NOMENCLATURE_QUESO_ALREADY_EXIST);
    }
}
