package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.services.QuesoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void Save__OK() {
        QuesoDTO dtoToSave = new QuesoDTO();
        dtoToSave.setCodigo("001");
        dtoToSave.setTipoQueso("tipoQueso");
        dtoToSave.setNomenclatura("tip");
        dtoToSave.setStock(1);

        when(quesoService.save(any(QuesoDTO.class))).thenReturn(dtoToSave);
        QuesoDTO dtoActual = requireNonNull(quesoController.save(dtoToSave).getBody()).getData();
        String message = requireNonNull(quesoController.save(dtoToSave).getBody()).getMessage();
        assertEquals(dtoToSave, dtoActual);
        assertEquals(SuccessfulMessages.MSG_QUESO_CREATED, message);
    }

    @Test
    void Update__OK() throws QuesoNotFoundException {
        QuesoDTO dtoToUpdate = new QuesoDTO();
        dtoToUpdate.setCodigo("001");
        dtoToUpdate.setTipoQueso("tipoQueso");
        dtoToUpdate.setNomenclatura("tip");
        dtoToUpdate.setStock(1);

        when(quesoService.update(any(QuesoDTO.class))).thenReturn(dtoToUpdate);
        QuesoDTO dtoActual = requireNonNull(quesoController.update(dtoToUpdate).getBody()).getData();
        String message = requireNonNull(quesoController.update(dtoToUpdate).getBody()).getMessage();
        assertEquals(dtoToUpdate, dtoActual);
        assertEquals(SuccessfulMessages.MSG_QUESO_UPDATED, message);
    }

    @Test
    void Update__Queso_Not_Found() throws QuesoNotFoundException {
        QuesoDTO dtoToUpdate = new QuesoDTO();
        dtoToUpdate.setCodigo("001");
        dtoToUpdate.setTipoQueso("tipoQueso");
        dtoToUpdate.setNomenclatura("tip");
        dtoToUpdate.setStock(1);

        when(quesoService.update(dtoToUpdate)).thenThrow(new QuesoNotFoundException());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoController.update(dtoToUpdate)
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Delete__OK() throws QuesoNotFoundException {
        when(quesoService.delete("001")).thenReturn("001");
        var actualID = Objects.requireNonNull(quesoController.delete("001").getBody()).getData();
        assertEquals("001", actualID);
    }

    @Test
    void Delete__Queso_Not_Found() throws QuesoNotFoundException {
        when(quesoService.delete("001")).thenThrow(new QuesoNotFoundException());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoController.delete("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
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
