package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.exceptions.DatabaseException;
import com.brikton.lachacra.exceptions.InvalidLoteException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.LoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/lotes")
@Slf4j
@Validated
public class LoteController {

    private final LoteService service;

    public LoteController(LoteService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse> get(@PathVariable("id") Long id) throws LoteNotFoundException {
        log.info("API::get - id: {}", id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.get(id)));
    }

    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse> save(@RequestBody LoteDTO dto) throws DatabaseException, InvalidLoteException, NotFoundConflictException {
        log.info("API::save - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.save(dto)));
    }


}