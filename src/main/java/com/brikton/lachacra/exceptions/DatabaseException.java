package com.brikton.lachacra.exceptions;

import com.brikton.lachacra.constants.ErrorMessages;

public class DatabaseException extends Exception {

    public DatabaseException(Throwable cause) {
        super(ErrorMessages.MSG_DATABASE_ERROR, cause);
    }
}
