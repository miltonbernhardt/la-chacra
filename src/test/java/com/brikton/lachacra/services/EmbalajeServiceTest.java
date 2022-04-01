package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.EmbalajeDTO;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.repositories.EmbalajeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {EmbalajeService.class})
public class EmbalajeServiceTest {

    @Autowired
    EmbalajeService service;
    @MockBean
    EmbalajeRepository repository;
    @MockBean
    QuesoService quesoService;

    @Test
    public void Embalaje_From_DTO__OK(){
        var dto = mockDTO();

        when(quesoService.getByCodigo("001")).thenReturn(mockQuesoA());
        when(quesoService.getByCodigo("002")).thenReturn(mockQuesoB());

        var result = service.embalajeFromDTO(dto);
        assertEquals(1L,result.getId());
        assertEquals(2,result.getListaQuesos().size());
        var resultDTO = new EmbalajeDTO(result);
        assertEquals(1L,resultDTO.getId());
        assertEquals(2,resultDTO.getListaQuesos().size());
    }

    EmbalajeDTO mockDTO(){
        var embalaje = new EmbalajeDTO();
        embalaje.setTipoEmbalaje("CAJA");
        embalaje.setStock(500);
        var quesoA = new QuesoDTO(mockQuesoA());
        var quesoB = new QuesoDTO(mockQuesoB());
        embalaje.setListaQuesos(List.of(quesoA,quesoB));
        embalaje.setId(1L);
        return embalaje;
    }

    Queso mockQuesoA() {
        Queso queso = new Queso();
        queso.setId(1L);
        queso.setCodigo("001");
        queso.setTipoQueso("A");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }

    Queso mockQuesoB() {
        Queso queso = new Queso();
        queso.setId(2L);
        queso.setCodigo("002");
        queso.setTipoQueso("B");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }

}
