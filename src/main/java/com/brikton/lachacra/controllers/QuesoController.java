package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.QuesoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

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
    public ResponseEntity<SuccessfulResponse<Queso>> get(@PathVariable("id") @Min(value = 1, message = "El id del queso debe ser mayor a 0") Long id) throws QuesoNotFoundException {
        log.info("API::get - id: {}", id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.get(id)));
    }

    @GetMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<List<QuesoDTO>>> getAll() {
        log.info("API::getAll");
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }
}
