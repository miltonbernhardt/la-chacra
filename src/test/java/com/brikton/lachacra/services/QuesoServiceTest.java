package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.dtos.QuesoUpdateDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.CodigoQuesoAlreadyExistsException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.LoteRepository;
import com.brikton.lachacra.repositories.QuesoRepository;
import com.brikton.lachacra.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {QuesoService.class})
public class QuesoServiceTest {

    @Autowired
    QuesoService quesoService;

    @MockBean
    QuesoRepository repository;

    @MockBean
    LoteRepository loteRepository;

    @MockBean
    PrecioService precioService;

    @MockBean
    DateUtil dateUtil;

    @Test
    void Get_All__OK() {
        Queso mockQueso1 = new Queso();
        mockQueso1.setId(1L);
        mockQueso1.setCodigo("001");
        mockQueso1.setTipoQueso("TIPO_QUESO");
        mockQueso1.setNomenclatura("TIP1");
        mockQueso1.setStock(1);

        Queso mockQueso2 = new Queso();
        mockQueso2.setId(2L);
        mockQueso2.setCodigo("002");
        mockQueso2.setTipoQueso("TIPO_QUESO_2");
        mockQueso2.setNomenclatura("TIP2");
        mockQueso2.setStock(2);

        QuesoDTO expectedDTO1 = new QuesoDTO();
        expectedDTO1.setId(1L);
        expectedDTO1.setCodigo("001");
        expectedDTO1.setTipoQueso("TIPO_QUESO");
        expectedDTO1.setNomenclatura("TIP1");
        expectedDTO1.setStock(1);

        QuesoDTO expectedDTO2 = new QuesoDTO();
        expectedDTO2.setId(2L);
        expectedDTO2.setCodigo("002");
        expectedDTO2.setTipoQueso("TIPO_QUESO_2");
        expectedDTO2.setNomenclatura("TIP2");
        expectedDTO2.setStock(2);

        List<Queso> listaMock = new ArrayList<>();
        listaMock.add(mockQueso1);
        listaMock.add(mockQueso2);
        when(repository.findAllQuesosNotFechaBaja()).thenReturn(listaMock);

        var quesos = quesoService.getAll();

        assertEquals(2, quesos.size());
        assertEquals(expectedDTO1, quesos.get(0));
        assertEquals(expectedDTO2, quesos.get(1));
    }

