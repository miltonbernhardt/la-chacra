package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.QuesoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/quesos")
@Slf4j
@Validated
@CrossOrigin(origins = "**")
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

    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<QuesoDTO>> save(@RequestBody @Valid QuesoDTO dto){
        log.info("API::save - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.save(dto)));
    } //TODO nunca se considero que pasa si te llega un post con un elemento que ya tenga id, eso no deberia pasar, deberia ir por el put

    @PutMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<QuesoDTO>> update(@RequestBody @Valid QuesoDTO dto) throws QuesoNotFoundException {
        log.info("API::update - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.update(dto)));
    }
}
