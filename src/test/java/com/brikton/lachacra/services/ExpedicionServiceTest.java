package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.ExpedicionDTO;
import com.brikton.lachacra.dtos.ExpedicionUpdateDTO;
import com.brikton.lachacra.entities.*;
import com.brikton.lachacra.exceptions.*;
import com.brikton.lachacra.repositories.ExpedicionRepository;
import com.brikton.lachacra.repositories.LoteRepository;
import com.brikton.lachacra.repositories.RemitoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ExpedicionService.class})
public class ExpedicionServiceTest {

    @Autowired
    ExpedicionService service;

    @MockBean
    ExpedicionRepository repository;

    @MockBean
    LoteRepository loteRepository;

    @MockBean
    PrecioService precioService;

    @MockBean
    ClienteService clienteService;

    @MockBean
    RemitoRepository remitoRepository;

    @MockBean
    LoteService loteService;

    @MockBean
    QuesoService quesoService;

    @Test
    void Get_All__OK() {
        var expedicion = mockExpedicion();

        List<Expedicion> expedicionList = new ArrayList<>();

        expedicionList.add(expedicion);
        expedicionList.add(expedicion);

        when(repository.findAll()).thenReturn(expedicionList);

        var response = service.getAll();
        assertEquals(2, response.size());
    }

    @Test
    void Save__OK() {
        var dto = new ExpedicionDTO();
        dto.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dto.setPeso(10D);
        dto.setImporte(900D);
        dto.setIdCliente(1L);
        dto.setIdLote("101020210011");

        when(clienteService.get(1L)).thenReturn(mockCliente());
        when(loteService.get("101020210011")).thenReturn(mockLote());
        when(precioService.getPrecioValue(any(Queso.class), any(TipoCliente.class))).thenReturn(150.0);
        when(repository.save(any(Expedicion.class))).then(AdditionalAnswers.returnsFirstArg());

        var response = service.save(dto);
        assertEquals(1500, response.getImporte());
    }

