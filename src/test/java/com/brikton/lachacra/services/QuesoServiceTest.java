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

import java.util.List;
import java.util.Optional;

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
    void Get_Queso__OK() throws QuesoNotFoundException {
        when(repository.findById("001")).thenReturn(Optional.of(mockQueso()));
        Queso quesoActual = quesoService.get("001");
        Queso quesoExpected = mockQueso();
        assertEquals(quesoActual, quesoExpected);
    }

    @Test
    void Get_Queso__Queso_Not_Found() {
        when(repository.findById("001")).thenReturn(Optional.empty());
        QuesoNotFoundException thrown = assertThrows(
                QuesoNotFoundException.class, () -> quesoService.get("001")
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
