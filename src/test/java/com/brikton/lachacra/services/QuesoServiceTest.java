package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.dtos.QuesoUpdateDTO;
import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.CodigoQuesoAlreadyExistsException;
import com.brikton.lachacra.exceptions.NomQuesoAlreadyExistsException;
import com.brikton.lachacra.exceptions.PrecioNotFoundException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.LoteRepository;
import com.brikton.lachacra.repositories.QuesoRepository;
import com.brikton.lachacra.util.DateUtil;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.when;

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
        when(repository.findAllQuesos()).thenReturn(listaMock);

        var quesos = quesoService.getAll();

        assertEquals(2, quesos.size());
        assertEquals(expectedDTO1, quesos.get(0));
        assertEquals(expectedDTO2, quesos.get(1));
    }

    @Test
    void Get_Entity_By_ID__OK() throws QuesoNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso()));
        Queso quesoActual = quesoService.getEntity(1L);
        Queso quesoExpected = mockQueso();
        assertEquals(quesoExpected, quesoActual);
    }

    @Test
    void Get_Queso_By_ID__Deleted() {
        var dto = mockQueso();
        dto.setFechaBaja(LocalDate.of(2021, 10, 10));
        when(repository.findById(1L)).thenReturn(Optional.of(dto));
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.getEntity(1L)
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_Entity_By_ID__Queso_Not_Found() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.getEntity(1L)
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_Entity_By_Codigo__OK() throws QuesoNotFoundException {
        when(repository.findByCodigo("001")).thenReturn(Optional.of(mockQueso()));
        Queso quesoActual = quesoService.getEntity("001");
        Queso quesoExpected = mockQueso();
        assertEquals(quesoExpected, quesoActual);
    }

    @Test
    void Get_Entity_By_Codigo__Deleted() {
        var dto = mockQueso();
        dto.setFechaBaja(LocalDate.of(2021, 10, 10));
        when(repository.findByCodigo("001")).thenReturn(Optional.of(dto));
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.getEntity("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_Entity_By_Codigo__Queso_Not_Found() {
        when(repository.findByCodigo("001")).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.getEntity("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Save__OK() throws CodigoQuesoAlreadyExistsException, NomQuesoAlreadyExistsException {
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

        when(repository.existsQuesoByCodigo("001")).thenReturn(false);
        when(repository.existsQuesoByNomenclatura("tip")).thenReturn(false);
        when(repository.save(any(Queso.class))).thenReturn(mockQueso);
        QuesoDTO dtoQuesoActual = quesoService.save(quesoToSave);
        assertEquals(dtoQuesoExpected, dtoQuesoActual);
    }

    @Test
    void Save__Already_Exists_By_Codigo_Queso() {
        QuesoDTO dto = new QuesoDTO();
        dto.setCodigo("001");

        when(repository.existsQuesoByCodigo("001")).thenReturn(true);

        CodigoQuesoAlreadyExistsException thrown = assertThrows(
                CodigoQuesoAlreadyExistsException.class, () -> quesoService.save(dto)
        );
        assertEquals(ErrorMessages.MSG_CODIGO_QUESO_ALREADY_EXIST, thrown.getMessage());
    }

    @Test
    void Save__Already_Exists_By_Nomenclatura() {
        QuesoDTO dto = new QuesoDTO();
        dto.setCodigo("001");
        dto.setNomenclatura("Q");
        dto.setTipoQueso("TIPO");

        when(repository.existsQuesoByCodigo("001")).thenReturn(false);
        when(repository.existsQuesoByNomenclatura("Q")).thenReturn(true);

        NomQuesoAlreadyExistsException thrown = assertThrows(
                NomQuesoAlreadyExistsException.class, () -> quesoService.save(dto)
        );
        assertEquals(ErrorMessages.MSG_NOMENCLATURE_QUESO_ALREADY_EXIST, thrown.getMessage());
    }

    @Test
    void Update__OK() throws QuesoNotFoundException, NomQuesoAlreadyExistsException, CodigoQuesoAlreadyExistsException {
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
        expectedDTO.setCodigo("002");
        expectedDTO.setTipoQueso("TIPO_QUESO2");
        expectedDTO.setNomenclatura("tip2");
        expectedDTO.setStock(1);

        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso));
        when(repository.existsQuesoByCodigo("001")).thenReturn(false);
        when(repository.existsQuesoByNomenclatura("001")).thenReturn(false);
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
    void Update__Codigo_Queso_Already_Exists() {
        var dto = new QuesoUpdateDTO();
        dto.setId(1L);
        dto.setCodigo("002");
        dto.setTipoQueso("TIPO_QUESO2");
        dto.setNomenclatura("tip2");
        dto.setStock(1);
        dto.setId(1L);

        Queso mockQueso = new Queso();
        mockQueso.setId(1L);
        mockQueso.setCodigo("001");
        mockQueso.setTipoQueso("TIPO_QUESO");
        mockQueso.setNomenclatura("tip");
        mockQueso.setStock(1);

        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso));
        when(repository.existsQuesoByCodigo("002")).thenReturn(true);
        CodigoQuesoAlreadyExistsException thrown = assertThrows(
                CodigoQuesoAlreadyExistsException.class, () -> quesoService.update(dto)
        );
        assertEquals(ErrorMessages.MSG_CODIGO_QUESO_ALREADY_EXIST, thrown.getMessage());
    }

    @Test
    void Update__Nomenclatura_Queso_Already_Exists() {
        var dto = new QuesoUpdateDTO();
        dto.setId(1L);
        dto.setCodigo("002");
        dto.setTipoQueso("TIPO_QUESO2");
        dto.setNomenclatura("tip2");
        dto.setStock(1);
        dto.setId(1L);

        Queso mockQueso = new Queso();
        mockQueso.setId(1L);
        mockQueso.setCodigo("001");
        mockQueso.setTipoQueso("TIPO_QUESO");
        mockQueso.setNomenclatura("tip");
        mockQueso.setStock(1);

        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso));
        when(repository.existsQuesoByCodigo("002")).thenReturn(false);
        when(repository.existsQuesoByNomenclatura("TIP2")).thenReturn(true);
        NomQuesoAlreadyExistsException thrown = assertThrows(
                NomQuesoAlreadyExistsException.class, () -> quesoService.update(dto)
        );
        assertEquals(ErrorMessages.MSG_NOMENCLATURE_QUESO_ALREADY_EXIST, thrown.getMessage());
    }

    @Test
    void Delete_Queso_WITH_Dependencies__OK() throws QuesoNotFoundException {
        Queso mockQueso = new Queso();
        mockQueso.setId(1L);
        mockQueso.setCodigo("001");
        mockQueso.setTipoQueso("TIPO_QUESO");
        mockQueso.setNomenclatura("tip");
        mockQueso.setStock(1);

        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso));
        when(loteRepository.existsByQueso(any(Queso.class))).thenReturn(true);
        when(dateUtil.now()).thenReturn(LocalDate.of(2021, 10, 10));
        String actualID = quesoService.delete(1L);
        assertEquals("001", actualID);
    }

    @Test
    void Delete_Queso_WITHOUT_Dependencies__OK() throws QuesoNotFoundException {
        Queso mockQueso = new Queso();
        mockQueso.setId(1L);
        mockQueso.setCodigo("001");
        mockQueso.setTipoQueso("TIPO_QUESO");
        mockQueso.setNomenclatura("tip");
        mockQueso.setStock(1);

        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso));
        when(loteRepository.existsByQueso(any(Queso.class))).thenReturn(false);
        when(precioService.getAllByQueso(1L)).thenReturn(List.of(1L, 2L, 3L));
        when(dateUtil.now()).thenReturn(LocalDate.of(2021, 10, 10));
        String actualID = quesoService.delete(1L);
        assertEquals("", actualID);
    }

    @Test
    void Delete_Queso_WITHOUT_Dependencies_Precio_Not_Found_OK() throws PrecioNotFoundException, QuesoNotFoundException {
        Queso mockQueso = new Queso();
        mockQueso.setId(1L);
        mockQueso.setCodigo("001");
        mockQueso.setTipoQueso("TIPO_QUESO");
        mockQueso.setNomenclatura("tip");
        mockQueso.setStock(1);

        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso));
        when(loteRepository.existsByQueso(any(Queso.class))).thenReturn(false);
        when(precioService.getAllByQueso(1L)).thenReturn(List.of(3L));
        when(precioService.delete(3L)).thenThrow(new PrecioNotFoundException());
        when(dateUtil.now()).thenReturn(LocalDate.of(2021, 10, 10));
        String actualID = quesoService.delete(1L);
        assertEquals("", actualID);
    }

    @Test
    void Delete__Queso_Not_Found() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.delete(1L)
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
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
