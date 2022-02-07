package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Expedicion;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class ExpedicionDTO {

    @NotNull(message = "No está presente")
    @PastOrPresent(message = "No puede ser posterior al día de hoy")
    private LocalDate fechaExpedicion;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
    private Integer cantidad;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
    private Double peso;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
    private Double importe;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
    private Long idCliente;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
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
