package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.constants.ValidationMessages;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TipoClienteDTO {

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String tipo;

    private Long id;

    public TipoClienteDTO() {
    }

    public TipoClienteDTO(TipoCliente tipoCliente) {
        this.setId(tipoCliente.getId());
        this.setTipo(tipoCliente.getTipo());
    }
}


