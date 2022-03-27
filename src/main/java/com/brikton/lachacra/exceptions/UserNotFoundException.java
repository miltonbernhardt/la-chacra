package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException() {
        super(ErrorMessages.MSG_USER_NOT_REGISTERED);
    }
}
