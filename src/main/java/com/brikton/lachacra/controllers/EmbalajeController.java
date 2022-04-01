package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.EmbalajeDTO;
import com.brikton.lachacra.dtos.EmbalajeUpdateDTO;
import com.brikton.lachacra.dtos.EmbalajeDTO;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.EmbalajeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/embalajes")
@Slf4j
@Validated
@CrossOrigin(origins = "**")
public class EmbalajeController {

    private final EmbalajeService service;

    public EmbalajeController(EmbalajeService embalajeService) {
        this.service = embalajeService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<List<EmbalajeDTO>>> getAll() {
        log.info("API::getAll");
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse<EmbalajeDTO>> getById(@Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1) @PathVariable("id") Long id) {
        log.info("API::getById - id: {}",id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.get(id)));
    }

    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<EmbalajeDTO>> save(@RequestBody @Valid EmbalajeDTO dto) {
        log.info("API::save - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_EMBALAJE_CREATED, service.saveNew(dto)));
    }

    @PutMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<EmbalajeDTO>> update(@RequestBody @Valid EmbalajeUpdateDTO dto) {
        log.info("API::update - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_EMBALAJE_UPDATED, service.update(dto)));
    }

    @PutMapping(value = "/agregar_stock")
    public ResponseEntity<SuccessfulResponse<EmbalajeDTO>> updateStock(@RequestParam("id") Long id,@RequestParam("stock") Integer stock ) {
        log.info("API::updateStock - id: {} - stock: {}", id, stock);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_EMBALAJE_UPDATED, service.updateStock(id,stock)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse<Long>> delete(@Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1) @PathVariable("id") Long id) {
        log.info("API::delete - id: {}", id);
        service.delete(id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_EMBALAJE_DELETED));
    }
}
