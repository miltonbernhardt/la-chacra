package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.rendimiento.RendimientoDiaDTO;
import com.brikton.lachacra.dtos.ventas.VentaDiaDTO;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.VentaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
@Slf4j
@Validated
@CrossOrigin(origins = "**")
public class VentaController {

    private final VentaService service;

    public VentaController(VentaService service) {
        this.service = service;
    }

    @GetMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<List<VentaDiaDTO>>> getVentas(@RequestParam("fecha_desde")
                                                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
                                                                                     @RequestParam("fecha_hasta")
                                                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {
        log.info("API::getVentas - fecha_desde: {} - fecha_hasta: {} ", fechaDesde,fechaHasta);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.getVentas(fechaDesde,fechaHasta)));
    }
}
