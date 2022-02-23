package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.TipoClienteDTO;
import com.brikton.lachacra.services.TipoClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {TipoClienteController.class})
public class TipoClienteControllerTest {

    @Autowired
    TipoClienteController controller;

    @MockBean
    TipoClienteService service;

    @Test
    void Get_All__OK() {
        var mockDTO1 = new TipoClienteDTO();
        mockDTO1.setId(1L);
        mockDTO1.setTipo("tipo1");

        var mockDTO2 = new TipoClienteDTO();
        mockDTO2.setId(2L);
        mockDTO2.setTipo("tipo2");


        when(service.getAll()).thenReturn(List.of(mockDTO1, mockDTO2));
        var actualDTOs = requireNonNull(controller.getAll().getBody()).getData();
        assertEquals(2, actualDTOs.size());
        assertEquals(mockDTO1, actualDTOs.get(0));
        assertEquals(mockDTO2, actualDTOs.get(1));
    }
}
