package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class LoteNotFoundException extends Exception {

    public LoteNotFoundException(Throwable cause) {
        super(ErrorMessages.MSG_LOTE_NOT_FOUND, cause);
    }

    public LoteNotFoundException() {
        super(ErrorMessages.MSG_LOTE_NOT_FOUND);
    }
}
