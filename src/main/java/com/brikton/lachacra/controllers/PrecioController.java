package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.PrecioDTO;
import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.PrecioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
