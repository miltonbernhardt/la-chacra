package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.LoteUpdateDTO;
import com.brikton.lachacra.services.LoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {LoteController.class})
public class LoteControllerTest {

    @Autowired
    LoteController loteController;

    @MockBean
    LoteService loteService;

    @Test
    void Get_All__OK() {
        when(loteService.getAll()).thenReturn(List.of(mockLoteDTO1(), mockLoteDTO2()));
        var listOfDTOs = requireNonNull(loteController.getAll().getBody()).getData();
        LoteDTO dtoExpected1 = mockLoteDTO1();
        LoteDTO dtoExpected2 = mockLoteDTO2();
        assertEquals(2, listOfDTOs.size());
        assertEquals(dtoExpected1, listOfDTOs.get(0));
        assertEquals(dtoExpected2, listOfDTOs.get(1));
    }

//    @Test
//    void Save__OK() throws NotFoundConflictException {
//        when(loteService.save(any(LoteDTO.class))).thenReturn(mockLoteDTO1());
//        LoteDTO dtoActual = requireNonNull(loteController.save(mockLoteDTO1()).getBody()).getData();
//        String message = requireNonNull(loteController.save(mockLoteDTO1()).getBody()).getMessage();
//        LoteDTO dtoExpected = mockLoteDTO1();
//        assertEquals(dtoExpected, dtoActual);
//        assertEquals(SuccessfulMessages.MSG_LOTE_CREATED, message);
//    }
//
//    @Test
//    void Save__Queso_Not_Found() throws NotFoundConflictException {
//        when(loteService.save(any(LoteDTO.class))).thenThrow(new NotFoundConflictException(ErrorMessages.MSG_QUESO_NOT_FOUND));
//        NotFoundConflictException thrown = assertThrows(
//                NotFoundConflictException.class, () -> loteController.save(mockLoteDTO1())
//        );
//        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
//    }
//
//    @Test
//    void Update__OK() throws NotFoundConflictException, LoteNotFoundException {
//        when(loteService.update(any(LoteUpdateDTO.class))).thenReturn(mockLoteDTO1());
//        LoteDTO dtoActual = requireNonNull(loteController.update(mockLoteUpdateDTO1()).getBody()).getData();
//        String message = requireNonNull(loteController.update(mockLoteUpdateDTO1()).getBody()).getMessage();
//        LoteDTO dtoExpected = mockLoteDTO1();
//        assertEquals(dtoExpected, dtoActual);
//        assertEquals(SuccessfulMessages.MSG_LOTE_UPDATED, message);
//    }
//
//    @Test
//    void Update__Lote_Not_Found() throws LoteNotFoundException, NotFoundConflictException {
//        when(loteService.update(any(LoteUpdateDTO.class))).thenThrow(new LoteNotFoundException());
//        LoteNotFoundException thrown = assertThrows(
//                LoteNotFoundException.class, () -> loteController.update(mockLoteUpdateDTO1())
//        );
//        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
//    }
//
//    @Test
//    void Save__Queso_Not_Found_Conflict() throws NotFoundConflictException, LoteNotFoundException {
//        when(loteService.update(any(LoteUpdateDTO.class))).thenThrow(new NotFoundConflictException("queso not found"));
//        NotFoundConflictException thrown = assertThrows(
//                NotFoundConflictException.class, () -> loteController.update(mockLoteUpdateDTO1())
//        );
//        assertEquals("queso not found", thrown.getMessage());
//    }
//
//    @Test
//    void Delete__OK() throws LoteNotFoundException {
//        when(loteService.delete("101020210011")).thenReturn("101020210011");
//        var actualID = (String) requireNonNull(loteController.delete("101020210011").getBody()).getData();
//        var message = (String) requireNonNull(loteController.delete("101020210011").getBody()).getMessage();
//        assertEquals("101020210011", actualID);
//        assertEquals(SuccessfulMessages.MSG_LOTE_DELETED, message);
//    }
//
//    @Test
//    void Delete__Lote_Not_Found() throws LoteNotFoundException {
//        when(loteService.delete("101020210011")).thenThrow(new LoteNotFoundException());
//        LoteNotFoundException thrown = assertThrows(
//                LoteNotFoundException.class, () -> loteController.delete("101020210011")
//        );
//        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
//    }

    LoteUpdateDTO mockLoteUpdateDTO1() {
        LoteUpdateDTO dto = new LoteUpdateDTO();
        dto.setId("101020210011");
        dto.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dto.setNumeroTina(1);
        dto.setLitrosLeche(1D);
        dto.setCantHormas(1);
        dto.setStockLote(1);
        dto.setPeso(1D);
        dto.setRendimiento(1D);
        dto.setLoteCultivo("cultivo1, cultivo2");
        dto.setLoteColorante("colorante1, colorante2");
        dto.setLoteCalcio("calcio1, calcio2");
        dto.setLoteCuajo("cuajo1, cuajo2");
        dto.setIdQueso(1L);
        return dto;
    }

    LoteDTO mockLoteDTO1() {
        LoteDTO dto = new LoteDTO();
        dto.setId("101020210011");
        dto.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dto.setNumeroTina(1);
        dto.setLitrosLeche(1D);
        dto.setCantHormas(1);
        dto.setStockLote(1);
        dto.setPeso(1D);
        dto.setRendimiento(1D);
        dto.setLoteCultivo("cultivo1, cultivo2");
        dto.setLoteColorante("colorante1, colorante2");
        dto.setLoteCalcio("calcio1, calcio2");
        dto.setLoteCuajo("cuajo1, cuajo2");
        dto.setIdQueso(1L);
        return dto;
    }

    LoteDTO mockLoteDTO2() {
        LoteDTO dto = new LoteDTO();
        dto.setId("111020210022");
        dto.setFechaElaboracion(LocalDate.of(2021, 10, 11));
        dto.setNumeroTina(2);
        dto.setLitrosLeche(2D);
        dto.setCantHormas(2);
        dto.setStockLote(2);
        dto.setPeso(2D);
        dto.setRendimiento(2D);
        dto.setLoteCultivo("cultivo1, cultivo2");
        dto.setLoteColorante("colorante1, colorante2");
        dto.setLoteCalcio("calcio1, calcio2");
        dto.setLoteCuajo("cuajo1, cuajo2");
        dto.setIdQueso(2L);
        return dto;
    }
}
