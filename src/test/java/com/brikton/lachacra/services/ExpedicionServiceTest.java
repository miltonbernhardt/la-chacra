package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ExpedicionDTO;
import com.brikton.lachacra.entities.*;
import com.brikton.lachacra.repositories.ExpedicionRepository;
import com.brikton.lachacra.repositories.LoteRepository;
import com.brikton.lachacra.repositories.RemitoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        //TODO
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
        //TODO
    }

    @Test
    void Save__Lote_Not_Found() {
        //TODO
    }

    @Test
    void Save__Precio_Not_Found() {
        //TODO
    }

    @Test
    void Update_Same_Lote_Same_Quantity__OK() {
        //TODO
    }

    @Test
    void Update_Same_Lote_Different_Quantity__OK() {
        //TODO
    }

    @Test
    void Update_Different_Lote__OK() {
        //TODO
    }

    @Test
    void Update__Expedicion_Not_Found() {
        //TODO
    }

    @Test
    void Update__Cliente_Not_Found() {
        //TODO
    }

    @Test
    void Update__Lote_Not_Found() {
        //TODO
    }

    @Test
    void Update__Precio_Not_Found() {
        //TODO
    }

    @Test
    void Delete_Without_Dependencies__OK() {
        //TODO
    }

    @Test
    void Delete_With_Dependency_Of_Remito__OK() {
        //TODO
    }

    @Test
    void Delete__Expedicion_Not_Found() {
        //TODO
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
        lote.setCantHormas(1);
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
