package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.entities.TipoCliente;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class TipoClienteDTO {

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String tipo;

    private Long id;

    public TipoClienteDTO(TipoCliente tipoCliente) {
        this.setId(tipoCliente.getId());
        this.setTipo(tipoCliente.getTipo());
    }
}


