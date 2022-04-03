package com.brikton.lachacra.dtos.ventas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.entities.VentaDia;
import lombok.Data;

@Data
public class VentaDiaDTO {

    private LocalDate fecha;
    private Integer total;
    private List<VentaDTO> ventas;

    public VentaDiaDTO(VentaDia ventaDia) {
     fecha=ventaDia.getFecha();
     total=ventaDia.getTotal();
     ventas = new ArrayList<>();
     var listaVentas = new ArrayList<>(ventaDia.getVentas().values());
     listaVentas.forEach(venta -> ventas.add(new VentaDTO(venta)));
    }
}
