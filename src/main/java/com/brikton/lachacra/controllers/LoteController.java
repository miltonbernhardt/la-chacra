package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.exceptions.DatabaseException;
import com.brikton.lachacra.exceptions.InvalidLote;
import com.brikton.lachacra.responses.ErrorResponse;
import com.brikton.lachacra.services.LoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/lotes")
@Slf4j
public class LoteController {

    private final String msgSaveOK = "El lote de producción se creo exitosamente.";
    private final String msgInvalidLote = "Los datos enviados no son correctos.";

    private final LoteService service;

    public LoteController(LoteService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}") //todo change return to dto
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        //todo validar los objetos que entren como parte del body - procesar error del id not long
        try {
            log.info("API::get - id: {}", id);
            return ResponseEntity.ok().body(service.get(id));
        } catch (DatabaseException e) {
            return processDatabaseError("get", e);
        }
    }


    @PostMapping(value = "/")
    public ResponseEntity<?> save(LoteDTO dto) {
        try {
            log.info("API::save - dto: {}", dto);
            return ResponseEntity.ok().body(service.save(dto));
        } catch (DatabaseException e) {
            return processDatabaseError("save", e);
        } catch (InvalidLote e) {
            return processInvalidLote("save", e);
        }
    }

    private ResponseEntity<?> processDatabaseError(String method, DatabaseException e) {
        log.error("API::{} ", method, e);
        return ResponseEntity.internalServerError().build();
    }

    private ResponseEntity<?> processInvalidLote(String method, InvalidLote e) {
        log.error("API::{} ", method, e);
        return ResponseEntity.unprocessableEntity().body(ErrorResponse.set(msgInvalidLote, e.getInvalidFields()));
    }

    //todo hacer unos catchs generales - ver de throwear directamente en los reponse y que un catch general se encargue de procesar

    //ToDo:usar exception handler https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc

//    // Convert a predefined exception to an HTTP Status code
//    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")  // 409
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public void conflict() {
//        // Nothing to do
//    }
//
//    // Specify name of a specific view that will be used to display the error:
//    @ExceptionHandler({SQLException.class, DataAccessException.class})
//    public String databaseError() {
//        // Nothing to do.  Returns the logical view name of an error page, passed
//        // to the view-resolver(s) in usual way.
//        // Note that the exception is NOT available to this view (it is not added
//        // to the model) but see "Extending ExceptionHandlerExceptionResolver"
//        // below.
//        return "databaseError";
//    }

//    // Total control - setup a model and return the view name yourself. Or
//    // consider subclassing ExceptionHandlerExceptionResolver (see below).
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleError(HttpServletRequest  req, Exception ex) {
//        log.error("Request: " + req.getRequestURL() + " raised " + ex);
//
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", ex);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName("error");
//        return mav;
//    }
}