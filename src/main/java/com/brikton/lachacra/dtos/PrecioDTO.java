package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Precio;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PrecioDTO {

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
    private Double precio;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
    private Long idQueso;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
    private Long idTipoCliente;

    private Long id;

    public PrecioDTO() {
    }

    public PrecioDTO(Precio precio) {
        this.setId(precio.getId());
        this.setIdQueso(precio.getQueso().getId());
        this.setIdTipoCliente(precio.getTipoCliente().getId());
        this.setPrecio(precio.getPrecio());
    }
}
