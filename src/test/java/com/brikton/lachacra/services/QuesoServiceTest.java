package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.CodigoQuesoAlreadyExistsException;
import com.brikton.lachacra.exceptions.NomQuesoAlreadyExistsException;
import com.brikton.lachacra.repositories.QuesoRepository;
import com.brikton.lachacra.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

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
    DateUtil dateUtil;

    @Test
    void Get_All__OK() {
        List<Queso> listaMock = new ArrayList<>();
        listaMock.add(mockQueso());
        listaMock.add(mockQueso());
        when(repository.findALLQuesos()).thenReturn(listaMock);
        assertEquals(2,quesoService.getAll().size());
    }
//
//    @Test
//    void Get_Entity__OK() throws QuesoNotFoundException {
//        when(repository.findById("001")).thenReturn(Optional.of(mockQueso()));
//        Queso quesoActual = quesoService.getEntity("001");
//        Queso quesoExpected = mockQueso();
//        assertEquals(quesoExpected, quesoActual);
//    }
//
//    @Test
//    void Get_Queso_Deleted() {
//        var dto = mockQueso();
//        dto.setFechaBaja(LocalDate.of(2021, 10, 10));
//        when(repository.findById("001")).thenReturn(Optional.of(dto));
//        QuesoNotFoundException thrown = assertThrows(
//                QuesoNotFoundException.class, () -> quesoService.getEntity("001")
//        );
//        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
//    }
//
//    @Test
//    void Get_Entity__Queso_Not_Found() {
//        when(repository.findById("001")).thenReturn(Optional.empty());
//        QuesoNotFoundException thrown = assertThrows(
//                QuesoNotFoundException.class, () -> quesoService.get("001")
//        );
//        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
//    }
//
//    @Test
//    void Get_DTO__OK() throws QuesoNotFoundException {
//        when(repository.findById("001")).thenReturn(Optional.of(mockQueso()));
//        Queso quesoActual = quesoService.getEntity("001");
//        Queso quesoExpected = mockQueso();
//        assertEquals(quesoExpected, quesoActual);
//    }
//
//    @Test
//    void Get_DTO__Queso_Deleted() {
//        var dto = mockQueso();
//        dto.setFechaBaja(LocalDate.of(2021, 10, 10));
//        when(repository.findById("001")).thenReturn(Optional.of(dto));
//        QuesoNotFoundException thrown = assertThrows(
//                QuesoNotFoundException.class, () -> quesoService.get("001")
//        );
//        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
//    }
//
//    @Test
//    void Get_DTO__Queso_Not_Found() {
//        when(repository.findById("001")).thenReturn(Optional.empty());
//        QuesoNotFoundException thrown = assertThrows(
//                QuesoNotFoundException.class, () -> quesoService.get("001")
//        );
//        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
//    }

    @Test
    void Save__OK() throws CodigoQuesoAlreadyExistsException, NomQuesoAlreadyExistsException {
        QuesoDTO quesoToSave = new QuesoDTO();
        quesoToSave.setCodigo("001");
        quesoToSave.setTipoQueso("tipoQueso");
        quesoToSave.setNomenclatura("tip");
        quesoToSave.setStock(1);

        QuesoDTO dtoQuesoExpected = new QuesoDTO();
        dtoQuesoExpected.setId(1L);
        dtoQuesoExpected.setCodigo("001");
        dtoQuesoExpected.setTipoQueso("tipoQueso");
        dtoQuesoExpected.setNomenclatura("tip");
        dtoQuesoExpected.setStock(1);

        Queso mockQueso = new Queso();
        mockQueso.setId(1L);
        mockQueso.setCodigo("001");
        mockQueso.setTipoQueso("tipoQueso");
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
        dto.setTipoQueso("tipo");

        when(repository.existsQuesoByCodigo("001")).thenReturn(false);
        when(repository.existsQuesoByNomenclatura("Q")).thenReturn(true);

        NomQuesoAlreadyExistsException thrown = assertThrows(
                NomQuesoAlreadyExistsException.class, () -> quesoService.save(dto)
        );
        assertEquals(ErrorMessages.MSG_NOMENCLATURE_QUESO_ALREADY_EXIST, thrown.getMessage());
    }

//    @Test
//    void Update__OK() throws QuesoNotFoundException {
//        QuesoDTO quesoExpected = new QuesoDTO();
//        quesoExpected.setCodigo("001");
//        quesoExpected.setTipoQueso("tipoQueso");
//        quesoExpected.setNomenclatura("tip");
//        quesoExpected.setStock(1);
//
//        Queso mockQueso = new Queso();
//        mockQueso.setCodigo("001");
//        mockQueso.setTipoQueso("tipoQueso");
//        mockQueso.setNomenclatura("tip");
//        mockQueso.setStock(1);
//
//        when(repository.existsById("001")).thenReturn(true);
//        when(repository.getById("001")).thenReturn(mockQueso);
//        when(repository.save(mockQueso)).thenReturn(mockQueso);
//        QuesoDTO quesoActual = quesoService.update(quesoExpected);
//        assertEquals(quesoExpected, quesoActual);
//    }

//    @Test
//    void Update__Queso_Not_Exists() {
//        QuesoDTO dto = new QuesoDTO();
//        dto.setCodigo("001");
//        when(repository.existsById("001")).thenReturn(false);
//        QuesoNotFoundException thrown = assertThrows(
//                QuesoNotFoundException.class, () -> quesoService.update(dto)
//        );
//        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
//    }
//
//    @Test
//    void Delete__OK() throws QuesoNotFoundException {
//        when(repository.findById("001")).thenReturn(Optional.of(mockQueso()));
//        when(dateUtil.now()).thenReturn(LocalDate.of(2021, 10, 10));
//        String actualID = quesoService.delete("001");
//        assertEquals("001", actualID);
//    }
//
//    @Test
//    void Delete__Queso_Already_Deleted() {
//        var dto = mockQueso();
//        dto.setFechaBaja(LocalDate.of(2021, 10, 10));
//        when(repository.findById("001")).thenReturn(Optional.of(dto));
//        QuesoNotFoundException thrown = assertThrows(
//                QuesoNotFoundException.class, () -> quesoService.delete("001")
//        );
//        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
//    }
//
//    @Test
//    void Delete__Queso_Not_Found() {
//        when(repository.findById("001")).thenReturn(Optional.empty());
//        QuesoNotFoundException thrown = assertThrows(
//                QuesoNotFoundException.class, () -> quesoService.delete("001")
//        );
//        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
//    }

    Queso mockQueso() {
        Queso queso = new Queso();
        queso.setId(1L);
        queso.setTipoQueso("tipoQueso");
        queso.setCodigo("001");
        queso.setNomenclatura("tip");
        queso.setStock(1);

        var tipoCliente1 = new TipoCliente(1L, "tipo1");
        var tipoCliente2 = new TipoCliente(2L, "tipo2");

        Precio p1 = new Precio();
        p1.setId(1L);
        p1.setQueso(queso);
        p1.setPrecio(1D);
        p1.setTipoCliente(tipoCliente1);

        Precio p2 = new Precio();
        p2.setId(2L);
        p2.setQueso(queso);
        p2.setPrecio(2D);
        p2.setTipoCliente(tipoCliente2);

        queso.setPreciosActual(List.of(p1, p2));

        return queso;
    }
}
