package com.brikton.lachacra.entities;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class VentaDia {

    private LocalDate fecha;
    private Integer total;
    private Map<String,Venta> ventas;

    public VentaDia(){
        ventas = new HashMap<>();
    }
}
