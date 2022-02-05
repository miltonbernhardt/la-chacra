package com.brikton.lachacra.controllers;

import com.brikton.lachacra.exceptions.DatabaseException;
import com.brikton.lachacra.exceptions.InvalidLoteException;
import com.brikton.lachacra.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final String msgInternalServerError = "hubo un error interno";
    private final String msgInvalidLote = "Los datos enviados no son correctos.";

    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<ErrorResponse> handlerDatabaseException(HttpServletRequest req, DatabaseException ex) {
        log.error("Request: {} - {}", req.getRequestURL(), ex);
        return ResponseEntity.internalServerError().body(ErrorResponse.set(msgInternalServerError));
    }


    @ExceptionHandler(InvalidLoteException.class)
    protected ResponseEntity<ErrorResponse> handlerInvalidLoteException(HttpServletRequest req, InvalidLoteException ex) {
        log.error("Request: {} - {}", req.getRequestURL(), ex);
        return ResponseEntity.unprocessableEntity().body(ErrorResponse.set(msgInvalidLote, ex.getInvalidFields()));
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        log.error("Request: {} - {}", req.getRequestURL(), ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

}
