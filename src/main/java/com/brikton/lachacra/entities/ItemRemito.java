package com.brikton.lachacra.entities;

import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.services.PrecioService;
import lombok.Data;

@Data
public class ItemRemito {

    private Integer cantidad;
    private Queso queso;
    private Double peso,precio,importe;

    public void update(Expedicion e) {
        if (e.getLote().getQueso().equals(queso)) {
            cantidad+=e.getCantidad();
            peso+=e.getPeso();
            importe+=e.getImporte();
        }
    }
}
