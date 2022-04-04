package com.brikton.lachacra.dtos.rendimiento;

import com.brikton.lachacra.entities.Lote;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class RendimientoDiaDTO extends RendimientoDTO {

    private LocalDate fecha;

    public RendimientoDiaDTO() {
    }

    public RendimientoDiaDTO(Lote lote) {
        rendimiento = lote.getRendimiento();
        fecha = lote.getFechaElaboracion();
    }
}
