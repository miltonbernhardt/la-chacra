package com.brikton.lachacra.entities;

import lombok.Data;

@Data
public class ItemRemito {

    private Integer cantidad;
    private Queso queso;
    private Double peso, precio, importe;

    public void update(Expedicion e) {
        if (e.getLote().getQueso().getTipoQueso().equals(queso.getTipoQueso())) {
            cantidad += e.getCantidad();
            peso += e.getPeso();
            importe += e.getImporte();
        }
    }
}
