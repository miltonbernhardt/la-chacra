package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class CookieNotFoundException extends TokenException {
    public CookieNotFoundException() {
        super(ErrorMessages.MSG_COOKIE_NOT_FOUND);
    }
}
