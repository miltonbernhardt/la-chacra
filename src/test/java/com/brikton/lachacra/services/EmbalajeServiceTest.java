package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.EmbalajeDTO;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.entities.Embalaje;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoEmbalaje;
import com.brikton.lachacra.exceptions.EmbalajeAlreadyExistsException;
import com.brikton.lachacra.repositories.EmbalajeRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

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
    public void Get_All__OK(){
        when(repository.findAll()).thenReturn(List.of(mockEmbalaje()));
        var result = service.getAll();
        assertEquals(1,result.size());
    }

    @Test
    public void Get__OK(){
        when(repository.findById(any())).thenReturn(Optional.of(mockEmbalaje()));
        var result = service.get(1L);
        assertEquals(1L,result.getId());
    }

    @Test
    @Disabled
    public void Save_New__OK(){
        when(repository.existsById(1L)).thenReturn(false);
        when(repository.existsByTipoEmbalajeAndListaQuesosContains(any(),any()))
                .thenReturn(false);
        when(repository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        var result = service.saveNew(mockDTO());
        assertEquals(1L,result.getId());
    }

    @Test
    public void Save_New__Embalaje_already_exists(){
        when(repository.existsById(1L)).thenReturn(false);
         when(repository.existsByTipoEmbalajeAndListaQuesosContains(any(),any()))
                .thenReturn(true);

        EmbalajeAlreadyExistsException result = assertThrows(
                EmbalajeAlreadyExistsException.class,() -> service.saveNew(mockDTO())
        );
        assertEquals(ErrorMessages.MSG_EMBALAJE_ALREADY_EXISTS,result.getMessage());
    }

    @Test
    public void Save_New__id_already_exists(){
        when(repository.existsById(1L)).thenReturn(true);

        EmbalajeAlreadyExistsException result = assertThrows(
                EmbalajeAlreadyExistsException.class,() -> service.saveNew(mockDTO())
        );
        assertEquals(ErrorMessages.MSG_EMBALAJE_ALREADY_EXISTS,result.getMessage());
    }

    @Test
    public void Update_Stock__OK(){
        var embalaje = mockEmbalaje();
        embalaje.setStock(50);

        when(repository.findById(any())).thenReturn(Optional.of(embalaje));
        when(repository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        var result = service.updateStock(1L,50);
        assertEquals(100,result.getStock());
    }

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
        assertEquals(TipoEmbalaje.CAJA,result.getTipoEmbalaje());
    }

    Embalaje mockEmbalaje(){
        var embalaje = new Embalaje();
        embalaje.setTipoEmbalaje(TipoEmbalaje.CAJA);
        embalaje.setStock(500);
        var quesoA =mockQuesoA();
        var quesoB = mockQuesoB();
        embalaje.setListaQuesos(List.of(quesoA,quesoB));
        embalaje.setId(1L);
        return embalaje;
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
