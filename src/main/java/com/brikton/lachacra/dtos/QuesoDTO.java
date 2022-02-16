package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.constants.ValidationMessages;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class QuesoDTO {

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 3, message = ValidationMessages.MUST_NOT_EXCEED_3_CHARACTERS)
    private String codigo;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String tipoQueso;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String nomenclatura;

    private Integer stock;

    public QuesoDTO() {
    }

    public QuesoDTO(Queso queso) {
        this.setCodigo(queso.getCodigo());
        this.setTipoQueso(queso.getTipoQueso());
        this.setNomenclatura(queso.getNomenclatura());
        this.setStock(queso.getStock());
    }
}
