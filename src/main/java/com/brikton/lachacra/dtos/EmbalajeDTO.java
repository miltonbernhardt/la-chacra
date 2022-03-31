package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.entities.Embalaje;
import com.brikton.lachacra.entities.TipoEmbalaje;
import com.brikton.lachacra.util.validation.ValidateString;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
@Data
public class EmbalajeDTO {

    private Long id;
    @NotNull(message = ValidationMessages.NOT_FOUND)
    @ValidateString(acceptedValues = {"CAJA","BOLSA"},message = ValidationMessages.INVALID_FORMAT)
    private String tipoEmbalaje;
    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Integer stock;
    @NotNull(message = ValidationMessages.NOT_FOUND)
    private List<QuesoDTO> listaQuesos;

    public EmbalajeDTO(Embalaje embalaje){
        this.id = embalaje.getId();
        this.tipoEmbalaje = embalaje.getTipoEmbalaje().toString();
        this.stock = embalaje.getStock();
        var quesosDTO = new ArrayList<QuesoDTO>();
        embalaje.getListaQuesos().forEach(queso -> quesosDTO.add(new QuesoDTO(queso)));
        listaQuesos = quesosDTO;
    }
}
