package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.constants.ValidationMessages;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PrecioDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 0, message = ValidationMessages.CANNOT_BE_LESS_THAN_0)
    private Double precio;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 3, message = ValidationMessages.MUST_NOT_EXCEED_3_CHARACTERS)
    private String codigoQueso;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 0, message = ValidationMessages.CANNOT_BE_LESS_THAN_0)
    private Long idTipoCliente;

    private Long id;

    public PrecioDTO() {
    }

    public PrecioDTO(Precio precio) {
        this.setId(precio.getId());
        this.setCodigoQueso(precio.getQueso().getCodigo());
        this.setIdTipoCliente(precio.getTipoCliente().getId());
        this.setPrecio(precio.getPrecio());
    }
}
