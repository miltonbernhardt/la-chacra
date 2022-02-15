package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.LoteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
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
    void Get__OK() throws LoteNotFoundException {
        when(repository.findById("1L")).thenReturn(Optional.of(mockLote()));
        LoteDTO dtoActual = loteService.get("1L");
        LoteDTO dtoExpected = mockLoteDTO();
        assertEquals(dtoExpected, dtoActual);
    }

    @Test
    void Get__Lote_Not_Found() {
        when(repository.findById("1L")).thenReturn(Optional.empty());
        LoteNotFoundException thrown = assertThrows(
                LoteNotFoundException.class, () -> loteService.get("1L")
        );
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_All__OK() {
        when(repository.findAll()).thenReturn(List.of(mockLote(), mockLote()));
        var actualLotes = loteService.getAll();
        LoteDTO dtoExpected = mockLoteDTO();
        assertEquals(2, actualLotes.size());
        assertEquals(dtoExpected, actualLotes.get(0));
        assertEquals(dtoExpected, actualLotes.get(1));
    }

    @Test
    void Save__OK() throws QuesoNotFoundException, NotFoundConflictException {
        when(quesoService.getEntity("001")).thenReturn(mockQueso());
        when(repository.save(any(Lote.class))).thenReturn(mockLote());
        LoteDTO dtoActual = loteService.save(mockLoteDTO());
        LoteDTO dtoExpected = mockLoteDTO();
        assertEquals(dtoExpected, dtoActual);
    }

    @Test
    void Save__Queso_Not_Found() throws QuesoNotFoundException {
        when(quesoService.getEntity("001")).thenThrow(QuesoNotFoundException.class);
        NotFoundConflictException thrown = assertThrows(
                NotFoundConflictException.class, () -> loteService.save(mockLoteDTO())
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update__OK() throws QuesoNotFoundException, LoteNotFoundException, NotFoundConflictException {
        //todo fix
        LoteDTO mockLoteActualizado = mockLoteDTO();
        mockLoteActualizado.setNumeroTina(4);
        mockLoteActualizado.setCodigoQueso("004");
        mockLoteActualizado.setFechaElaboracion(LocalDate.of(2022, 2, 2));
        when(quesoService.getEntity("001")).thenReturn(mockQueso());
        var quesito = mockQueso();
        quesito.setCodigo("004");
        when(quesoService.getEntity("004")).thenReturn(quesito);
        when(repository.findById(any())).thenReturn(Optional.of(mockLote()));
        when(repository.save(any(Lote.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });
        var updated = loteService.update(mockLoteActualizado);
        assertEquals("020220220044", updated.getId());
    }

    @Test
    void Update__Lote_Not_Found() throws QuesoNotFoundException, LoteNotFoundException, NotFoundConflictException {
        when(repository.findById(any())).thenReturn(Optional.empty());
        LoteNotFoundException thrown = assertThrows(
                LoteNotFoundException.class, () -> loteService.update(mockLoteDTO())
        );
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Delete__OK() throws LoteNotFoundException {
        Lote l = mockLote();
        when(repository.findById(any(String.class))).thenReturn(Optional.of(mockLote()));
        LoteDTO dto = loteService.delete("1L");
        assertEquals(mockLoteDTO(), dto);
    }

    @Test
    void Delete__Lote_Not_Found() {
        when(repository.findById("1L")).thenReturn(Optional.empty());
        LoteNotFoundException thrown = assertThrows(
                LoteNotFoundException.class, () -> loteService.delete("1L")
        );
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
    }

    Lote mockLote() {
        Lote lote = new Lote();
        lote.setId("1L");
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
        queso.setCodigo("001");
        queso.setTipoQueso("tipoQueso");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }

    LoteDTO mockLoteDTO() {
        LoteDTO dto = new LoteDTO();
        dto.setId("1L");
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
        dto.setCodigoQueso("001");
        return dto;
    }
}
