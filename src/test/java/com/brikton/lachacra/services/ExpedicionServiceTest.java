package com.brikton.lachacra.services;

import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.repositories.ExpedicionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ExpedicionService.class})
public class ExpedicionServiceTest {

    @Autowired
    ExpedicionService expedicionService;

    @MockBean
    ExpedicionRepository repository;

    @Test
    void Exists_By_Lote__True() {
        Lote lote = new Lote();
        lote.setId("101020210011");
        when(repository.existsByLote(lote)).thenReturn(true);
        var exists = expedicionService.existsByLote(lote);
        assertTrue(exists);
    }

    @Test
    void Exists_By_Lote__False() {
        Lote lote = new Lote();
        lote.setId("101020210011");
        when(repository.existsByLote(lote)).thenReturn(false);
        var exists = expedicionService.existsByLote(lote);
        assertFalse(exists);
    }
}