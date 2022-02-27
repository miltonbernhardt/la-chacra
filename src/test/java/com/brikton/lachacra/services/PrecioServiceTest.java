package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.PrecioDTO;
import com.brikton.lachacra.dtos.PrecioUpdateDTO;
import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.PrecioNotFoundException;
import com.brikton.lachacra.exceptions.QuesoNotFoundConflictException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundConflictException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundException;
import com.brikton.lachacra.repositories.PrecioRepository;
import com.brikton.lachacra.repositories.QuesoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PrecioService.class})
public class PrecioServiceTest {

    @Autowired
    PrecioService service;

    @MockBean
    PrecioRepository repository;

    @MockBean
    QuesoRepository quesoRepository;

    @MockBean
    TipoClienteService tipoClienteService;

    @Test
    void Get_All() {
        TipoCliente tipoClienteMock = new TipoCliente();
        tipoClienteMock.setTipo("tipo");
        tipoClienteMock.setId(1L);

        Queso quesoMock = new Queso();
        quesoMock.setId(1L);
        quesoMock.setTipoQueso("tipoQueso");
        quesoMock.setNomenclatura("nomenclatura");
        quesoMock.setCodigo("codigo");

        Precio precioMock = new Precio();
        precioMock.setId(1L);
        precioMock.setValor(1000D);
        precioMock.setQueso(quesoMock);
        precioMock.setTipoCliente(tipoClienteMock);

        List<Precio> precios = List.of(precioMock);

        when(repository.findAll()).thenReturn(precios);
        var preciosDTO = service.getAll();
        assertEquals(1, preciosDTO.size());
        assertEquals(1L, preciosDTO.get(0).getId());
        assertEquals(1L, preciosDTO.get(0).getIdTipoCliente());
        assertEquals(1L, preciosDTO.get(0).getIdQueso());
        assertEquals(1000D, preciosDTO.get(0).getValor());
    }

    @Test
    void Delete_Precios_By_Queso__OK() {
        when(repository.findAllByIdQueso(1L)).thenReturn(List.of(1L, 2L, 3L));

        service.deletePreciosByQueso(1L);

        verify(repository).deleteAllById(List.of(1L, 2L, 3L));
    }

    @Test
    void Save__OK() {
        PrecioDTO precioDTOToSave = new PrecioDTO();
        precioDTOToSave.setValor(1000D);
        precioDTOToSave.setIdTipoCliente(1L);
        precioDTOToSave.setIdQueso(1L);

        TipoCliente tipoClienteMock = new TipoCliente();
        tipoClienteMock.setTipo("tipo");
        tipoClienteMock.setId(1L);

        Queso quesoMock = new Queso();
        quesoMock.setId(1L);
        quesoMock.setTipoQueso("tipoQueso");
        quesoMock.setNomenclatura("nomenclatura");
        quesoMock.setCodigo("codigo");

        Precio precioSaved = new Precio();
        precioSaved.setId(1L);
        precioSaved.setValor(1000D);
        precioSaved.setQueso(quesoMock);
        precioSaved.setTipoCliente(tipoClienteMock);

        var expectedPrecio = new PrecioDTO();
        expectedPrecio.setId(1L);
        expectedPrecio.setValor(1000D);
        expectedPrecio.setIdTipoCliente(1L);
        expectedPrecio.setIdQueso(1L);

        when(tipoClienteService.getEntity(1L)).thenReturn(tipoClienteMock);
        when(quesoRepository.findById(1L)).thenReturn(Optional.of(quesoMock));
        when(repository.save(any(Precio.class))).thenReturn(precioSaved);

        var dtoActual = service.save(precioDTOToSave);
        assertEquals(expectedPrecio, dtoActual);
    }

