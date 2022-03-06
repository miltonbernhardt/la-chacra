package com.brikton.lachacra.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping(value = "{path:^(?!/api/v1).*}")
    public String forward() {
        return "index.html";
    }
}
