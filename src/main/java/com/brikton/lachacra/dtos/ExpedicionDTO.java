package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.constants.ValidationMessages;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class ExpedicionDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @PastOrPresent(message = ValidationMessages.CANT_BE_LATER_THAN_TODAY)
    private LocalDate fechaExpedicion;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 0, message = ValidationMessages.CANNOT_BE_LESS_THAN_0)
    private Integer cantidad;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 0, message = ValidationMessages.CANNOT_BE_LESS_THAN_0)
    private Double peso;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 0, message = ValidationMessages.CANNOT_BE_LESS_THAN_0)
    private Double importe;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 0, message = ValidationMessages.CANNOT_BE_LESS_THAN_0)
    private Long idCliente;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 0, message = ValidationMessages.CANNOT_BE_LESS_THAN_0)
    private Long idLote;

    private Long id;


    public ExpedicionDTO() {
    }

    public ExpedicionDTO(Expedicion expedicion) {
        this.setId(expedicion.getId());
        this.setFechaExpedicion(expedicion.getFechaExpedicion());
        this.setCantidad(expedicion.getCantidad());
        this.setPeso(expedicion.getPeso());
        this.setImporte(expedicion.getImporte());
        this.setIdCliente(expedicion.getCliente().getId());
        this.setIdLote(expedicion.getLote().getId());
    }
}
