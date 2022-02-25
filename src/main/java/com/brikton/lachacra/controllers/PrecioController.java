package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.dtos.*;
import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.exceptions.*;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.PrecioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/precios")
@Slf4j
@Validated
@CrossOrigin(origins = "**")
public class PrecioController {

    private final PrecioService service;

    public PrecioController(PrecioService service) {
        this.service = service;
    }

    @GetMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<List<PrecioDTO>>> getAll() {
        log.info("API::getAll");
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }

    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<PrecioDTO>> save(@RequestBody @Valid PrecioDTO dto) throws TipoClienteNotFoundException, QuesoNotFoundException, PrecioAlreadyExistsException {
        log.info("API::save - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_PRECIO_CREATED, service.save(dto)));
    }

    @PutMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<PrecioDTO>> update(@RequestBody @Valid PrecioUpdateDTO dto) throws PrecioNotFoundException, TipoClienteNotFoundException, QuesoNotFoundException {
        log.info("API::update - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_QUESO_UPDATED, service.update(dto)));
    }
}
