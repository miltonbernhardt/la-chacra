package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.entities.Queso;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class QuesoDTO {

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(min = 3, max = 3, message = ValidationMessages.MUST_HAVE_3_CHARACTERS)
    private String codigo;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String tipoQueso;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String nomenclatura;

    private Long id;
    private Integer stock;
    private String color;

    public QuesoDTO(Queso queso) {
        this.setId(queso.getId());
        this.setCodigo(queso.getCodigo());
        this.setTipoQueso(queso.getTipoQueso());
        this.setNomenclatura(queso.getNomenclatura());
        this.setStock(queso.getStock());
        this.setColor(queso.getColor());
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
