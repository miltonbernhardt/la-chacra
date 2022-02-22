package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class ClienteAlreadyExistsException extends AlreadyExistsException{
    public ClienteAlreadyExistsException(){super(ErrorMessages.MSG_CLIENTE_ALREADY_EXIST);}
}
