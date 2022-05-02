package com.brikton.lachacra.dtos.litrosElaborados;

import com.brikton.lachacra.entities.LitrosElaboradosDia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LitrosElaboradosDiaDTO {

    private LocalDate fecha;
    private Double total;
    private List<LitrosElaboradosDTO> litrosElaborados;

    public LitrosElaboradosDiaDTO(LitrosElaboradosDia litrosElaboradosDia) {
        fecha = litrosElaboradosDia.getFecha();
        total = litrosElaboradosDia.getTotal();
        litrosElaborados = new ArrayList<>();
        var listaLitros = new ArrayList<>(litrosElaboradosDia.getLitrosElaborados().values());
        listaLitros.forEach(litros -> litrosElaborados.add(new LitrosElaboradosDTO(litros)));
    }
}
