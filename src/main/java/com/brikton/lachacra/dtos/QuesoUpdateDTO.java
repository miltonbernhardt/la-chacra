package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class QuesoUpdateDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Long id;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(min = 3, max = 3, message = ValidationMessages.MUST_HAVE_3_CHARACTERS)
    private String codigo;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String tipoQueso;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String nomenclatura;

    private Integer stock;
    private String color;

    public QuesoUpdateDTO() {
    }

    public String getCodigo() {
        return codigo != null ? codigo.toUpperCase() : null;
    }

    public String getTipoQueso() {
        return tipoQueso != null ? tipoQueso.toUpperCase() : null;
    }

    public String getNomenclatura() {
        return nomenclatura != null ? nomenclatura.toUpperCase() : null;
    }
}
