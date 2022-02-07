package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Queso;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class QuesoDTO {

    @NotNull(message = "No está presente")
    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String codigo;

    @NotNull(message = "No está presente")
    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String tipoQueso;

    @NotNull(message = "No está presente")
    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String nomenclatura;

    private Long id;
    private Integer stock;

    public QuesoDTO() {
    }

    public QuesoDTO(Queso queso) {
        this.setId(queso.getId());
        this.setCodigo(queso.getCodigo());
        this.setTipoQueso(queso.getTipoQueso());
        this.setNomenclatura(queso.getNomenclatura());
        this.setStock(queso.getStock());
    }
}
