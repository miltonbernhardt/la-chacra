package com.brikton.lachacra.entities;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class LitrosElaboradosDia {

    private LocalDate fecha;
    private Double total;
    private Map<String,LitrosElaborados> litrosElaborados;

    public LitrosElaboradosDia(){
        litrosElaborados = new HashMap<>();
    }
}
