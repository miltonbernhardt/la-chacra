package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.QuesoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/quesos")
@Slf4j
@Validated
public class QuesoController {


    private final QuesoService service;

    public QuesoController(QuesoService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse> get(@PathVariable("id") @Min(value = 1, message = "El id del queso debe ser mayor a 0") Long id) throws QuesoNotFoundException {
        log.info("API::get - id: {}", id); //TODO logueamos esto? capaz se vuelve muy verboso
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.get(id)));
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getAll(){
        log.info("API::getAll"); //TODO logueamos esto? capaz se vuelve muy verboso
        return ResponseEntity.ok().body(service.getAll());
    }
/*
    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse> save(@RequestBody @Valid QuesoDTO dto) throws NotFoundConflictException {
        log.info("API::save - dto: {}", dto); //TODO logueamos esto? capaz se vuelve muy verboso
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.save(dto)));
    }
*/
}