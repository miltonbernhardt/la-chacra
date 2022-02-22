package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Remito;
import com.brikton.lachacra.constants.ValidationMessages;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class RemitoDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @PastOrPresent(message = ValidationMessages.CANT_BE_LATER_THAN_TODAY)
    private LocalDate fecha;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Double importeTotal;

//    private List<ExpedicionDTO> expediciones;

    private Long id;

    public RemitoDTO() {
    }

    public RemitoDTO(Remito remito) {
        this.setId(remito.getId());
        this.setFecha(remito.getFecha());
        this.setImporteTotal(remito.getImporteTotal());
    }
}
