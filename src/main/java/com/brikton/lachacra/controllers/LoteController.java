package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.exceptions.DatabaseException;
import com.brikton.lachacra.exceptions.InvalidLoteException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.LoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

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
    public ResponseEntity<LoteDTO> save(@Valid @RequestBody LoteDTO dto) throws DatabaseException, InvalidLoteException {
        log.info("API::save - dto: {}", dto);
        //todo no estaria validando las anotaciones
        return ResponseEntity.ok().body(service.save(dto));
    }
}