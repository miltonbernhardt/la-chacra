package com.brikton.lachacra.exceptions;


import com.brikton.lachacra.constants.ErrorMessages;

public class TipoClienteNotFoundException extends NotFoundException{
    public TipoClienteNotFoundException(){super(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND);}
}
