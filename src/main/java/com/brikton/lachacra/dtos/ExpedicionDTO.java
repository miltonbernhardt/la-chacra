package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.entities.Expedicion;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ExpedicionDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @PastOrPresent(message = ValidationMessages.CANT_BE_LATER_THAN_TODAY)
    private LocalDate fechaExpedicion;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Integer cantidad;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Double peso;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Long idCliente;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String idLote;

    private Double importe;
    private Long id;
    private Boolean onRemito;

    public ExpedicionDTO(Expedicion expedicion) {
        this.setId(expedicion.getId());
        this.setFechaExpedicion(expedicion.getFechaExpedicion());
        this.setCantidad(expedicion.getCantidad());
        this.setPeso(expedicion.getPeso());
        this.setImporte(expedicion.getImporte());
        this.setIdCliente(expedicion.getCliente().getId());
        this.setIdLote(expedicion.getLote().getId());
        this.setOnRemito(expedicion.getOnRemito());
    }

    public ExpedicionDTO(ExpedicionUpdateDTO expedicion) {
        this.setId(expedicion.getId());
        this.setFechaExpedicion(expedicion.getFechaExpedicion());
        this.setCantidad(expedicion.getCantidad());
        this.setPeso(expedicion.getPeso());
        this.setImporte(expedicion.getImporte());
        this.setIdCliente(expedicion.getIdCliente());
        this.setIdLote(expedicion.getIdLote());
    }
}
