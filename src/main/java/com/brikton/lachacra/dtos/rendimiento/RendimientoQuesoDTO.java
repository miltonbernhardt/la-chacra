package com.brikton.lachacra.dtos.rendimiento;

import com.brikton.lachacra.dtos.QuesoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RendimientoQuesoDTO extends RendimientoDTO {

    private QuesoDTO queso;

    public RendimientoQuesoDTO() {
    }
}
