package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Lote;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RendimientoDTO {

    private Double rendimiento;
    private String codigoQueso;
    private LocalDate fecha;

    public RendimientoDTO(Lote lote){
        rendimiento = lote.getRendimiento();
        codigoQueso = lote.getQueso().getCodigo();
        fecha = lote.getFechaElaboracion();
    }
}
