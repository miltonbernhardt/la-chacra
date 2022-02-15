package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.services.QuesoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {QuesoController.class})
public class QuesoControllerTest {

    @Autowired
    QuesoController quesoController;

    @MockBean
    QuesoService quesoService;

    @Test
    void Get__OK() throws QuesoNotFoundException {
        when(quesoService.get("001")).thenReturn(mockQuesoDTO());
        var dtoActual = Objects.requireNonNull(quesoController.get("001").getBody()).getData();
        QuesoDTO dtoExpected = mockQuesoDTO();
        assertEquals(dtoExpected, dtoActual);
    }

    @Test
    void Get__Queso_Not_Found() throws QuesoNotFoundException {
        when(quesoService.get("001")).thenThrow(new QuesoNotFoundException());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoController.get("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_All__OK() {
        when(quesoService.getAll()).thenReturn(List.of(mockQuesoDTO(), mockQuesoDTO()));
        var actualDTOs = Objects.requireNonNull(quesoController.getAll().getBody()).getData();
        assertEquals(mockQuesoDTO(), actualDTOs.get(0));
        assertEquals(mockQuesoDTO(), actualDTOs.get(1));
    }

    QuesoDTO mockQuesoDTO(){
        QuesoDTO queso = new QuesoDTO();
        queso.setCodigo("001");
        queso.setTipoQueso("tipoQueso");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }
}
