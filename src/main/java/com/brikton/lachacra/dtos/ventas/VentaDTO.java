package com.brikton.lachacra.dtos.ventas;

import com.brikton.lachacra.entities.Venta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VentaDTO {

    private Integer cantidad;
    private String codigoQueso;

    public VentaDTO(Venta venta) {
        cantidad = venta.getCantidad();
        codigoQueso = venta.getCodigoQueso();
    }
}
