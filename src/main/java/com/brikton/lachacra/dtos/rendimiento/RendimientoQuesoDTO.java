package com.brikton.lachacra.dtos.rendimiento;

import com.brikton.lachacra.entities.Lote;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class RendimientoQuesoDTO extends RendimientoDTO{

    private String codigoQueso;

    public RendimientoQuesoDTO(){}

    public RendimientoQuesoDTO(Lote lote){
        rendimiento = lote.getRendimiento();
        codigoQueso = lote.getQueso().getCodigo();
    }
}
