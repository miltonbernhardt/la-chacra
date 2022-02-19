package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.exceptions.CodigoQuesoAlreadyExistsException;
import com.brikton.lachacra.exceptions.NomQuesoAlreadyExistsException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.services.QuesoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

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
    void Get_All__OK() {
        when(quesoService.getAll()).thenReturn(List.of(mockQuesoDTO(), mockQuesoDTO()));
        var actualDTOs = requireNonNull(quesoController.getAll().getBody()).getData();
        assertEquals(mockQuesoDTO(), actualDTOs.get(0));
        assertEquals(mockQuesoDTO(), actualDTOs.get(1));
    }

    @Test
    void Save__OK() throws CodigoQuesoAlreadyExistsException, NomQuesoAlreadyExistsException {
        QuesoDTO dtoToSave = new QuesoDTO();
        dtoToSave.setCodigo("001");
        dtoToSave.setTipoQueso("tipoQueso");
        dtoToSave.setNomenclatura("tip");
        dtoToSave.setStock(1);

        QuesoDTO dtoReturned = new QuesoDTO();
        dtoToSave.setId(1L);
        dtoToSave.setTipoQueso("tipoQueso");
        dtoToSave.setNomenclatura("tip");
        dtoToSave.setStock(1);

        when(quesoService.save(any(QuesoDTO.class))).thenReturn(dtoReturned);

        var response = requireNonNull(quesoController.save(dtoToSave).getBody());

        QuesoDTO dtoActual = response.getData();
        String message = response.getMessage();
        assertEquals(dtoReturned, dtoActual);
        assertEquals(SuccessfulMessages.MSG_QUESO_CREATED, message);
    }

    @Test
    void Save__Codigo_Queso_Already_Exists() throws CodigoQuesoAlreadyExistsException, NomQuesoAlreadyExistsException {
        QuesoDTO dto = new QuesoDTO();
        when(quesoService.save(dto)).thenThrow(new CodigoQuesoAlreadyExistsException());
        CodigoQuesoAlreadyExistsException thrown = assertThrows(
                CodigoQuesoAlreadyExistsException.class, () -> quesoController.save(dto)
        );
        assertEquals(ErrorMessages.MSG_CODIGO_QUESO_ALREADY_EXIST, thrown.getMessage());
    }

    @Test
    void Save__Nomenclatura_Queso_Already_Exists() throws CodigoQuesoAlreadyExistsException, NomQuesoAlreadyExistsException {
        QuesoDTO dto = new QuesoDTO();
        when(quesoService.save(dto)).thenThrow(new NomQuesoAlreadyExistsException());
        NomQuesoAlreadyExistsException thrown = assertThrows(
                NomQuesoAlreadyExistsException.class, () -> quesoController.save(dto)
        );
        assertEquals(ErrorMessages.MSG_NOMENCLATURE_QUESO_ALREADY_EXIST, thrown.getMessage());
    }

    @Test
    void Update__OK() throws QuesoNotFoundException, NomQuesoAlreadyExistsException, CodigoQuesoAlreadyExistsException {
        QuesoDTO dtoToUpdate = new QuesoDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setCodigo("001");
        dtoToUpdate.setTipoQueso("tipoQueso");
        dtoToUpdate.setNomenclatura("tip");
        dtoToUpdate.setStock(1);

        QuesoDTO dtoExpected = new QuesoDTO();
        dtoExpected.setId(1L);
        dtoExpected.setCodigo("001");
        dtoExpected.setTipoQueso("tipoQueso");
        dtoExpected.setNomenclatura("tip");
        dtoExpected.setStock(1);

        when(quesoService.update(dtoToUpdate)).thenReturn(dtoExpected);

        var response = requireNonNull(quesoController.update(dtoToUpdate).getBody());

        QuesoDTO dtoActual = response.getData();
        String message = response.getMessage();
        assertEquals(dtoExpected, dtoActual);
        assertEquals(SuccessfulMessages.MSG_QUESO_UPDATED, message);
    }

    @Test
    void Update__Queso_Not_Found() throws QuesoNotFoundException, NomQuesoAlreadyExistsException, CodigoQuesoAlreadyExistsException {
        QuesoDTO dtoToUpdate = new QuesoDTO();
        dtoToUpdate.setId(1L);
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
    void Update__Codigo_Queso_Already_Exists() throws QuesoNotFoundException, NomQuesoAlreadyExistsException, CodigoQuesoAlreadyExistsException {
        QuesoDTO dtoToUpdate = new QuesoDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setCodigo("001");
        dtoToUpdate.setTipoQueso("tipoQueso");
        dtoToUpdate.setNomenclatura("tip");
        dtoToUpdate.setStock(1);

        when(quesoService.update(dtoToUpdate)).thenThrow(new CodigoQuesoAlreadyExistsException());
        CodigoQuesoAlreadyExistsException thrown = assertThrows(
                CodigoQuesoAlreadyExistsException.class, () -> quesoController.update(dtoToUpdate)
        );
        assertEquals(ErrorMessages.MSG_CODIGO_QUESO_ALREADY_EXIST, thrown.getMessage());
    }

    @Test
    void Update__Nomenclatura_Queso_Already_Exists() throws QuesoNotFoundException, NomQuesoAlreadyExistsException, CodigoQuesoAlreadyExistsException {
        QuesoDTO dtoToUpdate = new QuesoDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setCodigo("001");
        dtoToUpdate.setTipoQueso("tipoQueso");
        dtoToUpdate.setNomenclatura("tip");
        dtoToUpdate.setStock(1);

        when(quesoService.update(dtoToUpdate)).thenThrow(new NomQuesoAlreadyExistsException());
        NomQuesoAlreadyExistsException thrown = assertThrows(
                NomQuesoAlreadyExistsException.class, () -> quesoController.update(dtoToUpdate)
        );
        assertEquals(ErrorMessages.MSG_NOMENCLATURE_QUESO_ALREADY_EXIST, thrown.getMessage());
    }

//    @Test
//    void Delete__OK() throws QuesoNotFoundException {
//        when(quesoService.delete("001")).thenReturn("001");
//        var actualID = Objects.requireNonNull(quesoController.delete("001").getBody()).getData();
//        assertEquals("001", actualID);
//    }
//
//    @Test
//    void Delete__Queso_Not_Found() throws QuesoNotFoundException {
//        when(quesoService.delete("001")).thenThrow(new QuesoNotFoundException());
//        QuesoNotFoundException thrown = assertThrows(
//                QuesoNotFoundException.class, () -> quesoController.delete("001")
//        );
//        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
//    }

    QuesoDTO mockQuesoDTO(){
        QuesoDTO queso = new QuesoDTO();
        queso.setCodigo("001");
        queso.setTipoQueso("tipoQueso");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }
}
