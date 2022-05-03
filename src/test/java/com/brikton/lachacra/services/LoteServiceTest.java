package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.LoteUpdateDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.LoteAlreadyExistsException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.DevolucionRepository;
import com.brikton.lachacra.repositories.ExpedicionRepository;
import com.brikton.lachacra.repositories.LoteRepository;
import com.brikton.lachacra.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = LoteService.class)
public class LoteServiceTest {

    @Autowired
    LoteService service;

    @MockBean
    LoteRepository repository;

    @MockBean
    DateUtil dateUtil;

    @MockBean
    DevolucionRepository devolucionRepository;

    @MockBean
    ExpedicionRepository expedicionRepository;

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
        dtoExpected.setCodigoQueso("001");

        when(repository.findAllLotesNotFechaBaja()).thenReturn(List.of(mockLote(), mockLote()));

        var actualLotes = service.getAll();

        assertEquals(2, actualLotes.size());
        assertEquals(dtoExpected, actualLotes.get(0));
        assertEquals(dtoExpected, actualLotes.get(1));
    }

    @Test
    void Get__OK() {
        Lote expectedLote = new Lote();
        expectedLote.setId("101020210011");
        expectedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        expectedLote.setNumeroTina(1);
        expectedLote.setLitrosLeche(1D);
        expectedLote.setCantHormas(1);
        expectedLote.setStockLote(10);
        expectedLote.setPeso(1D);
        expectedLote.setRendimiento(1D);
        expectedLote.setLoteCultivo("cultivo1, cultivo2");
        expectedLote.setLoteColorante("colorante1, colorante2");
        expectedLote.setLoteCalcio("calcio1, calcio2");
        expectedLote.setLoteCuajo("cuajo1, cuajo2");

        when(repository.findById("101020210011")).thenReturn(Optional.of(expectedLote));

        var actualLote = service.get("101020210011");

        assertEquals(expectedLote, actualLote);
    }

    @Test
    void Get__Not_Found() {
        when(repository.findById("101020210011")).thenReturn(Optional.empty());
        LoteNotFoundException thrown = assertThrows(
                LoteNotFoundException.class, () -> service.get("101020210011")
        );
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Decrease_Stock__OK() {
        Lote lote = new Lote();
        lote.setId("101020210011");
        lote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        lote.setNumeroTina(1);
        lote.setLitrosLeche(1D);
        lote.setCantHormas(1);
        lote.setStockLote(10);
        lote.setPeso(1D);
        lote.setRendimiento(1D);
        lote.setLoteCultivo("cultivo1, cultivo2");
        lote.setLoteColorante("colorante1, colorante2");
        lote.setLoteCalcio("calcio1, calcio2");
        lote.setLoteCuajo("cuajo1, cuajo2");

        Lote savedLote = new Lote();
        savedLote.setId("101020210011");
        savedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        savedLote.setNumeroTina(1);
        savedLote.setLitrosLeche(1D);
        savedLote.setCantHormas(1);
        savedLote.setStockLote(5);
        savedLote.setPeso(1D);
        savedLote.setRendimiento(1D);
        savedLote.setLoteCultivo("cultivo1, cultivo2");
        savedLote.setLoteColorante("colorante1, colorante2");
        savedLote.setLoteCalcio("calcio1, calcio2");
        savedLote.setLoteCuajo("cuajo1, cuajo2");

        when(repository.save(any(Lote.class))).thenReturn(savedLote);

        var actualLote = service.decreaseStock(lote, 5);

        ArgumentCaptor<Lote> captor = ArgumentCaptor.forClass(Lote.class);
        verify(repository).save(captor.capture());

        assertEquals(savedLote, actualLote);
        assertEquals(5, captor.getValue().getStockLote());
    }

    @Test
    void Increase_Stock__OK() {
        Lote lote = new Lote();
        lote.setId("101020210011");
        lote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        lote.setNumeroTina(1);
        lote.setLitrosLeche(1D);
        lote.setCantHormas(1);
        lote.setStockLote(10);
        lote.setPeso(1D);
        lote.setRendimiento(1D);
        lote.setLoteCultivo("cultivo1, cultivo2");
        lote.setLoteColorante("colorante1, colorante2");
        lote.setLoteCalcio("calcio1, calcio2");
        lote.setLoteCuajo("cuajo1, cuajo2");

        Lote savedLote = new Lote();
        savedLote.setId("101020210011");
        savedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        savedLote.setNumeroTina(1);
        savedLote.setLitrosLeche(1D);
        savedLote.setCantHormas(1);
        savedLote.setStockLote(15);
        savedLote.setPeso(1D);
        savedLote.setRendimiento(1D);
        savedLote.setLoteCultivo("cultivo1, cultivo2");
        savedLote.setLoteColorante("colorante1, colorante2");
        savedLote.setLoteCalcio("calcio1, calcio2");
        savedLote.setLoteCuajo("cuajo1, cuajo2");

        when(repository.findById("101020210011")).thenReturn(Optional.of(lote));
        when(repository.save(any(Lote.class))).thenReturn(savedLote);

        service.increaseStock(lote, 5);

        ArgumentCaptor<Lote> captor = ArgumentCaptor.forClass(Lote.class);
        verify(repository).save(captor.capture());

        assertEquals(15, captor.getValue().getStockLote());
    }

    @Test
    void Save__OK() throws QuesoNotFoundException, NotFoundConflictException, LoteAlreadyExistsException {
        LoteDTO dtoToSave = new LoteDTO();
        dtoToSave.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dtoToSave.setNumeroTina(1);
        dtoToSave.setCantHormas(1);
        dtoToSave.setLitrosLeche(20D);
        dtoToSave.setPeso(10D);
        dtoToSave.setLoteCultivo("cultivo1, cultivo2");
        dtoToSave.setLoteColorante("colorante1, colorante2");
        dtoToSave.setLoteCalcio("calcio1, calcio2");
        dtoToSave.setLoteCuajo("cuajo1, cuajo2");
        dtoToSave.setCodigoQueso("001");

        Lote savedLote = new Lote();
        savedLote.setId("101020210011");
        savedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        savedLote.setNumeroTina(1);
        savedLote.setCantHormas(1);
        savedLote.setStockLote(90);
        savedLote.setLitrosLeche(20D);
        savedLote.setPeso(10D);
        savedLote.setRendimiento(50D);
        savedLote.setLoteCultivo("cultivo1, cultivo2");
        savedLote.setLoteColorante("colorante1, colorante2");
        savedLote.setLoteCalcio("calcio1, calcio2");
        savedLote.setLoteCuajo("cuajo1, cuajo2");
        savedLote.setQueso(mockQueso());

        LoteDTO expectedDTO = new LoteDTO();
        expectedDTO.setId("101020210011");
        expectedDTO.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        expectedDTO.setNumeroTina(1);
        expectedDTO.setCantHormas(1);
        expectedDTO.setLitrosLeche(20D);
        expectedDTO.setPeso(10D);
        expectedDTO.setStockLote(90);
        expectedDTO.setRendimiento(50D);
        expectedDTO.setLoteCultivo("cultivo1, cultivo2");
        expectedDTO.setLoteColorante("colorante1, colorante2");
        expectedDTO.setLoteCalcio("calcio1, calcio2");
        expectedDTO.setLoteCuajo("cuajo1, cuajo2");
        expectedDTO.setCodigoQueso("001");

        when(quesoService.getByCodigo("001")).thenReturn(mockQueso());
        when(repository.existsByIdNotFechaBaja("101020210011")).thenReturn(false);
        when(repository.save(any(Lote.class))).thenReturn(savedLote);

        LoteDTO actualDTO = service.save(dtoToSave);

        ArgumentCaptor<Lote> captor = ArgumentCaptor.forClass(Lote.class);
        verify(repository).save(captor.capture());

        assertEquals(expectedDTO, actualDTO);
        assertEquals(50.0, captor.getValue().getRendimiento());
    }

    @Test
    void Save__Queso_Not_Found() throws QuesoNotFoundException {
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
        dto.setCodigoQueso("001");

        when(quesoService.getByCodigo("001")).thenThrow(QuesoNotFoundException.class);
        NotFoundConflictException thrown = assertThrows(
                NotFoundConflictException.class, () -> service.save(dto)
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Save__Lote_Already_Exists() throws QuesoNotFoundException {
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
        dto.setCodigoQueso("001");

        when(quesoService.getByCodigo("001")).thenReturn(mockQueso());
        when(repository.existsByIdNotFechaBaja("101020210011")).thenReturn(true);
        LoteAlreadyExistsException thrown = assertThrows(
                LoteAlreadyExistsException.class, () -> service.save(dto)
        );
        assertEquals(ErrorMessages.MSG_LOTE_ALREADY_EXIST, thrown.getMessage());
    }

    @Test
    void Generate_ID() throws QuesoNotFoundException, NotFoundConflictException, LoteAlreadyExistsException {
        LoteDTO dtoToSave = new LoteDTO();
        dtoToSave.setId("101020210011");
        dtoToSave.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dtoToSave.setNumeroTina(1);
        dtoToSave.setCantHormas(1);
        dtoToSave.setLitrosLeche(20D);
        dtoToSave.setPeso(10D);
        dtoToSave.setLoteCultivo("cultivo1, cultivo2");
        dtoToSave.setLoteColorante("colorante1, colorante2");
        dtoToSave.setLoteCalcio("calcio1, calcio2");
        dtoToSave.setLoteCuajo("cuajo1, cuajo2");
        dtoToSave.setCodigoQueso("001");

        Lote savedLote = new Lote();
        savedLote.setId("101020210011");
        savedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        savedLote.setNumeroTina(1);
        savedLote.setCantHormas(1);
        savedLote.setStockLote(90);
        savedLote.setLitrosLeche(20D);
        savedLote.setPeso(10D);
        savedLote.setRendimiento(20D);
        savedLote.setLoteCultivo("cultivo1, cultivo2");
        savedLote.setLoteColorante("colorante1, colorante2");
        savedLote.setLoteCalcio("calcio1, calcio2");
        savedLote.setLoteCuajo("cuajo1, cuajo2");
        savedLote.setQueso(mockQueso());

        LoteDTO expectedLote = new LoteDTO();
        expectedLote.setId("101020210011");
        expectedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        expectedLote.setNumeroTina(1);
        expectedLote.setCantHormas(1);
        expectedLote.setLitrosLeche(20D);
        expectedLote.setPeso(10D);
        expectedLote.setStockLote(90);
        expectedLote.setRendimiento(20D);
        expectedLote.setLoteCultivo("cultivo1, cultivo2");
        expectedLote.setLoteColorante("colorante1, colorante2");
        expectedLote.setLoteCalcio("calcio1, calcio2");
        expectedLote.setLoteCuajo("cuajo1, cuajo2");
        expectedLote.setCodigoQueso("001");

        when(quesoService.getByCodigo("001")).thenReturn(mockQueso());
        when(repository.save(any(Lote.class))).thenReturn(savedLote);

        LoteDTO dtoActual = service.save(dtoToSave);
        assertEquals(expectedLote, dtoActual);
    }

    @Test
    void Update__OK() throws QuesoNotFoundException, NotFoundConflictException, LoteNotFoundException {
        LoteUpdateDTO dtoToUpdate = new LoteUpdateDTO();
        dtoToUpdate.setId("101020210011");
        dtoToUpdate.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dtoToUpdate.setNumeroTina(1);
        dtoToUpdate.setCantHormas(1);
        dtoToUpdate.setLitrosLeche(20D);
        dtoToUpdate.setPeso(5D);
        dtoToUpdate.setLoteCultivo("cultivo1, cultivo2");
        dtoToUpdate.setLoteColorante("colorante1, colorante2");
        dtoToUpdate.setLoteCalcio("calcio1, calcio2");
        dtoToUpdate.setLoteCuajo("cuajo1, cuajo2");
        dtoToUpdate.setCodigoQueso("001");

        Lote updatedLote = new Lote();
        updatedLote.setId("101020210011");
        updatedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        updatedLote.setNumeroTina(1);
        updatedLote.setCantHormas(1);
        updatedLote.setStockLote(1);
        updatedLote.setLitrosLeche(20D);
        updatedLote.setPeso(5D);
        updatedLote.setRendimiento(25D);
        updatedLote.setLoteCultivo("cultivo1, cultivo2");
        updatedLote.setLoteColorante("colorante1, colorante2");
        updatedLote.setLoteCalcio("calcio1, calcio2");
        updatedLote.setLoteCuajo("cuajo1, cuajo2");
        updatedLote.setQueso(mockQueso());

        LoteDTO expectedLote = new LoteDTO();
        expectedLote.setId("101020210011");
        expectedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        expectedLote.setNumeroTina(1);
        expectedLote.setCantHormas(1);
        expectedLote.setLitrosLeche(20D);
        expectedLote.setPeso(5D);
        expectedLote.setStockLote(1);
        expectedLote.setRendimiento(25D);
        expectedLote.setLoteCultivo("cultivo1, cultivo2");
        expectedLote.setLoteColorante("colorante1, colorante2");
        expectedLote.setLoteCalcio("calcio1, calcio2");
        expectedLote.setLoteCuajo("cuajo1, cuajo2");
        expectedLote.setCodigoQueso("001");

        when(repository.existsByIdNotFechaBaja("101020210011")).thenReturn(true);
        when(repository.save(any(Lote.class))).thenReturn(updatedLote);
        when(repository.getById("101020210011")).thenReturn(updatedLote);
        when(quesoService.getByCodigo("001")).thenReturn(mockQueso());

        LoteDTO dtoActual = service.update(dtoToUpdate);
        ArgumentCaptor<Lote> captor = ArgumentCaptor.forClass(Lote.class);
        verify(repository).save(captor.capture());

        assertEquals(expectedLote, dtoActual);
        assertEquals(25.0, captor.getValue().getRendimiento());
    }

    @Test
    void Update__Lote_Not_Found() {
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
        dto.setCodigoQueso("001");

        when(repository.existsByIdNotFechaBaja("101020210011")).thenReturn(false);
        LoteNotFoundException thrown = assertThrows(
                LoteNotFoundException.class, () -> service.update(dto)
        );
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Delete_Without_Dependencies__OK() throws LoteNotFoundException {
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

        when(repository.existsByIdNotFechaBaja("101020210011")).thenReturn(true);
        when(devolucionRepository.existsByLote(lote)).thenReturn(false);
        when(expedicionRepository.existsByLote(lote)).thenReturn(false);

        service.delete("101020210011");

        verify(repository).deleteById("101020210011");
        verify(repository, never()).getById("101020210011");
        verify(repository, never()).save(any(Lote.class));
    }

    @Test
    void Delete_With_Devolucion_Dependency__OK() throws LoteNotFoundException {
        when(repository.existsByIdNotFechaBaja("101020210011")).thenReturn(true);
        when(repository.getById("101020210011")).thenReturn(mockLote());
        when(devolucionRepository.existsByLote(any(Lote.class))).thenReturn(true);
        when(expedicionRepository.existsByLote(any(Lote.class))).thenReturn(false);
        when(dateUtil.now()).thenReturn(LocalDate.of(2021, 10, 10));

        service.delete("101020210011");

        verify(repository, never()).deleteById("101020210011");
        verify(repository).getById("101020210011");
        verify(repository).save(any(Lote.class));
    }

    @Test
    void Delete_With_Expedicion_Dependency__OK() throws LoteNotFoundException {
        when(repository.existsByIdNotFechaBaja("101020210011")).thenReturn(true);
        when(repository.getById("101020210011")).thenReturn(mockLote());
        when(devolucionRepository.existsByLote(any(Lote.class))).thenReturn(false);
        when(expedicionRepository.existsByLote(any(Lote.class))).thenReturn(true);
        when(dateUtil.now()).thenReturn(LocalDate.of(2021, 10, 10));

        service.delete("101020210011");

        verify(repository, never()).deleteById("101020210011");
        verify(repository).getById("101020210011");
        verify(repository).save(any(Lote.class));
    }

    @Test
    void Delete__Lote_Not_Found() {
        when(repository.existsByIdNotFechaBaja("1")).thenReturn(false);
        LoteNotFoundException thrown = assertThrows(
                LoteNotFoundException.class, () -> service.delete("101020210011")
        );
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_By_Queso_And_With_Stock__OK(){
        when(quesoService.getByCodigo(any(String.class))).thenReturn(mockQueso());
        when(repository.findAllByQuesoAndStockLoteGreaterThan(any(Queso.class),any(Integer.class))).thenReturn(List.of(mockLote()));
        var result = service.getByQuesoAndWithStock("001");
        assertEquals(1,result.size());
    }

    @Test
    void Get_Between_Dates__OK(){
        when(repository.findAllByFechaBajaAndFechaElaboracionBetween(any(),any(LocalDate.class),any(LocalDate.class))).thenReturn(List.of(mockLote()));
        var result = service.getBetweenDates(LocalDate.now(),LocalDate.now());
        assertEquals(1,result.size());
    }

    @Test
    void Get_DTO_By_Id_OK(){
        when(repository.findById(anyString())).thenReturn(Optional.of(mockLote()));
        var result = service.getDTOById("101020210011");
        assertEquals("101020210011",result.getId());
        assertEquals(LocalDate.of(2021, 10, 10),result.getFechaElaboracion());
        assertEquals(1,result.getNumeroTina());
        assertEquals(1D,result.getLitrosLeche());
        assertEquals(1,result.getCantHormas());
        assertEquals(1,result.getStockLote());
        assertEquals(1D,result.getPeso());
        assertEquals(1D,result.getRendimiento());
        assertEquals("cultivo1, cultivo2",result.getLoteCultivo());
        assertEquals("colorante1, colorante2",result.getLoteColorante());
        assertEquals("calcio1, calcio2",result.getLoteCalcio());
        assertEquals("cuajo1, cuajo2",result.getLoteCuajo());
        assertEquals(mockQueso().getCodigo(),result.getCodigoQueso());
    }

    @Test
    void Get_Rendimiento_By_Dia__OK(){
        var lote1 = mockLote();
        var lote2 = mockLote();
        var lote3 = mockLote();
        lote1.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        lote2.setFechaElaboracion(LocalDate.of(2021, 10, 11));
        lote3.setFechaElaboracion(LocalDate.of(2021, 10, 11));

        when(repository.findAllByFechaBajaAndFechaElaboracionBetween(any(),any(),any()))
                .thenReturn(List.of(lote1,lote2,lote3));

        var result = service.getRendimientoByDia(LocalDate.of(2021, 10, 10),LocalDate.of(2021, 10, 10));
        assertEquals(2,result.size());
    }

    @Test
    void Get_Rendimiento_By_Queso__OK(){
        var lote1 = mockLote();
        var lote2 = mockLote();
        var lote3 = mockLote();

        var queso1 = mockQueso();
        var queso2 = mockQueso();
        queso1.setTipoQueso("tipo1");
        queso2.setTipoQueso("tipo2");

        lote1.setQueso(queso1);
        lote2.setQueso(queso1);
        lote3.setQueso(queso2);

        when(repository.findAllByFechaBajaAndFechaElaboracionBetween(any(),any(),any()))
                .thenReturn(List.of(lote1,lote2,lote3));

        var result = service.getRendimientoByQueso(LocalDate.of(2021, 10, 10),LocalDate.of(2021, 10, 10));
        assertEquals(2,result.size());
    }

    @Test
    void Get_Litros_Elaborados__OK(){
        var loteA = mockLote();
        var loteB = mockLote();
        var loteC = mockLote();

        loteA.setLitrosLeche(100d);
        loteB.setLitrosLeche(200d);
        loteC.setLitrosLeche(300d);

        var quesoA = mockQueso();
        var quesoB = mockQueso();
        quesoA.setCodigo("quesoA");
        quesoB.setCodigo("quesoB");

        loteA.setQueso(quesoA);
        loteB.setQueso(quesoA);
        loteC.setQueso(quesoB);

        when(repository
                .findAllByFechaBajaAndFechaElaboracionBetween(any(),any(LocalDate.class),any(LocalDate.class)))
                .thenReturn(List.of(loteA,loteB,loteC));

        var result = service.getLitrosElaborados(
                LocalDate.of(2021, 10, 10),
                LocalDate.of(2021, 10, 10));
        assertEquals(1,result.size());
        assertEquals(2,result.get(0).getLitrosElaborados().size());
        assertEquals(300d,result.get(0).getLitrosElaborados().get(0).getCantidad());
        assertEquals(300d,result.get(0).getLitrosElaborados().get(1).getCantidad());

    }

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
