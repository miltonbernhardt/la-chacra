package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class RemitoNotFoundException extends NotFoundException{
    public RemitoNotFoundException(){super(ErrorMessages.MSG_REMITO_NOT_FOUND);}
}
