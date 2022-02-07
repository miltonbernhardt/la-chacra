package com.brikton.lachacra.controllers;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.LoteRepository;
import com.brikton.lachacra.services.LoteService;
import com.brikton.lachacra.services.QuesoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {LoteController.class})
public class LoteControllerTest {

    @Autowired
    LoteController loteController;

    @MockBean
    LoteService loteService;

    @Test
    void Get_Lote__OK() throws LoteNotFoundException {
        when(loteService.get(1L)).thenReturn(mockLoteDTO());
        var dtoActual = (LoteDTO) loteController.get(1L).getBody().getData();
        LoteDTO dtoExpected = mockLoteDTO();
        assertEquals(dtoExpected, dtoActual);
    }

    @Test
    void Get_Lote__Lote_Not_Found() throws LoteNotFoundException {
        when(loteService.get(1L)).thenThrow(new LoteNotFoundException());
        LoteNotFoundException thrown = assertThrows(
                LoteNotFoundException.class, () -> loteController.get(1L)
        );
        assertEquals("Lote no encontrado", thrown.getMessage());
    }

    @Test
    void Save_Lote__OK() throws NotFoundConflictException {
        when(loteService.save(any(LoteDTO.class))).thenReturn(mockLoteDTO());
        LoteDTO dtoActual = (LoteDTO) loteController.save(mockLoteDTO()).getBody().getData();
        LoteDTO dtoExpected = mockLoteDTO();
        assertEquals(dtoExpected, dtoActual);
    }

    @Test
    void Save_Lote__Lote_Not_Found() throws LoteNotFoundException {
        when(loteService.get(1L)).thenThrow(new LoteNotFoundException());
        LoteNotFoundException thrown = assertThrows(
                LoteNotFoundException.class, () -> loteController.get(1L)
        );
        assertEquals("Lote no encontrado", thrown.getMessage());
    }

    LoteDTO mockLoteDTO() {
        LoteDTO dto = new LoteDTO();
        dto.setId(1L);
        dto.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dto.setNumeroTina(1);
        dto.setLitrosLeche(1D);
        dto.setCantHormas(1);
        dto.setStockLote(1);
        dto.setPeso(1D);
        dto.setRendimiento(1D);
        dto.setCultivo("cultivo");
        dto.setLoteCultivo(Arrays.asList("cultivo1", "cultivo2"));
        dto.setLoteColorante(Arrays.asList("colorante1", "colorante2"));
        dto.setLoteCalcio(Arrays.asList("calcio1", "calcio2"));
        dto.setLoteCuajo(Arrays.asList("cuajo1", "cuajo2"));
        dto.setCodigoQueso("001");
        return dto;
    }
}
