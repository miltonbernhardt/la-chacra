package com.brikton.lachacra.controllers;

import com.brikton.lachacra.annotations.HasClienteAuthority;
import com.brikton.lachacra.annotations.HasEmitirRemitoAuthority;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.ClienteUpdateDTO;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(Path.API_CLIENTES)
@Slf4j
@Validated
@HasClienteAuthority
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @HasEmitirRemitoAuthority
    public ResponseEntity<SuccessfulResponse<List<ClienteDTO>>> getAll() {
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }

    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<ClienteDTO>> save(@RequestBody @Valid ClienteDTO dto) {
        log.info("API::save - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_CLIENTE_CREATED, service.save(dto)));
    }

    @PutMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<ClienteDTO>> update(@RequestBody @Valid ClienteUpdateDTO dto) {
        log.info("API::update - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_CLIENTE_UPDATED, service.update(dto)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse<Long>> delete(@Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1) @PathVariable("id") Long id) {
        log.info("API::delete - id: {}", id);
        service.delete(id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_CLIENTE_DELETED));
    }
}