    @Test
    void Save__Cliente_Not_Found() {
        var dto = new ExpedicionDTO();

        dto.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dto.setPeso(10D);
        dto.setImporte(900D);
        dto.setIdCliente(1L);
        dto.setIdLote("101020210011");

        when(clienteService.get(1L)).thenThrow(new ClienteNotFoundException());

        ClienteNotFoundException thrown = assertThrows(
                ClienteNotFoundException.class, () -> service.save(dto)
        );
        assertEquals(ErrorMessages.MSG_CLIENTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Save__Lote_Not_Found() {
        var dto = new ExpedicionDTO();
        dto.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dto.setPeso(10D);
        dto.setImporte(900D);
        dto.setIdCliente(1L);
        dto.setIdLote("101020210011");
        when(clienteService.get(1L)).thenReturn(mockCliente());
        when(loteService.get("101020210011")).thenThrow(new LoteNotFoundException());
        LoteNotFoundException thrown = assertThrows(
                LoteNotFoundException.class, () -> service.save(dto)
        );
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Save__Precio_Not_Found() {
        var dto = new ExpedicionDTO();
        dto.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dto.setPeso(10D);
        dto.setImporte(900D);
        dto.setIdCliente(1L);
        dto.setIdLote("101020210011");

        when(clienteService.get(1L)).thenReturn(mockCliente());
        when(loteService.get("101020210011")).thenReturn(mockLote());
        when(precioService.getPrecioValue(any(Queso.class), any(TipoCliente.class))).thenThrow(new PrecioNotFoundException());

        PrecioNotFoundException thrown = assertThrows(
                PrecioNotFoundException.class, () -> service.save(dto)
        );
        assertEquals(ErrorMessages.MSG_PRECIO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update_Same_Lote_Same_Quantity__OK() {
        var dtoUpdate = new ExpedicionUpdateDTO();
        dtoUpdate.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dtoUpdate.setPeso(20D);
        dtoUpdate.setCantidad(20);
        dtoUpdate.setImporte(900D);
        dtoUpdate.setIdCliente(1L);
        dtoUpdate.setIdLote("101020210011");
        dtoUpdate.setId(1L);

        when(clienteService.get(1L)).thenReturn(mockCliente());
        when(loteService.get("101020210011")).thenReturn(mockLote());
        when(precioService.getPrecioValue(any(Queso.class), any(TipoCliente.class))).thenReturn(150.0);
        when(repository.findById(1L)).thenReturn(Optional.of(mockExpedicion()));
        when(repository.save(any(Expedicion.class))).then(AdditionalAnswers.returnsFirstArg());

        var response = service.update(dtoUpdate);

        assertEquals(3000, response.getImporte());
        verify(loteService, never()).increaseStock(any(Lote.class), any(Integer.class));
        verify(loteService, never()).decreaseStock(any(Lote.class), any(Integer.class));
    }

    @Test
    void Update_Same_Lote_Different_Quantity__OK() {
        var expedicion = mockExpedicion();
        expedicion.setCantidad(50);

        var dtoUpdate = new ExpedicionUpdateDTO();
        dtoUpdate.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dtoUpdate.setPeso(20D);
        dtoUpdate.setCantidad(20);
        dtoUpdate.setImporte(900D);
        dtoUpdate.setIdCliente(1L);
        dtoUpdate.setIdLote("101020210011");
        dtoUpdate.setId(1L);

        when(clienteService.get(1L)).thenReturn(mockCliente());
        when(loteService.get("101020210011")).thenReturn(mockLote());
        when(precioService.getPrecioValue(any(Queso.class), any(TipoCliente.class))).thenReturn(150.0);
        when(repository.findById(1L)).thenReturn(Optional.of(expedicion));
        when(repository.save(any(Expedicion.class))).then(AdditionalAnswers.returnsFirstArg());

        var response = service.update(dtoUpdate);
        assertEquals(20, response.getCantidad());
        assertEquals(3000, response.getImporte());
        verify(loteService, never()).increaseStock(any(Lote.class), any(Integer.class));
        verify(loteService).decreaseStock(any(Lote.class), any(Integer.class));
    }

    @Test
    void Update_Different_Lote__OK() {
        var expedicion = mockExpedicion();
        expedicion.getLote().setId("101020210013");
        expedicion.setCantidad(50);

        var dtoUpdate = new ExpedicionUpdateDTO();
        dtoUpdate.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dtoUpdate.setPeso(20D);
        dtoUpdate.setCantidad(20);
        dtoUpdate.setImporte(900D);
        dtoUpdate.setIdCliente(1L);
        dtoUpdate.setIdLote("101020210011");
        dtoUpdate.setId(1L);

        when(clienteService.get(1L)).thenReturn(mockCliente());
        when(loteService.get("101020210011")).thenReturn(mockLote());
        when(precioService.getPrecioValue(any(Queso.class), any(TipoCliente.class))).thenReturn(150.0);
        when(repository.findById(1L)).thenReturn(Optional.of(expedicion));
        when(repository.save(any(Expedicion.class))).then(AdditionalAnswers.returnsFirstArg());

        var response = service.update(dtoUpdate);
        assertEquals(20, response.getCantidad());
        verify(loteService).increaseStock(any(Lote.class), any(Integer.class));
        verify(loteService).decreaseStock(any(Lote.class), any(Integer.class));
    }

    @Test
    void Update__Expedicion_Not_Found() {
        var dto = new ExpedicionUpdateDTO();
        dto.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dto.setPeso(10D);
        dto.setImporte(900D);
        dto.setIdCliente(1L);
        dto.setIdLote("101020210011");
        dto.setId(1L);

        when(clienteService.get(1L)).thenReturn(mockCliente());
        when(loteService.get("101020210011")).thenReturn(mockLote());
        when(precioService.getPrecioValue(any(Queso.class), any(TipoCliente.class))).thenThrow(new PrecioNotFoundException());
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ExpedicionNotFoundException thrown = assertThrows(
                ExpedicionNotFoundException.class, () -> service.update(dto)
        );
        assertEquals(ErrorMessages.MSG_EXPEDICION_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update__Cliente_Not_Found() {
        var expedicion = mockExpedicion();
        expedicion.getLote().setId("101020210013");
        expedicion.setCantidad(50);

        var dto = new ExpedicionUpdateDTO();

        dto.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dto.setPeso(10D);
        dto.setImporte(900D);
        dto.setIdCliente(1L);
        dto.setIdLote("101020210011");
        dto.setId(1L);

        when(clienteService.get(1L)).thenThrow(new ClienteNotFoundException());
        when(repository.findById(1L)).thenReturn(Optional.of(expedicion));

        ClienteNotFoundException thrown = assertThrows(
                ClienteNotFoundException.class, () -> service.update(dto)
        );
        assertEquals(ErrorMessages.MSG_CLIENTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update__Lote_Not_Found() {
        var dto = new ExpedicionUpdateDTO();
        dto.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dto.setPeso(10D);
        dto.setImporte(900D);
        dto.setIdCliente(1L);
        dto.setIdLote("101020210011");
        dto.setId(1L);
        when(clienteService.get(1L)).thenReturn(mockCliente());
        when(repository.findById(1L)).thenReturn(Optional.of(mockExpedicion()));
        when(loteService.get("101020210011")).thenThrow(new LoteNotFoundException());
        LoteNotFoundException thrown = assertThrows(
                LoteNotFoundException.class, () -> service.update(dto)
        );
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update__Precio_Not_Found() {
        var dto = new ExpedicionUpdateDTO();
        dto.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dto.setPeso(10D);
        dto.setCantidad(20);
        dto.setImporte(900D);
        dto.setIdCliente(1L);
        dto.setIdLote("101020210011");
        dto.setId(1L);

        when(clienteService.get(1L)).thenReturn(mockCliente());
        when(loteService.get("101020210011")).thenReturn(mockLote());
        when(repository.findById(1L)).thenReturn(Optional.of(mockExpedicion()));
        when(precioService.getPrecioValue(any(Queso.class), any(TipoCliente.class))).thenThrow(new PrecioNotFoundException());

        PrecioNotFoundException thrown = assertThrows(
                PrecioNotFoundException.class, () -> service.update(dto)
        );
        assertEquals(ErrorMessages.MSG_PRECIO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Delete_Without_Dependencies__OK() {
        var expedicionToDelete = new Expedicion();
        expedicionToDelete.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        expedicionToDelete.setPeso(10D);
        expedicionToDelete.setCantidad(5);
        expedicionToDelete.setImporte(900D);
        expedicionToDelete.setLote(new Lote());

        when(repository.findById(1L)).thenReturn(Optional.of(expedicionToDelete));
        when(remitoRepository.existsByExpedicionesContains(any(Expedicion.class))).thenReturn(false);

        service.delete(1L);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(loteService).increaseStock(any(Lote.class), captor.capture());
        verify(repository).delete(any(Expedicion.class));
        assertEquals(5, captor.getValue());
    }

    @Test
    void Delete_With_Dependency_Of_Remito__Cannot_Delete() {
        var expedicionToDelete = new Expedicion();
        expedicionToDelete.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        expedicionToDelete.setPeso(10D);
        expedicionToDelete.setCantidad(5);
        expedicionToDelete.setImporte(900D);
        expedicionToDelete.setLote(new Lote());

        when(repository.findById(1L)).thenReturn(Optional.of(expedicionToDelete));
        when(remitoRepository.existsByExpedicionesContains(any(Expedicion.class))).thenReturn(true);

        ExpedicionCannotDeleteException thrown = assertThrows(
                ExpedicionCannotDeleteException.class, () -> service.delete(1L)
        );
        assertEquals(ErrorMessages.MSG_EXPEDICION_CANNOT_BE_DELETED, thrown.getMessage());
    }

    @Test
    void Delete__Expedicion_Not_Found() {
        var expedicionToDelete = new Expedicion();
        expedicionToDelete.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        expedicionToDelete.setPeso(10D);
        expedicionToDelete.setCantidad(5);
        expedicionToDelete.setImporte(900D);
        expedicionToDelete.setLote(new Lote());

        when(repository.findById(1L)).thenReturn(Optional.empty());

        ExpedicionNotFoundException thrown = assertThrows(
                ExpedicionNotFoundException.class, () -> service.delete(1L)
        );
        assertEquals(ErrorMessages.MSG_EXPEDICION_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_All_By_Lote__OK(){
        when(loteService.get(anyString())).thenReturn(mockLote());
        when(repository.findAllByLote(any(Lote.class))).thenReturn(List.of(mockExpedicion()));
        var result = service.getAllByLote("101020210011");
        assertEquals(1,result.size());
    }

    private Expedicion mockExpedicion() {
        var exp = new Expedicion();
        exp.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        exp.setPeso(10D);
        exp.setCantidad(20);
        exp.setId(1L);
        exp.setImporte(900D);
        exp.setCliente(mockCliente());
        exp.setLote(mockLote());
        return exp;
    }

    Lote mockLote() {
        Lote lote = new Lote();
        lote.setId("101020210011");
        lote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        lote.setNumeroTina(1);
        lote.setLitrosLeche(1D);
        lote.setCantHormas(100);
        lote.setStockLote(1);
        lote.setPeso(1D);
        lote.setRendimiento(1D);
        lote.setLoteCultivo("cultivo1, cultivo2");
        lote.setLoteColorante("colorante1, colorante2");
        lote.setLoteCalcio("calcio1, calcio2");
        lote.setLoteCuajo("cuajo1, cuajo2");
        lote.setQueso(mockQueso());
        return lote;
    }

    Queso mockQueso() {
        Queso queso = new Queso();
        queso.setId(1L);
        queso.setCodigo("001");
        queso.setTipoQueso("tipoQueso");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }

    Cliente mockCliente() {
        var mockCliente = new Cliente();
        mockCliente.setId(1L);
        mockCliente.setTipoCliente(mockTipoCliente());
        mockCliente.setCuit("99888888887");
        mockCliente.setCodPostal("3000");
        mockCliente.setDomicilio("Domicilio 1");
        mockCliente.setLocalidad("Localidad 1");
        mockCliente.setPais("Pais 1");
        mockCliente.setProvincia("Provincia 1");
        mockCliente.setTransporte("Provincia 1");
        mockCliente.setSenasaUta("Senasa UTA 1");
        mockCliente.setTelefono("233334444444");
        mockCliente.setCelular("233334444444");
        mockCliente.setFax("233334444444");
        mockCliente.setEmail("mail1@mail.com");
        mockCliente.setRazonSocial("Razon social 1");
        return mockCliente;
    }

    TipoCliente mockTipoCliente() {
        var mockTipoCliente = new TipoCliente();
        mockTipoCliente.setId(1L);
        mockTipoCliente.setTipo("tipo1");
        return mockTipoCliente;
    }

    Precio mockPrecio() {
        var precio = new Precio();
        precio.setId(1L);
        precio.setQueso(mockQueso());
        precio.setTipoCliente(mockTipoCliente());
        precio.setValor(150d);
        return precio;
    }
}
