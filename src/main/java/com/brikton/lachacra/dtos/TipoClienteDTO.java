package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.TipoCliente;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class TipoClienteDTO {

    @Length(max = 255, message = "No debe superar los 255 caracteres")
    @NotNull(message = "No está presente")
    private String tipo;

    private Long id;

    public TipoClienteDTO() {
    }

    public TipoClienteDTO(TipoCliente tipoCliente) {
        this.setId(tipoCliente.getId());
        this.setTipo(tipoCliente.getTipo());
    }
}


