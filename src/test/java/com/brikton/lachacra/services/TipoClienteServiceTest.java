package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.TipoClienteDTO;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundException;
import com.brikton.lachacra.repositories.TipoClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TipoClienteService.class})
public class TipoClienteServiceTest {

    @Autowired
    TipoClienteService service;

    @MockBean
    TipoClienteRepository repository;

    @Test
    void Get_All__OK() {
        var mockTipoCliente = new TipoCliente();
        mockTipoCliente.setId(1L);
        mockTipoCliente.setTipo("tipo1");

        var mockTipoCliente2 = new TipoCliente();
        mockTipoCliente2.setId(2L);
        mockTipoCliente2.setTipo("tipo2");

        var mockDTO1 = new TipoClienteDTO();
        mockDTO1.setId(1L);
        mockDTO1.setTipo("tipo1");

        var mockDTO2 = new TipoClienteDTO();
        mockDTO2.setId(2L);
        mockDTO2.setTipo("tipo2");


        when(repository.findAll()).thenReturn(List.of(mockTipoCliente, mockTipoCliente2));
        var tipoClientes = service.getAll();
        assertEquals(2, tipoClientes.size());
        assertEquals(mockDTO1, tipoClientes.get(0));
        assertEquals(mockDTO2, tipoClientes.get(1));
    }

    @Test
    void Get_Entity__OK() {
        var mockTipoCliente = new TipoCliente();
        mockTipoCliente.setId(1L);
        mockTipoCliente.setTipo("tipo1");

        when(repository.findById(1L)).thenReturn(Optional.of(mockTipoCliente));
        var tipoClienteActual = service.get(1L);

        assertEquals(mockTipoCliente, tipoClienteActual);
    }

    @Test
    void Get_Entity__Not_Exists() {
        var dto = new TipoCliente();
        dto.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.empty());
        TipoClienteNotFoundException thrown = assertThrows(
                TipoClienteNotFoundException.class, () -> service.get(1L)
        );
        assertEquals(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND, thrown.getMessage());
    }
}
