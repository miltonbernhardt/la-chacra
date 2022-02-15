package com.brikton.lachacra.services;

import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.QuesoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @Test
    void Get_All__OK() {
        List<Queso> listaMock = new ArrayList<Queso>();
        listaMock.add(mockQueso());
        listaMock.add(mockQueso());
        when(repository.findAll()).thenReturn(listaMock);
        assertEquals(2,quesoService.getAll().size());
    }

    @Test
    void Get__OK() throws QuesoNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.of(mockQueso()));
        Queso quesoActual = quesoService.get(1L);
        Queso quesoExpected = mockQueso();
        assertEquals(quesoActual, quesoExpected);
    }

    @Test
    void Get__Queso_Not_Found() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.get(1L)
        );
        assertEquals("Queso no encontrado", thrown.getMessage());
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
//        p1.setQueso(queso); //todo
        p1.setPrecio(1D);
        p1.setTipoCliente(tipoCliente1);

        Precio p2 = new Precio();
        p2.setId(2L);
//        p2.setQueso(queso); //todo
        p2.setPrecio(2D);
        p2.setTipoCliente(tipoCliente2);

        queso.setPreciosActual(List.of(p1, p2));

        return queso;
    }
}
