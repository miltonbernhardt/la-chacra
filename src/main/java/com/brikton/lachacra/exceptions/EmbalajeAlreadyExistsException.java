package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class EmbalajeAlreadyExistsException extends AlreadyExistsException{
    public EmbalajeAlreadyExistsException() {super(ErrorMessages.MSG_EMBALAJE_ALREADY_EXISTS);}
}
