package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.exceptions.*;
import com.brikton.lachacra.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {LoteNotFoundException.class, QuesoNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handlerLoteNotFoundException(HttpServletRequest req, EntityNotFoundException ex) {
        return response(ex, req, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(NotFoundConflictException.class)
    protected ResponseEntity<ErrorResponse> handlerNotFoundConflictException(HttpServletRequest req, NotFoundConflictException ex) {
        return response(ex, req, HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    ResponseEntity<ErrorResponse> handleValidationError(HttpServletRequest req, ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream().collect(Collectors.toMap(n -> ((PathImpl) n.getPropertyPath()).getLeafNode().toString(), ConstraintViolation::getMessage));
        return response(ex, req, HttpStatus.BAD_REQUEST, ErrorMessages.MSG_INVALID_PARAMS, errors);
    }

    @ExceptionHandler(value = {DatabaseException.class, Exception.class})
    protected ResponseEntity<ErrorResponse> handlerInternalError(HttpServletRequest req, Exception ex) {
        log.error("Request: {} - {}", req.getRequestURL(), ex);
        return new ResponseEntity<>(ErrorResponse.set(ErrorMessages.MSG_INTERNAL_SERVER_ERROR, req.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(
                ex,
                ErrorResponse.set(ErrorMessages.MSG_INVALID_BODY, errors, ((ServletWebRequest) request).getRequest().getRequestURI(), status.value()),
                headers, status, request
        );
    }

    private ResponseEntity<ErrorResponse> response(Exception ex, HttpServletRequest req, HttpStatus status, String message) {
        log.error("Request: {} - {}", req.getRequestURL(), ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.set(message, req.getRequestURI(), status.value()), status);
    }

    private ResponseEntity<ErrorResponse> response(Exception ex, HttpServletRequest req, HttpStatus status, String message, Map<String, String> errors) {
        log.error("Request: {} - {}", req.getRequestURL(), ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.set(message, errors, req.getRequestURI(), status.value()), status);
    }
}
