package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.exceptions.DatabaseException;
import com.brikton.lachacra.exceptions.InvalidLoteException;
import com.brikton.lachacra.services.LoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/api/v1/lotes")
@Slf4j
@RestController
@Validated
public class LoteController {

    private final String msgSaveOK = "El lote de producción se creó exitosamente.";


    private final LoteService service;

    public LoteController(LoteService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LoteDTO> get(@PathVariable("id") Long id) throws DatabaseException, Exception {
        log.info("API::get - id: {}", id);
        return ResponseEntity.ok().body(service.get(id));
    }

    @PostMapping(value = "/")
    public ResponseEntity<LoteDTO> save(@Valid @RequestBody LoteDTO dto) throws DatabaseException, InvalidLoteException {
        log.info("API::save - dto: {}", dto);
        //todo no estaria validando las anotaciones
        return ResponseEntity.ok().body(service.save(dto));
    }
}