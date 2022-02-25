package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.ExpedicionDTO;
import com.brikton.lachacra.exceptions.ClienteNotFoundException;
import com.brikton.lachacra.exceptions.ExpedicionAlreadyExistsException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundConflictException;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.ClienteService;
import com.brikton.lachacra.services.ExpedicionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expediciones")
@Slf4j
@Validated
@CrossOrigin(origins = "**")
public class ExpedicionController {

    private final ExpedicionService service;

    public ExpedicionController(ExpedicionService service) {
        this.service = service;
    }

    @GetMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<List<ExpedicionDTO>>> getAll() {
        log.info("API::getAll");
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }

    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<ExpedicionDTO>> save(@RequestBody @Valid ExpedicionDTO dto) throws ExpedicionAlreadyExistsException, ClienteNotFoundException, LoteNotFoundException {
        log.info("API::save - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_CLIENTE_CREATED, service.save(dto)));
    }
}
