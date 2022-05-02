package com.brikton.lachacra.controllers;

import com.brikton.lachacra.annotations.HasCargarExpedicionAuthority;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.ExpedicionDTO;
import com.brikton.lachacra.dtos.ExpedicionUpdateDTO;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.ExpedicionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(Path.API_EXPEDICIONES)
@Slf4j
@Validated
@HasCargarExpedicionAuthority
public class ExpedicionController {

    private final ExpedicionService service;

    public ExpedicionController(ExpedicionService service) {
        this.service = service;
    }

    @HasCargarExpedicionAuthority
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<List<ExpedicionDTO>>> getAll() {
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }

    @GetMapping(value = "/lote", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<List<ExpedicionDTO>>> getAllByLote(
            @NotNull(message = ValidationMessages.NOT_FOUND) @PathParam("idLote") @Pattern(regexp = "^[0-9]{12,14}$", message = ValidationMessages.INVALID_FORMAT) String idLote
    ) {
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAllByLote(idLote)));
    }

    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<List<ExpedicionDTO>>> getBetweenDates(
            @RequestParam("fecha_desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam("fecha_hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta
    ) {
        log.info("API::getBetweenDates - fecha_desde: {} - fecha_hasta: {} ", fechaDesde, fechaHasta);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getBetweenDates(fechaDesde, fechaHasta)));
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<ExpedicionDTO>> save(@RequestBody @Valid ExpedicionDTO dto) {
        log.info("API::save - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_EXPEDICION_CREATED, service.save(dto)));
    }

    @PostMapping(value = "/lote", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<ExpedicionDTO>> saveExpedicionLoteCompleto(@RequestBody @Valid ExpedicionDTO dto) {
        log.info("API::saveExpedicionLoteCompleto - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_EXPEDICION_CREATED, service.saveExpedicionLoteCompleto(dto)));
    }

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<ExpedicionDTO>> update(@RequestBody @Valid ExpedicionUpdateDTO dto) {
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_EXPEDICION_UPDATED, service.update(dto)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse<String>> delete(
            @PathVariable("id") @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1) Long id
    ) {
        log.info("API::delete - id: {}", id);
        service.delete(id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_EXPEDICION_DELETED));
    }
}
