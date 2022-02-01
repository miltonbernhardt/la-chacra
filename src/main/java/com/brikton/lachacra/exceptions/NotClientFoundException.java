package com.brikton.lachacra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No existe el cliente")
public class NotClientFoundException extends RuntimeException{
    //A familiar HTTP 404 response will be returned if the URL handled by this method includes an unknown order id.
}
