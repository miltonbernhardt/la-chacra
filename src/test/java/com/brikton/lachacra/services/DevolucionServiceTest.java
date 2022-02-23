package com.brikton.lachacra.services;

import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.repositories.DevolucionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {DevolucionService.class})
public class DevolucionServiceTest {

    @Autowired
    DevolucionService devolucionService;

    @MockBean
    DevolucionRepository repository;

    @Test
    void Exists_By_Lote__True() {
        Lote lote = new Lote();
        lote.setId("101020210011");
        when(repository.existsByLote(lote)).thenReturn(true);
        var exists = devolucionService.existsByLote(lote);
        assertTrue(exists);
    }

    @Test
    void Exists_By_Lote__False() {
        Lote lote = new Lote();
        lote.setId("101020210011");
        when(repository.existsByLote(lote)).thenReturn(false);
        var exists = devolucionService.existsByLote(lote);
        assertFalse(exists);
    }
}
