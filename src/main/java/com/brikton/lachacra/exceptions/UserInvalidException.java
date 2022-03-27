package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class UserInvalidException extends AuthenticationException {
    public UserInvalidException() {
        super(ErrorMessages.MSG_INVALID_USER);
    }
}
