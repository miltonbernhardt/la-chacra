package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.entities.Precio;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PrecioDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Double precio;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Long idQueso;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
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
