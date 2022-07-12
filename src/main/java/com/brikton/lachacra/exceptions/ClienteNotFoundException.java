package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class ClienteNotFoundException extends NotFoundException {
    public ClienteNotFoundException() {
        super(ErrorMessages.MSG_CLIENTE_NOT_FOUND);
    }
}
