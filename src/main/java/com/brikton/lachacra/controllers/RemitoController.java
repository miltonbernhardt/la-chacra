package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.RemitoDTO;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.RemitoService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping(Path.API_REMITOS)
@Slf4j
//TODO: add authority
public class RemitoController {

    private final RemitoService service;

    public RemitoController(RemitoService remitoService) {
        this.service = remitoService;
    }

    @GetMapping(value = "/generate")
    public ResponseEntity<SuccessfulResponse<RemitoDTO>> generateRemito(
            @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
            @RequestParam("id_cliente") Long idCliente,
            @RequestParam("fecha")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        log.info("API::generateRemito - id_cliente: {} fecha: {}", idCliente, fecha);
        return ResponseEntity.ok().body(SuccessfulResponse.set(service.generateRemitoDTO(idCliente, fecha)));
    }

    @GetMapping(value = "/pdf/{id}")
    public ResponseEntity<byte[]> getPdf(@Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
                                         @PathVariable("id") Long id) throws JRException, IOException {
        log.info("API::getPdf - id: {}", id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "remito-" + id + ".pdf");
        return new ResponseEntity<>(service.getPdf(id), headers, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<SuccessfulResponse<RemitoDTO>> generateAndSave(
            @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
            @RequestParam("id_cliente") Long idCliente,
            @RequestParam("fecha")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        log.info("API::generateAndSave- id_cliente: {} fecha: {}", idCliente, fecha);

        return ResponseEntity.ok().body(SuccessfulResponse.set(SuccessfulMessages.MSG_REMITO_CREATED, service.generateAndSave(idCliente, fecha)));
    }
}
