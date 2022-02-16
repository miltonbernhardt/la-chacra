package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class LoteNotFoundException extends NotFoundException {
    public LoteNotFoundException() {
        super(ErrorMessages.MSG_LOTE_NOT_FOUND);
    }
}
