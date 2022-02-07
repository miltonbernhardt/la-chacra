package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Remito;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class RemitoDTO {

    @NotNull(message = "No está presente")
    @PastOrPresent(message = "No puede ser posterior al día de hoy")
    private LocalDate fecha;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
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
