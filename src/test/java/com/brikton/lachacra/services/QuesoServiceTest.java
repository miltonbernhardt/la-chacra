package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.QuesoRepository;
import com.brikton.lachacra.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        when(repository.findAll()).thenReturn(listaMock);
        assertEquals(2,quesoService.getAll().size());
    }

    @Test
    void Get_Entity__OK() throws QuesoNotFoundException {
        when(repository.findById("001")).thenReturn(Optional.of(mockQueso()));
        Queso quesoActual = quesoService.getEntity("001");
        Queso quesoExpected = mockQueso();
        assertEquals(quesoActual, quesoExpected);
    }

    @Test
    void Get_Queso_Deleted() {
        var dto = mockQueso();
        dto.setFechaBaja(LocalDate.of(2021, 10, 10));
        when(repository.findById("001")).thenReturn(Optional.of(dto));
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.getEntity("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_Entity__Queso_Not_Found() {
        when(repository.findById("001")).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.get("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_DTO__OK() throws QuesoNotFoundException {
        when(repository.findById("001")).thenReturn(Optional.of(mockQueso()));
        Queso quesoActual = quesoService.getEntity("001");
        Queso quesoExpected = mockQueso();
        assertEquals(quesoActual, quesoExpected);
    }

    @Test
    void Get_DTO__Queso_Deleted() {
        var dto = mockQueso();
        dto.setFechaBaja(LocalDate.of(2021, 10, 10));
        when(repository.findById("001")).thenReturn(Optional.of(dto));
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.get("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_DTO__Queso_Not_Found() {
        when(repository.findById("001")).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.get("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }


    @Test
    void Delete__OK() throws QuesoNotFoundException {
        when(repository.findById("001")).thenReturn(Optional.of(mockQueso()));
        when(dateUtil.now()).thenReturn(LocalDate.of(2021, 10, 10));
        String actualID = quesoService.delete("001");
        assertEquals("001", actualID);
    }

    @Test
    void Delete__Queso_Already_Deleted() {
        var dto = mockQueso();
        dto.setFechaBaja(LocalDate.of(2021, 10, 10));
        when(repository.findById("001")).thenReturn(Optional.of(dto));
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.delete("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Delete__Queso_Not_Found() {
        when(repository.findById("001")).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.delete("001")
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    Queso mockQueso() {
        Queso queso = new Queso();
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
