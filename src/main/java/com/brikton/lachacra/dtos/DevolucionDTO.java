package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Devolucion;
import com.brikton.lachacra.constants.ValidationMessages;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class DevolucionDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @PastOrPresent(message = ValidationMessages.CANT_BE_LATER_THAN_TODAY)
    private LocalDate fecha;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String motivo;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    private Integer cantidad;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    private Double peso;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    private Double temperatura;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    private Long idCliente;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    @Pattern(regexp = "^[0-9]{12,13}$", message = ValidationMessages.INVALID_FORMAT)
    private String idLoteProducto;

    private Long id;

    public DevolucionDTO() {
    }

    public DevolucionDTO(Devolucion devolucion) {
        this.setId(devolucion.getId());
        this.setFecha(devolucion.getFecha());
        this.setMotivo(devolucion.getMotivo());
        this.setCantidad(devolucion.getCantidad());
        this.setPeso(devolucion.getPeso());
        this.setTemperatura(devolucion.getTemperatura());
        this.setPeso(devolucion.getPeso());
        this.setIdCliente(devolucion.getCliente().getId());
        this.setIdLoteProducto(devolucion.getLoteProducto().getId());
    }
}
