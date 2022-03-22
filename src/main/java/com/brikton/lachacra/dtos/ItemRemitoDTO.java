package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.ItemRemito;
import lombok.Data;

@Data
public class ItemRemitoDTO {

    private Integer cantidad;
    private String tipoQueso;
    private Double peso, precio, importe;

    public ItemRemitoDTO(ItemRemito itemRemito){
        this.cantidad = itemRemito.getCantidad();
        this.tipoQueso = itemRemito.getQueso().getTipoQueso();
        this.peso = itemRemito.getPeso();
        this.precio = itemRemito.getPrecio();
        this.importe = itemRemito.getImporte();
    }
}
