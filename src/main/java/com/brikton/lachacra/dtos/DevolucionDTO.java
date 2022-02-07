package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Devolucion;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class DevolucionDTO {

    @NotNull(message = "No está presente")
    @PastOrPresent(message = "No puede ser posterior al día de hoy")
    private LocalDate fecha;

    @NotNull(message = "No está presente")
    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String motivo;

    @NotNull(message = "No está presente")
    private Integer cantidad;

    @NotNull(message = "No está presente")
    private Double peso;

    @NotNull(message = "No está presente")
    private Double temperatura;

    @NotNull(message = "No está presente")
    private Long idCliente;

    @NotNull(message = "No está presente")
    private Long idLoteProducto;

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
