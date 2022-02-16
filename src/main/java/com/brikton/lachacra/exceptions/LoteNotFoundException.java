package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

import javax.persistence.EntityNotFoundException;

public class LoteNotFoundException extends EntityNotFoundException {
    public LoteNotFoundException() {
        super(ErrorMessages.MSG_LOTE_NOT_FOUND);
    }
}
