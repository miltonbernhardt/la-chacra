package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.LoteUpdateDTO;
import com.brikton.lachacra.dtos.rendimiento.RendimientoDiaDTO;
import com.brikton.lachacra.dtos.rendimiento.RendimientoQuesoDTO;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.LoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
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

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<List<LoteDTO>>> getAll() {
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getAll()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse<LoteDTO>> getById(@Pattern(regexp = "^[0-9]{12,14}$", message = ValidationMessages.INVALID_FORMAT)
                                                               @PathVariable("id") String id) {
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getDTOById(id)));
    }

    @GetMapping(value = "/queso", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<List<LoteDTO>>> getByQuesoAndWithStock(@PathParam("codigoQueso") String codigoQueso) {
        log.info("API::getByQuesoAndWithStock - codigoQueso: {}", codigoQueso);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getByQuesoAndWithStock(codigoQueso)));
    }

    //TODO: tests params
    @GetMapping(value = "/produccion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<List<LoteDTO>>> getBetweenDates(@RequestParam("fecha_desde")
                                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
                                                                             @RequestParam("fecha_hasta")
                                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {
        log.info("API::getBetweenDates - fecha_desde: {} - fecha_hasta: {} ", fechaDesde, fechaHasta);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getBetweenDates(fechaDesde, fechaHasta)));
    }

    //TODO: test
    @GetMapping(value = "/rendimiento/dia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<List<RendimientoDiaDTO>>> getRendimientoByDia(@RequestParam("fecha_desde")
                                                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
                                                                                           @RequestParam("fecha_hasta")
                                                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {
        log.info("API::getRendimientoByDia - fecha_desde: {} - fecha_hasta: {} ", fechaDesde, fechaHasta);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getRendimientoByDia(fechaDesde, fechaHasta)));
    }

    //TODO: test
    @GetMapping(value = "/rendimiento/queso", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<List<RendimientoQuesoDTO>>> getRendimientoByQueso(@RequestParam("fecha_desde")
                                                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
                                                                                               @RequestParam("fecha_hasta")
                                                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {
        log.info("API::getRendimientoByQueso - fecha_desde: {} - fecha_hasta: {} ", fechaDesde, fechaHasta);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getRendimientoByQueso(fechaDesde, fechaHasta)));
    }

    //TODO: test
    @GetMapping(value = "/litros_elaborados")
    public ResponseEntity<SuccessfulResponse<List<LitrosElaboradosDiaDTO>>> getLitrosElaborados(@RequestParam("fecha_desde")
                                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
                                                                                                @RequestParam("fecha_hasta")
                                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {
        log.info("API::getLitrosElaborados - fecha_desde: {} - fecha_hasta: {} ", fechaDesde,fechaHasta);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getLitrosElaborados(fechaDesde,fechaHasta)));
    }

    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<LoteDTO>> save(@RequestBody @Valid LoteDTO dto) {
        log.info("API::save - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_LOTE_CREATED, service.save(dto)));
    }

    @PutMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<LoteDTO>> update(@RequestBody @Valid LoteUpdateDTO dto) {
        log.info("API::update - dto: {}", dto);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_LOTE_UPDATED, service.update(dto)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SuccessfulResponse<String>> delete(@Pattern(regexp = "^[0-9]{12,14}$", message = ValidationMessages.INVALID_FORMAT)
                                                             @PathVariable("id") String id) {
        log.info("API::delete - id: {}", id);
        service.delete(id);
        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_LOTE_DELETED));
    }
}