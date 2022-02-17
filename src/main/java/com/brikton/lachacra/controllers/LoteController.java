package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.LoteUpdateDTO;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.LoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lotes")
@Slf4j
@Validated
@CrossOrigin(origins = "**")
public class LoteController {

    private final LoteService service;

    public LoteController(LoteService service) {
        this.service = service;
    }

    //todo getall

    @GetMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse<LoteDTO>> get(@Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
                                                           @PathVariable("id") String id) throws LoteNotFoundException {
        log.info("API::get - id: {}", id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.get(id)));
    }

    @GetMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<List<LoteDTO>>> getAll() {
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }

    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<LoteDTO>> save(@RequestBody @Valid LoteDTO dto) throws NotFoundConflictException {
        log.info("API::save - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_LOTE_CREATED, service.save(dto)));
    }

    @PutMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<LoteDTO>> update(@RequestBody @Validated LoteUpdateDTO dto) throws NotFoundConflictException, LoteNotFoundException {
        log.info("API::update - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_LOTE_UPDATED, service.update(dto)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse<String>> delete(@Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
                                                             @PathVariable("id") String id) throws LoteNotFoundException {
        log.info("API::delete - id: {}", id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_LOTE_DELETED, service.delete(id)));
    }
}