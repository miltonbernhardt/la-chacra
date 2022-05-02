package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class PesoNoConfiableException extends NotFoundException  {
    public PesoNoConfiableException() {super(ErrorMessages.MSG_PESO_NO_CONFIABLE); }
}