    @Test
    void Save__Tipo_Cliente_Not_Found() {
        PrecioDTO precioDTOToSave = new PrecioDTO();
        precioDTOToSave.setValor(1000D);
        precioDTOToSave.setIdTipoCliente(1L);
        precioDTOToSave.setIdQueso(1L);

        when(tipoClienteService.getEntity(1L)).thenThrow(new TipoClienteNotFoundException());
        TipoClienteNotFoundConflictException thrown = assertThrows(
                TipoClienteNotFoundConflictException.class, () -> service.save(precioDTOToSave)
        );
        assertEquals(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Save__Queso_Not_Found() {
        PrecioDTO precioDTOToSave = new PrecioDTO();
        precioDTOToSave.setValor(1000D);
        precioDTOToSave.setIdTipoCliente(1L);
        precioDTOToSave.setIdQueso(1L);

        TipoCliente tipoClienteMock = new TipoCliente();
        tipoClienteMock.setTipo("tipo");
        tipoClienteMock.setId(1L);

        when(tipoClienteService.getEntity(1L)).thenReturn(tipoClienteMock);
        when(quesoRepository.findById(1L)).thenReturn(Optional.empty());
        QuesoNotFoundConflictException thrown = assertThrows(
                QuesoNotFoundConflictException.class, () -> service.save(precioDTOToSave)
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update__OK() {
        PrecioUpdateDTO precioDTOToUpdate = new PrecioUpdateDTO();
        precioDTOToUpdate.setId(1L);
        precioDTOToUpdate.setValor(1000D);
        precioDTOToUpdate.setIdTipoCliente(1L);
        precioDTOToUpdate.setIdQueso(1L);

        TipoCliente tipoClienteMock = new TipoCliente();
        tipoClienteMock.setTipo("tipo");
        tipoClienteMock.setId(1L);

        Queso quesoMock = new Queso();
        quesoMock.setId(1L);
        quesoMock.setTipoQueso("tipoQueso");
        quesoMock.setNomenclatura("nomenclatura");
        quesoMock.setCodigo("codigo");

        Precio precioSaved = new Precio();
        precioSaved.setId(1L);
        precioSaved.setValor(1000D);
        precioSaved.setQueso(quesoMock);
        precioSaved.setTipoCliente(tipoClienteMock);

        var expectedPrecio = new PrecioDTO();
        expectedPrecio.setId(1L);
        expectedPrecio.setValor(1000D);
        expectedPrecio.setIdTipoCliente(1L);
        expectedPrecio.setIdQueso(1L);

        when(repository.existsById(1L)).thenReturn(true);
        when(tipoClienteService.getEntity(1L)).thenReturn(tipoClienteMock);
        when(quesoRepository.findById(1L)).thenReturn(Optional.of(quesoMock));
        when(repository.save(any(Precio.class))).thenReturn(precioSaved);

        var dtoActual = service.update(precioDTOToUpdate);
        assertEquals(expectedPrecio, dtoActual);
    }

    @Test
    void Update__Precio_Not_Found() {
        PrecioUpdateDTO precioDTOToUpdate = new PrecioUpdateDTO();
        precioDTOToUpdate.setId(1L);
        precioDTOToUpdate.setValor(1000D);
        precioDTOToUpdate.setIdTipoCliente(1L);
        precioDTOToUpdate.setIdQueso(1L);

        when(repository.existsById(1L)).thenReturn(false);
        PrecioNotFoundException thrown = assertThrows(
                PrecioNotFoundException.class, () -> service.update(precioDTOToUpdate)
        );
        assertEquals(ErrorMessages.MSG_PRECIO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update__Tipo_Cliente_Not_Found() {
        PrecioUpdateDTO precioDTOToUpdate = new PrecioUpdateDTO();
        precioDTOToUpdate.setId(1L);
        precioDTOToUpdate.setValor(1000D);
        precioDTOToUpdate.setIdTipoCliente(1L);
        precioDTOToUpdate.setIdQueso(1L);

        when(repository.existsById(1L)).thenReturn(true);
        when(tipoClienteService.getEntity(1L)).thenThrow(new TipoClienteNotFoundException());
        TipoClienteNotFoundConflictException thrown = assertThrows(
                TipoClienteNotFoundConflictException.class, () -> service.update(precioDTOToUpdate)
        );
        assertEquals(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update__Queso_Not_Found() {
        PrecioUpdateDTO precioDTOToUpdate = new PrecioUpdateDTO();
        precioDTOToUpdate.setId(1L);
        precioDTOToUpdate.setValor(1000D);
        precioDTOToUpdate.setIdTipoCliente(1L);
        precioDTOToUpdate.setIdQueso(1L);

        TipoCliente tipoClienteMock = new TipoCliente();
        tipoClienteMock.setTipo("tipo");
        tipoClienteMock.setId(1L);

        when(repository.existsById(1L)).thenReturn(true);
        when(tipoClienteService.getEntity(1L)).thenReturn(tipoClienteMock);
        when(quesoRepository.findById(1L)).thenReturn(Optional.empty());
        QuesoNotFoundConflictException thrown = assertThrows(
                QuesoNotFoundConflictException.class, () -> service.update(precioDTOToUpdate)
        );
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, thrown.getMessage());
    }
}
