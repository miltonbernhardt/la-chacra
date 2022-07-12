package com.brikton.lachacra.entities;

import lombok.Data;

@Data
public class ItemRemito {

    private Integer cantidad;
    private Queso queso;
    private Double peso, precio, importe;

}
