package com.brikton.lachacra.util;

import com.brikton.lachacra.dtos.RendimientoDiaDTO;
import com.brikton.lachacra.dtos.litrosElaborados.LitrosElaboradosDiaDTO;
import com.brikton.lachacra.dtos.ventas.VentaDiaDTO;

import java.util.Comparator;

public class DateComparator implements Comparator {

    @Override
    public int compare(Object o, Object t1) {
        if (o.getClass() == RendimientoDiaDTO.class)
            return compareRendimiento((RendimientoDiaDTO) o, (RendimientoDiaDTO) t1);
        if (o.getClass() == VentaDiaDTO.class) return compareVenta((VentaDiaDTO) o, (VentaDiaDTO) t1);
        if (o.getClass() == LitrosElaboradosDiaDTO.class)
            return compareLitrosElaborados((LitrosElaboradosDiaDTO) o, (LitrosElaboradosDiaDTO) t1);
        return 0;
    }

    private int compareRendimiento(RendimientoDiaDTO r1, RendimientoDiaDTO r2) {
        if (r1.getFecha().isBefore(r2.getFecha())) return -1;
        return 1;
    }

    private int compareVenta(VentaDiaDTO r1, VentaDiaDTO r2) {
        if (r1.getFecha().isBefore(r2.getFecha())) return -1;
        return 1;
    }

    private int compareLitrosElaborados(LitrosElaboradosDiaDTO r1, LitrosElaboradosDiaDTO r2) {
        if (r1.getFecha().isBefore(r2.getFecha())) return -1;
        return 1;
    }
}
