package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.LoteUpdateDTO;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.QuesoService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SuccessfulResponse<QuesoDTO>> get(@Length(max = 3, message = ValidationMessages.MUST_NOT_EXCEED_3_CHARACTERS)
                                                            @PathVariable("id") String id) throws QuesoNotFoundException {
        log.info("API::get - id: {}", id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.get(id)));
    }

    @GetMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<List<QuesoDTO>>> getAll() {
        log.info("API::getAll");
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse<String>> delete(@Length(max = 3, message = ValidationMessages.MUST_NOT_EXCEED_3_CHARACTERS)
                                                             @PathVariable("id") String id) throws QuesoNotFoundException {
        log.info("API::delete - id: {}", id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_QUESO_DELETED, service.delete(id)));
    }
}
