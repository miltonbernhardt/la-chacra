package com.brikton.lachacra.controllers;

import com.brikton.lachacra.exceptions.DatabaseException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final String msgInternalServerError = "Ocurrió un error interno";
    private final String msgInvalidBody = "Cuerpo del mensaje inválido";
    private final String msgInvalidParam = "Parámetro inválido";
    private final String msgLoteNotFound = "Lote no encontrado";

    @ExceptionHandler(value = {LoteNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handlerLoteNotFoundException(HttpServletRequest req, LoteNotFoundException ex) {
        return response(ex, req, HttpStatus.NOT_FOUND, msgLoteNotFound);
    }

    @ExceptionHandler(NotFoundConflictException.class)
    protected ResponseEntity<ErrorResponse> handlerNotFoundConflictException(HttpServletRequest req, NotFoundConflictException ex) {
        return response(ex, req, HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    ResponseEntity<ErrorResponse> handleValidationError(HttpServletRequest req, ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream().collect(Collectors.toMap(n -> n.getPropertyPath().toString(), ConstraintViolation::getMessage));
        return response(ex, req, HttpStatus.BAD_REQUEST, msgInvalidParam, errors);
    }

    @ExceptionHandler(value = {DatabaseException.class, Exception.class})
    protected ResponseEntity<ErrorResponse> handlerInternalError(HttpServletRequest req, Exception ex) {
        log.error("Request: {} - {}", req.getRequestURL(), ex);
        return new ResponseEntity<>(ErrorResponse.set(msgInternalServerError, req.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        status = HttpStatus.UNPROCESSABLE_ENTITY;
        return handleExceptionInternal(
                ex,
                ErrorResponse.set(msgInvalidBody, errors, ((ServletWebRequest) request).getRequest().getRequestURI(), status.value()),
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
