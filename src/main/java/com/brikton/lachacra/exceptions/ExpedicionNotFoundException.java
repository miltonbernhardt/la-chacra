package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class ExpedicionNotFoundException extends NotFoundException {
    public ExpedicionNotFoundException() {
        super(ErrorMessages.MSG_EXPEDICION_NOT_FOUND);
    }
}
