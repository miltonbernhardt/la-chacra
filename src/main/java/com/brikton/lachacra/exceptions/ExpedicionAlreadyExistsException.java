package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class ExpedicionAlreadyExistsException extends AlreadyExistsException{
    public ExpedicionAlreadyExistsException() { super(ErrorMessages.MSG_EXPEDICION_ALREADY_EXISTS); }
}
