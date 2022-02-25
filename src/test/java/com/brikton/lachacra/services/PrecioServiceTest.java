package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.PrecioDTO;
import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.PrecioAlreadyExistsException;
import com.brikton.lachacra.exceptions.PrecioNotFoundException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundException;
import com.brikton.lachacra.repositories.PrecioRepository;
import com.brikton.lachacra.repositories.QuesoRepository;
import com.brikton.lachacra.repositories.TipoClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PrecioService.class})
public class PrecioServiceTest {

    @Autowired
    PrecioService precioService;

    @MockBean
    PrecioRepository repository;

    @MockBean
    QuesoRepository mockQuesoRepository;
    @MockBean
    TipoClienteService mockTipoClienteService;

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

    @Test
    void Get_All() throws QuesoNotFoundException, TipoClienteNotFoundException {
        List<Precio> precios = new ArrayList<>();
        precios.add(precioMock());
        when(repository.findAll()).thenReturn(precios);
        when(mockQuesoRepository.findById(1L)).thenReturn(Optional.of(quesoMock()));
        when(mockTipoClienteService.getEntity(1L)).thenReturn(tipoClienteMock());
        var preciosDTO = precioService.getAll();
        assertEquals(1,preciosDTO.size());
        assertEquals(1L,preciosDTO.get(0).getId());
        assertEquals(1L,preciosDTO.get(0).getIdTipoCliente());
        assertEquals(1L,preciosDTO.get(0).getIdQueso());
        assertEquals(1000D,preciosDTO.get(0).getPrecio());
    }

    @Test
    void Save__OK() throws QuesoNotFoundException, TipoClienteNotFoundException, PrecioAlreadyExistsException {
        when(repository.existsByQuesoAndTipoCliente(any(Queso.class),any(TipoCliente.class))).thenReturn(false);
        when(mockQuesoRepository.findById(any(Long.class))).thenReturn(Optional.of(quesoMock()));
        when(mockTipoClienteService.getEntity(any(Long.class))).thenReturn(tipoClienteMock());
        // esto retorna el mismo elemento con el que se llamo a la funcion
        // es para verificar que se la llame correctamente
        when(repository.save(any(Precio.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });
        var request = precioDTOMock();
        var response = precioService.save(request);
        assertEquals(request.getIdQueso(),response.getIdQueso());
        assertEquals(request.getPrecio(),response.getPrecio());
        assertEquals(request.getIdTipoCliente(),response.getIdTipoCliente());
    }

    @Test
    void Update__OK() throws QuesoNotFoundException, TipoClienteNotFoundException, PrecioAlreadyExistsException {
        when(repository.existsByQuesoAndTipoCliente(any(Queso.class),any(TipoCliente.class))).thenReturn(false);
        when(mockQuesoRepository.findById(any(Long.class))).thenReturn(Optional.of(quesoMock()));
        when(mockTipoClienteService.getEntity(any(Long.class))).thenReturn(tipoClienteMock());
        // esto retorna el mismo elemento con el que se llamo a la funcion
        // es para verificar que se la llame correctamente
        when(repository.save(any(Precio.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });
        when(repository.getById(any(Long.class))).thenReturn(precioMock());

        var request = precioDTOMock();
        request.setPrecio(21D);
        var response = precioService.save(request);
        assertEquals(request.getIdQueso(),response.getIdQueso());
        assertEquals(request.getPrecio(),response.getPrecio());
        assertEquals(request.getIdTipoCliente(),response.getIdTipoCliente());
    }

    private Precio precioMock(){
        Precio precio = new Precio();
        precio.setId(1L);
        precio.setPrecio(1000D);
        precio.setQueso(quesoMock());
        precio.setTipoCliente(tipoClienteMock());
        return precio;
    }

    private PrecioDTO precioDTOMock(){
        PrecioDTO dto = new PrecioDTO();
        dto.setId(1L);
        dto.setPrecio(1000D);
        dto.setIdQueso(1L);
        dto.setIdTipoCliente(1L);
        return dto;
    }

    private TipoCliente tipoClienteMock(){
        TipoCliente tipoCliente = new TipoCliente();
        tipoCliente.setTipo("tipo");
        tipoCliente.setId(1L);
        return tipoCliente;
    }

    private Queso quesoMock(){
        Queso queso = new Queso();
        queso.setId(1L);
        queso.setTipoQueso("tipoQueso");
        queso.setNomenclatura("nomenclatura");
        queso.setCodigo("codigo");
        return queso;
    }
}
