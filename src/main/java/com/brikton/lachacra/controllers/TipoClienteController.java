package com.brikton.lachacra.controllers;

import com.brikton.lachacra.annotations.HasCargarPreciosAuthority;
import com.brikton.lachacra.annotations.HasClienteAuthority;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.dtos.TipoClienteDTO;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.TipoClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Path.API_TIPOS_CLIENTE)
@Slf4j
@HasCargarPreciosAuthority
@HasClienteAuthority
public class TipoClienteController {

    private final TipoClienteService service;

    public TipoClienteController(TipoClienteService service) {
        this.service = service;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<List<TipoClienteDTO>>> getAll() {
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }
}
