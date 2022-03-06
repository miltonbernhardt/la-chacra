package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.TipoClienteDTO;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.TipoClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos_cliente")
@Slf4j
@Validated
@CrossOrigin(origins = "**")
public class TipoClienteController {

    private final TipoClienteService service;

    public TipoClienteController(TipoClienteService service) {
        this.service = service;
    }

    @GetMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<List<TipoClienteDTO>>> getAll() {
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }
}
