package com.brikton.lachacra.dtos.litrosElaborados;

import com.brikton.lachacra.entities.LitrosElaborados;
import com.brikton.lachacra.entities.Venta;
import lombok.Data;

@Data
public class LitrosElaboradosDTO {

    private Double cantidad;
    private String codigoQueso;

    public LitrosElaboradosDTO(LitrosElaborados litros){
        cantidad = litros.getCantidad();
        codigoQueso = litros.getCodigoQueso();
    }
}
