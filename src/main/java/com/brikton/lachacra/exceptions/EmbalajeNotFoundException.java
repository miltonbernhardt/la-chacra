package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class EmbalajeNotFoundException extends NotFoundException{
    public EmbalajeNotFoundException(){ super(ErrorMessages.MSG_EMBALAJE_NOT_FOUND);}
}
