package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.repositories.LoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void Get_All__OK() {
        LoteDTO dtoExpected = new LoteDTO();
        dtoExpected.setId("101020210011");
        dtoExpected.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dtoExpected.setNumeroTina(1);
        dtoExpected.setLitrosLeche(1D);
        dtoExpected.setCantHormas(1);
        dtoExpected.setStockLote(1);
        dtoExpected.setPeso(1D);
        dtoExpected.setRendimiento(1D);
        dtoExpected.setLoteCultivo("cultivo1, cultivo2");
        dtoExpected.setLoteColorante("colorante1, colorante2");
        dtoExpected.setLoteCalcio("calcio1, calcio2");
        dtoExpected.setLoteCuajo("cuajo1, cuajo2");
        dtoExpected.setIdQueso(1L);

        when(repository.findAll()).thenReturn(List.of(mockLote(), mockLote()));
        var actualLotes = loteService.getAll();
        assertEquals(2, actualLotes.size());
        assertEquals(dtoExpected, actualLotes.get(0));
        assertEquals(dtoExpected, actualLotes.get(1));
    }

//    @Test
//    void Save__OK() throws QuesoNotFoundException, NotFoundConflictException {
//        LoteDTO dtoToSave = new LoteDTO();
//        dtoToSave.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        dtoToSave.setNumeroTina(1);
//        dtoToSave.setCantHormas(1);
//        dtoToSave.setLitrosLeche(20D);
//        dtoToSave.setPeso(10D);
//        dtoToSave.setLoteCultivo("cultivo1, cultivo2");
//        dtoToSave.setLoteColorante("colorante1, colorante2");
//        dtoToSave.setLoteCalcio("calcio1, calcio2");
//        dtoToSave.setLoteCuajo("cuajo1, cuajo2");
//        dtoToSave.setCodigoQueso("001");
//
//        Lote savedLote = new Lote();
//        savedLote.setId("101020210011");
//        savedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        savedLote.setNumeroTina(1);
//        savedLote.setCantHormas(1);
//        savedLote.setStockLote(90);
//        savedLote.setLitrosLeche(20D);
//        savedLote.setPeso(10D);
//        savedLote.setRendimiento(20D);
//        savedLote.setLoteCultivo("cultivo1, cultivo2");
//        savedLote.setLoteColorante("colorante1, colorante2");
//        savedLote.setLoteCalcio("calcio1, calcio2");
//        savedLote.setLoteCuajo("cuajo1, cuajo2");
//        savedLote.setQueso(mockQueso());
//
//        LoteDTO expectedLote = new LoteDTO();
//        expectedLote.setId("101020210011");
//        expectedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        expectedLote.setNumeroTina(1);
//        expectedLote.setCantHormas(1);
//        expectedLote.setLitrosLeche(20D);
//        expectedLote.setPeso(10D);
//        expectedLote.setStockLote(90);
//        expectedLote.setRendimiento(20D);
//        expectedLote.setLoteCultivo("cultivo1, cultivo2");
//        expectedLote.setLoteColorante("colorante1, colorante2");
//        expectedLote.setLoteCalcio("calcio1, calcio2");
//        expectedLote.setLoteCuajo("cuajo1, cuajo2");
//        expectedLote.setCodigoQueso("001");
//
//        when(quesoService.getEntity("001")).thenReturn(mockQueso());
//        when(repository.existsById("001")).thenReturn(false);
//        when(repository.save(any(Lote.class))).thenReturn(savedLote);
//
//        LoteDTO dtoActual = loteService.save(dtoToSave);
//        assertEquals(expectedLote, dtoActual);
//    }
//
//    @Test
//    void Save__Queso_Not_Found() throws QuesoNotFoundException {
//        LoteDTO dto = new LoteDTO();
//        dto.setId("101020210011");
//        dto.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        dto.setNumeroTina(1);
//        dto.setLitrosLeche(1D);
//        dto.setCantHormas(1);
//        dto.setStockLote(1);
//        dto.setPeso(1D);
//        dto.setRendimiento(1D);
//        dto.setLoteCultivo("cultivo1, cultivo2");
//        dto.setLoteColorante("colorante1, colorante2");
//        dto.setLoteCalcio("calcio1, calcio2");
//        dto.setLoteCuajo("cuajo1, cuajo2");
//        dto.setCodigoQueso("001");
//
//        when(quesoService.getEntity("001")).thenThrow(QuesoNotFoundException.class);
//        NotFoundConflictException thrown = assertThrows(
//                NotFoundConflictException.class, () -> loteService.save(dto)
//        );
//        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
//    }
//
//    @Test
//    void Generate_ID() throws QuesoNotFoundException, NotFoundConflictException {
//        LoteDTO dtoToSave = new LoteDTO();
//        dtoToSave.setId("101020210011");
//        dtoToSave.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        dtoToSave.setNumeroTina(1);
//        dtoToSave.setCantHormas(1);
//        dtoToSave.setLitrosLeche(20D);
//        dtoToSave.setPeso(10D);
//        dtoToSave.setLoteCultivo("cultivo1, cultivo2");
//        dtoToSave.setLoteColorante("colorante1, colorante2");
//        dtoToSave.setLoteCalcio("calcio1, calcio2");
//        dtoToSave.setLoteCuajo("cuajo1, cuajo2");
//        dtoToSave.setCodigoQueso("001");
//
//        Lote savedLote = new Lote();
//        savedLote.setId("101020210011");
//        savedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        savedLote.setNumeroTina(1);
//        savedLote.setCantHormas(1);
//        savedLote.setStockLote(90);
//        savedLote.setLitrosLeche(20D);
//        savedLote.setPeso(10D);
//        savedLote.setRendimiento(20D);
//        savedLote.setLoteCultivo("cultivo1, cultivo2");
//        savedLote.setLoteColorante("colorante1, colorante2");
//        savedLote.setLoteCalcio("calcio1, calcio2");
//        savedLote.setLoteCuajo("cuajo1, cuajo2");
//        savedLote.setQueso(mockQueso());
//
//        LoteDTO expectedLote = new LoteDTO();
//        expectedLote.setId("101020210011");
//        expectedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        expectedLote.setNumeroTina(1);
//        expectedLote.setCantHormas(1);
//        expectedLote.setLitrosLeche(20D);
//        expectedLote.setPeso(10D);
//        expectedLote.setStockLote(90);
//        expectedLote.setRendimiento(20D);
//        expectedLote.setLoteCultivo("cultivo1, cultivo2");
//        expectedLote.setLoteColorante("colorante1, colorante2");
//        expectedLote.setLoteCalcio("calcio1, calcio2");
//        expectedLote.setLoteCuajo("cuajo1, cuajo2");
//        expectedLote.setCodigoQueso("001");
//
//        when(quesoService.getEntity("001")).thenReturn(mockQueso());
//        when(repository.save(any(Lote.class))).thenReturn(savedLote);
//
//        LoteDTO dtoActual = loteService.save(dtoToSave);
//        assertEquals(expectedLote, dtoActual);
//    }
//
//    @Test
//    void Update_With_ID_In_DTO__OK() throws QuesoNotFoundException, NotFoundConflictException, LoteNotFoundException {
//        LoteUpdateDTO dtoToUpdate = new LoteUpdateDTO();
//        dtoToUpdate.setId("101020210011");
//        dtoToUpdate.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        dtoToUpdate.setNumeroTina(1);
//        dtoToUpdate.setCantHormas(1);
//        dtoToUpdate.setLitrosLeche(20D);
//        dtoToUpdate.setPeso(10D);
//        dtoToUpdate.setLoteCultivo("cultivo1, cultivo2");
//        dtoToUpdate.setLoteColorante("colorante1, colorante2");
//        dtoToUpdate.setLoteCalcio("calcio1, calcio2");
//        dtoToUpdate.setLoteCuajo("cuajo1, cuajo2");
//        dtoToUpdate.setCodigoQueso("001");
//
//        Lote updatedLote = new Lote();
//        updatedLote.setId("101020210011");
//        updatedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        updatedLote.setNumeroTina(1);
//        updatedLote.setCantHormas(1);
//        updatedLote.setStockLote(1);
//        updatedLote.setLitrosLeche(20D);
//        updatedLote.setPeso(10D);
//        updatedLote.setRendimiento(20D);
//        updatedLote.setLoteCultivo("cultivo1, cultivo2");
//        updatedLote.setLoteColorante("colorante1, colorante2");
//        updatedLote.setLoteCalcio("calcio1, calcio2");
//        updatedLote.setLoteCuajo("cuajo1, cuajo2");
//        updatedLote.setQueso(mockQueso());
//
//        LoteDTO expectedLote = new LoteDTO();
//        expectedLote.setId("101020210011");
//        expectedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        expectedLote.setNumeroTina(1);
//        expectedLote.setCantHormas(1);
//        expectedLote.setLitrosLeche(20D);
//        expectedLote.setPeso(10D);
//        expectedLote.setStockLote(1);
//        expectedLote.setRendimiento(20D);
//        expectedLote.setLoteCultivo("cultivo1, cultivo2");
//        expectedLote.setLoteColorante("colorante1, colorante2");
//        expectedLote.setLoteCalcio("calcio1, calcio2");
//        expectedLote.setLoteCuajo("cuajo1, cuajo2");
//        expectedLote.setCodigoQueso("001");
//
//        when(repository.existsById("101020210011")).thenReturn(true);
//        when(repository.save(any(Lote.class))).thenReturn(updatedLote);
//        when(quesoService.getEntity("001")).thenReturn(mockQueso());
//
//        LoteDTO dtoActual = loteService.update(dtoToUpdate);
//        assertEquals(expectedLote, dtoActual);
//    }
//
//    @Test
//    void Update_Without_ID_In_DTO__OK() throws QuesoNotFoundException, NotFoundConflictException, LoteNotFoundException {
//        LoteUpdateDTO dtoToUpdate = new LoteUpdateDTO();
//        dtoToUpdate.setId("101020210011");
//        dtoToUpdate.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        dtoToUpdate.setNumeroTina(1);
//        dtoToUpdate.setCantHormas(1);
//        dtoToUpdate.setLitrosLeche(20D);
//        dtoToUpdate.setPeso(10D);
//        dtoToUpdate.setLoteCultivo("cultivo1, cultivo2");
//        dtoToUpdate.setLoteColorante("colorante1, colorante2");
//        dtoToUpdate.setLoteCalcio("calcio1, calcio2");
//        dtoToUpdate.setLoteCuajo("cuajo1, cuajo2");
//        dtoToUpdate.setCodigoQueso("001");
//
//        Lote updatedLote = new Lote();
//        updatedLote.setId("101020210011");
//        updatedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        updatedLote.setNumeroTina(1);
//        updatedLote.setCantHormas(1);
//        updatedLote.setStockLote(1);
//        updatedLote.setLitrosLeche(20D);
//        updatedLote.setPeso(10D);
//        updatedLote.setRendimiento(20D);
//        updatedLote.setLoteCultivo("cultivo1, cultivo2");
//        updatedLote.setLoteColorante("colorante1, colorante2");
//        updatedLote.setLoteCalcio("calcio1, calcio2");
//        updatedLote.setLoteCuajo("cuajo1, cuajo2");
//        updatedLote.setQueso(mockQueso());
//
//        LoteDTO expectedLote = new LoteDTO();
//        expectedLote.setId("101020210011");
//        expectedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        expectedLote.setNumeroTina(1);
//        expectedLote.setCantHormas(1);
//        expectedLote.setStockLote(1);
//        expectedLote.setLitrosLeche(20D);
//        expectedLote.setPeso(10D);
//        expectedLote.setRendimiento(20D);
//        expectedLote.setLoteCultivo("cultivo1, cultivo2");
//        expectedLote.setLoteColorante("colorante1, colorante2");
//        expectedLote.setLoteCalcio("calcio1, calcio2");
//        expectedLote.setLoteCuajo("cuajo1, cuajo2");
//        expectedLote.setCodigoQueso("001");
//
//        when(repository.existsById("101020210011")).thenReturn(true);
//        when(repository.save(any(Lote.class))).thenReturn(updatedLote);
//        when(quesoService.getEntity("001")).thenReturn(mockQueso());
//
//        LoteDTO dtoActual = loteService.update(dtoToUpdate);
//        assertEquals(expectedLote, dtoActual);
//    }
//
//    @Test
//    void Update__Lote_Not_Found() {
//        LoteUpdateDTO dto = new LoteUpdateDTO();
//        dto.setId("101020210011");
//        dto.setFechaElaboracion(LocalDate.of(2021, 10, 10));
//        dto.setNumeroTina(1);
//        dto.setLitrosLeche(1D);
//        dto.setCantHormas(1);
//        dto.setStockLote(1);
//        dto.setPeso(1D);
//        dto.setRendimiento(1D);
//        dto.setLoteCultivo("cultivo1, cultivo2");
//        dto.setLoteColorante("colorante1, colorante2");
//        dto.setLoteCalcio("calcio1, calcio2");
//        dto.setLoteCuajo("cuajo1, cuajo2");
//        dto.setCodigoQueso("001");
//
//        when(repository.existsById("1")).thenReturn(false);
//        LoteNotFoundException thrown = assertThrows(
//                LoteNotFoundException.class, () -> loteService.update(dto)
//        );
//        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
//    }
//
//    @Test
//    void Delete__OK() throws LoteNotFoundException {
//        when(repository.existsById("1")).thenReturn(true);
//        String id = loteService.delete("1");
//        assertEquals("1", id);
//    }
//
//    @Test
//    void Delete__Lote_Not_Found() {
//        when(repository.existsById("1")).thenReturn(false);
//        LoteNotFoundException thrown = assertThrows(
//                LoteNotFoundException.class, () -> loteService.delete("1")
//        );
//        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
//    }

    Lote mockLote() {
        Lote lote = new Lote();
        lote.setId("101020210011");
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
        queso.setId(1L);
        queso.setCodigo("001");
        queso.setTipoQueso("tipoQueso");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }
}
