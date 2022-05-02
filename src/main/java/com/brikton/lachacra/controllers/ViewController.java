package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class ViewController {
    @RequestMapping(value = {
            Path.LOGIN,
            Path.CLIENTES,
            Path.CARGAR_EXPEDICIONES,
            Path.CARGAR_LOTES,
            Path.CARGAR_PRECIOS,
            Path.CARGAR_QUESOS,
            Path.CLIENTES,
            Path.EMITIR_REMITO,
            Path.MANTENIMIENTO,
            Path.RENDIMIENTO,
            Path.STOCK_EMBALAJE,
            Path.STOCK_PRODUCTOS,
            Path.VER_LITROS,
            Path.VER_PRODUCCION,
            Path.VER_TRAZABILIDAD,
            Path.VER_VENTAS,
            Path.VER_REMITOS,
            Path.VER_EXPEDICIONES
    })
    public String getIndex() {
        return "index.html";
    }
}



