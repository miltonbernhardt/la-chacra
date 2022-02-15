package com.brikton.lachacra.controllers;

import com.brikton.lachacra.services.QuesoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {QuesoController.class})
public class QuesoControllerTest {

    @Autowired
    QuesoController loteController;

    @MockBean
    QuesoService loteService;

    @Test
    void Get__OK() {
        //todo
    }

    @Test
    void Get__Queso_Not_Found() {
        //todo
    }

    @Test
    void Get_All__OK() {
        //todo
    }
}
