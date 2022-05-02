package com.brikton.lachacra.exceptions;


import com.brikton.lachacra.constants.ErrorMessages;

public class TipoClienteNotFoundConflictException extends NotFoundConflictException {
    public TipoClienteNotFoundConflictException() {
        super(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND);
    }
}
