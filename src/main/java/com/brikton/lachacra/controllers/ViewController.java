package com.brikton.lachacra.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping(value = "/")
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/clientes")
    public String clientes() {
        return "forward:/";
    }

    @RequestMapping(value = "/cargar/lote")
    public String cargarLotes() {
        return "index";
    }

    @RequestMapping(value = "/cargar/quesos")
    public String cargarQuesos() {
        return "index";
    }
}
