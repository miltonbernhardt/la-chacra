package com.brikton.lachacra.dtos.litrosElaborados;

import com.brikton.lachacra.entities.LitrosElaborados;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LitrosElaboradosDTO {

    private Double cantidad;
    private String codigoQueso;

    public LitrosElaboradosDTO(LitrosElaborados litros) {
        cantidad = litros.getCantidad();
        codigoQueso = litros.getCodigoQueso();
    }
}
