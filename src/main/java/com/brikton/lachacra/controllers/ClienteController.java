package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.TipoClienteDTO;
import com.brikton.lachacra.exceptions.ClienteAlreadyExistsException;
import com.brikton.lachacra.exceptions.LoteAlreadyExistsException;
import com.brikton.lachacra.exceptions.QuesoNotFoundConflictException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundException;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.ClienteService;
import com.brikton.lachacra.services.LoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@Slf4j
@Validated
@CrossOrigin(origins = "**")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<List<ClienteDTO>>> getAll() {
        log.info("API::getAll");
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }

    @GetMapping(value = "/tipo/")
    public ResponseEntity<SuccessfulResponse<List<TipoClienteDTO>>> getAllTipoCliente() {
        log.info("API::getAllTipoCliente");
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAllTipoCliente()));
    }

    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<ClienteDTO>> save(@RequestBody @Valid ClienteDTO dto) throws TipoClienteNotFoundException, ClienteAlreadyExistsException {
        log.info("API::save - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_CLIENTE_CREATED, service.save(dto)));
    }
}
