package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.LoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = LoteService.class)
public class LoteServiceTest {

    @Autowired
    LoteService loteService;

    @MockBean
    LoteRepository repository;

    @MockBean
    QuesoService quesoService;

    @Test
    void Get_Lote__OK() throws LoteNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.of(mockLote()));
        LoteDTO dtoActual = loteService.get(1L);
        LoteDTO dtoExpected = mockLoteDTO();
        assertEquals(dtoExpected, dtoActual);
    }

    @Test
    void Get_Lote__Lote_Not_Found() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        LoteNotFoundException thrown = assertThrows(
                LoteNotFoundException.class, () -> loteService.get(1L)
        );
        assertEquals("Lote no encontrado", thrown.getMessage());
    }

    @Test
    void Save_Lote__OK() throws QuesoNotFoundException, NotFoundConflictException {
        when(quesoService.get(1L)).thenReturn(mockQueso());
        when(repository.save(any(Lote.class))).thenReturn(mockLote());
        LoteDTO dtoActual = loteService.save(mockLoteDTO());
        LoteDTO dtoExpected = mockLoteDTO();
        assertEquals(dtoExpected, dtoActual);
    }

    @Test
    void Save_Lote__Queso_Not_Found() throws QuesoNotFoundException {
        when(quesoService.get(1L)).thenThrow(QuesoNotFoundException.class);
        NotFoundConflictException thrown = assertThrows(
                NotFoundConflictException.class, () -> loteService.save(mockLoteDTO())
        );
        assertEquals("Queso no encontrado", thrown.getMessage());
    }

    Lote mockLote() {
        Lote lote = new Lote();
        lote.setId(1L);
        lote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        lote.setNumeroTina(1);
        lote.setLitrosLeche(1D);
        lote.setCantHormas(1);
        lote.setStockLote(1);
        lote.setPeso(1D);
        lote.setRendimiento(1D);
        lote.setLoteCultivo("cultivo1, cultivo2");
        lote.setLoteColorante("colorante1, colorante2");
        lote.setLoteCalcio("calcio1, calcio2");
        lote.setLoteCuajo("cuajo1, cuajo2");
        lote.setQueso(mockQueso());
        return lote;
    }

    Queso mockQueso() {
        Queso queso = new Queso();
        queso.setTipoQueso("tipoQueso");
        queso.setCodigo("001");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
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
        dto.setLoteCultivo("cultivo1, cultivo2");
        dto.setLoteColorante("colorante1, colorante2");
        dto.setLoteCalcio("calcio1, calcio2");
        dto.setLoteCuajo("cuajo1, cuajo2");
        dto.setIdQueso(1L);
        return dto;
    }
}