    @Test
    void Get__OK() {
        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso()));
        Queso quesoActual = quesoService.get(1L);
        Queso quesoExpected = mockQueso();
        assertEquals(quesoExpected, quesoActual);
    }

    @Test
    void Get__Deleted() {
        var dto = mockQueso();
        dto.setFechaBaja(LocalDate.of(2021, 10, 10));
        when(repository.findById(1L)).thenReturn(Optional.of(dto));
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.get(1L)
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get__Queso_Not_Found() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.get(1L)
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_By_Codigo__OK() {
        when(repository.findByCodigo("001")).thenReturn(Optional.of(mockQueso()));
        Queso quesoActual = quesoService.getByCodigo("001");
        Queso quesoExpected = mockQueso();
        assertEquals(quesoExpected, quesoActual);
    }

    @Test
    void Get_By_Codigo__Deleted() {
        var dto = mockQueso();
        dto.setFechaBaja(LocalDate.of(2021, 10, 10));
        when(repository.findByCodigo("001")).thenReturn(Optional.of(dto));
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.getByCodigo("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_By_Codigo__Queso_Not_Found() {
        when(repository.findByCodigo("001")).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.getByCodigo("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Save__OK() {
        QuesoDTO quesoToSave = new QuesoDTO();
        quesoToSave.setCodigo("001");
        quesoToSave.setTipoQueso("TIPO_QUESO");
        quesoToSave.setNomenclatura("tip");
        quesoToSave.setStock(1);

        QuesoDTO dtoQuesoExpected = new QuesoDTO();
        dtoQuesoExpected.setId(1L);
        dtoQuesoExpected.setCodigo("001");
        dtoQuesoExpected.setTipoQueso("TIPO_QUESO");
        dtoQuesoExpected.setNomenclatura("tip");
        dtoQuesoExpected.setStock(1);

        Queso mockQueso = new Queso();
        mockQueso.setId(1L);
        mockQueso.setCodigo("001");
        mockQueso.setTipoQueso("TIPO_QUESO");
        mockQueso.setNomenclatura("tip");
        mockQueso.setStock(1);

        when(repository.existsByCodigoNotFechaBaja("001")).thenReturn(false);
        when(repository.save(any(Queso.class))).thenReturn(mockQueso);
        QuesoDTO dtoQuesoActual = quesoService.save(quesoToSave);
        assertEquals(dtoQuesoExpected, dtoQuesoActual);
    }

    @Test
    void Save__Already_Exists_By_Codigo_Queso() {
        QuesoDTO dto = new QuesoDTO();
        dto.setCodigo("001");

        when(repository.existsByCodigoNotFechaBaja("001")).thenReturn(true);

        CodigoQuesoAlreadyExistsException thrown = assertThrows(
                CodigoQuesoAlreadyExistsException.class, () -> quesoService.save(dto)
        );
        assertEquals(ErrorMessages.MSG_CODIGO_QUESO_ALREADY_EXIST, thrown.getMessage());
    }

    @Test
    void Update__OK() {
        var quesoTpUpdate = new QuesoUpdateDTO();
        quesoTpUpdate.setId(1L);
        quesoTpUpdate.setCodigo("002");
        quesoTpUpdate.setTipoQueso("TIPO_QUESO2");
        quesoTpUpdate.setNomenclatura("tip2");
        quesoTpUpdate.setStock(2);

        Queso mockQueso = new Queso();
        mockQueso.setId(1L);
        mockQueso.setCodigo("001");
        mockQueso.setTipoQueso("TIPO_QUESO");
        mockQueso.setNomenclatura("tip");
        mockQueso.setStock(1);

        QuesoDTO expectedDTO = new QuesoDTO();
        expectedDTO.setId(1L);
        expectedDTO.setCodigo("001");
        expectedDTO.setTipoQueso("TIPO_QUESO2");
        expectedDTO.setNomenclatura("tip2");
        expectedDTO.setStock(1);

        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso));
        when(repository.existsByCodigoNotFechaBaja("001")).thenReturn(false);
        when(repository.save(any(Queso.class))).thenReturn(mockQueso);
        QuesoDTO actualDTO = quesoService.update(quesoTpUpdate);
        assertEquals(expectedDTO, actualDTO);
    }

    @Test
    void Update__Queso_Not_Exists() {
        var dto = new QuesoUpdateDTO();
        dto.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.update(dto)
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Delete_Queso_WITH_Dependencies__OK() {
        Queso mockQueso = new Queso();
        mockQueso.setId(1L);
        mockQueso.setCodigo("001");
        mockQueso.setTipoQueso("TIPO_QUESO");
        mockQueso.setNomenclatura("tip");
        mockQueso.setStock(1);

        Queso deletedQueso = new Queso();
        deletedQueso.setId(1L);
        deletedQueso.setCodigo("001");
        deletedQueso.setTipoQueso("TIPO_QUESO");
        deletedQueso.setNomenclatura("tip");
        deletedQueso.setStock(1);
        deletedQueso.setFechaBaja(LocalDate.of(2021, 10, 10));

        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso));
        when(repository.save(any(Queso.class))).thenReturn(deletedQueso);
        when(loteRepository.existsByQueso(any(Queso.class))).thenReturn(true);
        when(dateUtil.now()).thenReturn(LocalDate.of(2021, 10, 10));

        quesoService.delete(1L);

        verify(precioService).deletePreciosByQueso(1L);
        verify(repository).save(any(Queso.class));
        verify(repository, never()).delete(any(Queso.class));
    }

    @Test
    void Delete_Queso_WITHOUT_Dependencies__OK() {
        Queso mockQueso = new Queso();
        mockQueso.setId(1L);
        mockQueso.setCodigo("001");
        mockQueso.setTipoQueso("TIPO_QUESO");
        mockQueso.setNomenclatura("tip");
        mockQueso.setStock(1);

        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso));
        when(loteRepository.existsByQueso(any(Queso.class))).thenReturn(false);
        when(dateUtil.now()).thenReturn(LocalDate.of(2021, 10, 10));

        quesoService.delete(1L);

        verify(precioService).deletePreciosByQueso(1L);
        verify(repository).delete(any(Queso.class));
        verify(repository, never()).save(any(Queso.class));
    }

    @Test
    void Delete__Queso_Not_Found() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.delete(1L)
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Increase_Stock(){
        when(repository.save(any(Queso.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        var result = quesoService.increaseStock(mockQueso(),1);
        assertEquals(2,result.getStock());
    }

    @Test
    void Decrease_Stock(){
        when(repository.save(any(Queso.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        var result = quesoService.decreaseStock(mockQueso(),1);
        assertEquals(0,result.getStock());
    }

    Queso mockQueso() {
        Queso queso = new Queso();
        queso.setId(1L);
        queso.setTipoQueso("TIPO_QUESO");
        queso.setCodigo("001");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }
}
