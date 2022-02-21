package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.exceptions.PrecioNotFoundException;
import com.brikton.lachacra.repositories.PrecioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PrecioService.class})
public class PrecioServiceTest {

    @Autowired
    PrecioService precioService;

    @MockBean
    PrecioRepository repository;

    @Test
    void Get_All__OK() {
        when(repository.findAllByIdQueso(1L)).thenReturn(List.of(1L, 2L, 3L));
        var quesos = precioService.getAllByQueso(1L);
        assertEquals(3, quesos.size());
        assertEquals(1, quesos.get(0));
        assertEquals(2, quesos.get(1));
        assertEquals(3, quesos.get(2));
    }

    @Test
    void Delete__OK() throws PrecioNotFoundException {
        when(repository.existsById(1L)).thenReturn(true);
        var id = precioService.delete(1L);
        assertEquals(1L, id);
    }

    @Test
    void Delete__Precio_Not_Founds() {
        when(repository.existsById(1L)).thenReturn(false);
        PrecioNotFoundException thrown = assertThrows(
                PrecioNotFoundException.class, () -> precioService.delete(1L)
        );
        assertEquals(ErrorMessages.MSG_PRECIO_NOT_FOUND, thrown.getMessage());
    }
}
