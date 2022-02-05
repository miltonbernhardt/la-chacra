package com.brikton.lachacra.controllers;

import com.brikton.lachacra.exceptions.DatabaseException;
import com.brikton.lachacra.exceptions.InvalidLoteException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final String msgInternalServerError = "Ocurrió un error interno";
    private final String msgInvalidLote = "Lote inválido";
    private final String msgLoteNotFound = "Lote no encontrado";

    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<ErrorResponse> handlerDatabaseException(HttpServletRequest req, DatabaseException ex) {
        log.error("Request: {} - {}", req.getRequestURL(), ex);
        return ResponseEntity.internalServerError().body(ErrorResponse.set(msgInternalServerError));
    }

    @ExceptionHandler(LoteNotFoundException.class)
    protected ResponseEntity<?> handlerLoteNotFoundException(HttpServletRequest req, LoteNotFoundException ex) {
        log.error("Request: {} - {}", req.getRequestURL(), ex);
        return new ResponseEntity<>(ErrorResponse.set(msgLoteNotFound), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundConflictException.class)
    protected ResponseEntity<?> handlerNotFoundConflictException(HttpServletRequest req, NotFoundConflictException ex) {
        log.error("Request: {} - {}", req.getRequestURL(), ex);
        return new ResponseEntity<>(ErrorResponse.set(ex.getMessage()), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(InvalidLoteException.class)
    protected ResponseEntity<?> handlerInvalidLoteException(HttpServletRequest req, InvalidLoteException ex) {
        log.error("Request: {} - {}", req.getRequestURL(), ex);
        return new ResponseEntity<>(ErrorResponse.set(msgInvalidLote, ex.getInvalidFields()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handlerException(HttpServletRequest req, Exception ex) {
        log.error("Request: {} - {}", req.getRequestURL(), ex);
        return ResponseEntity.internalServerError().body(ErrorResponse.set(msgInternalServerError));
    }
}
