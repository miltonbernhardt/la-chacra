package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.entities.Embalaje;
import com.brikton.lachacra.util.validation.ValidateString;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmbalajeUpdateDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Long id;
    @NotNull(message = ValidationMessages.NOT_FOUND)
    //TODO this validation does not work
    @ValidateString(acceptedValues = {"CAJA","BOLSA"},message = ValidationMessages.INVALID_FORMAT)
    private String tipoEmbalaje;
    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Integer stock;
    @NotNull(message = ValidationMessages.NOT_FOUND)
    private List<QuesoDTO> listaQuesos;

    public EmbalajeUpdateDTO(){}
}
