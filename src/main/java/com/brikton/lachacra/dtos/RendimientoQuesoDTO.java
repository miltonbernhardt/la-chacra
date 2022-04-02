package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Lote;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RendimientoQuesoDTO extends RendimientoDTO {

    private QuesoDTO queso;

    public RendimientoQuesoDTO() {
    }

    public RendimientoQuesoDTO(Lote lote) {
        rendimiento = lote.getRendimiento();
        queso = new QuesoDTO(lote.getQueso());
    }
}
