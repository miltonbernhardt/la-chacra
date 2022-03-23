package com.brikton.lachacra.util;

import com.brikton.lachacra.dtos.rendimiento.RendimientoDTO;
import com.brikton.lachacra.dtos.rendimiento.RendimientoDiaDTO;

import java.util.Comparator;

public class DateComparator implements Comparator {

    @Override
    public int compare(Object o, Object t1) {
        if (o.getClass() == RendimientoDiaDTO.class) return compareRendimiento((RendimientoDiaDTO) o,(RendimientoDiaDTO) t1);
        return 0;
    }

    private int compareRendimiento(RendimientoDiaDTO r1, RendimientoDiaDTO r2){
        if (r1.getFecha().isBefore(r2.getFecha())) return -1;
        return 1;
    }
}
