package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.ExpedicionDTO;
import com.brikton.lachacra.dtos.ExpedicionUpdateDTO;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.ExpedicionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(Path.API_EXPEDICIONES)
@Slf4j
@Validated
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

    @GetMapping(value = "/lote")
    public ResponseEntity<SuccessfulResponse<List<ExpedicionDTO>>> getAllByLote(@Pattern(regexp = "^[0-9]{12,14}$", message = ValidationMessages.INVALID_FORMAT)
                                                                                    @PathParam("idLote") String idLote) {
        log.info("API::getAllByLote - idLote = {}",idLote);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAllByLote(idLote)));
    }

    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<ExpedicionDTO>> save(@RequestBody @Valid ExpedicionDTO dto) {
        log.info("API::save - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_EXPEDICION_CREATED, service.save(dto)));
    }

    @PutMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<ExpedicionDTO>> update(@RequestBody @Valid ExpedicionUpdateDTO dto) {
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_EXPEDICION_UPDATED, service.update(dto)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse<String>> delete(@Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
                                                             @PathVariable("id") Long id) {
        log.info("API::delete - id: {}", id);
        service.delete(id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_EXPEDICION_DELETED));
    }
}
