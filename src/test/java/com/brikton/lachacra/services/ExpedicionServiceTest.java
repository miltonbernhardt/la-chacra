package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ExpedicionDTO;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.*;
import com.brikton.lachacra.repositories.*;
import com.brikton.lachacra.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ExpedicionService.class})
public class ExpedicionServiceTest {

    @Autowired
    ExpedicionService expedicionService;

    @MockBean
    ExpedicionRepository repository;

    @MockBean
    LoteRepository loteRepository;

    @MockBean
    PrecioRepository precioRepository;

    @MockBean
    ClienteRepository clienteRepository;

    @MockBean
    RemitoRepository remitoRepository;

    @MockBean
    LoteService loteService;

    @MockBean
    QuesoService quesoService;

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

    @Test
    void Exists_By_Cliente__True() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(repository.existsByCliente(cliente)).thenReturn(true);
        var exists = expedicionService.existsByCliente(cliente);
        assertTrue(exists);
    }

    @Test
    void Exists_By_Cliente__False() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(repository.existsByCliente(cliente)).thenReturn(false);
        var exists = expedicionService.existsByCliente(cliente);
        assertFalse(exists);
    }
//TODO
    @Test
    void Save__OK(){
        when(repository.save(any(Expedicion.class))).then(AdditionalAnswers.returnsFirstArg());
        when(repository.existsById(any(Long.class))).thenReturn(false);
        when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.of(mockCliente()));
        when(loteService.getEntity(any(String.class))).thenReturn(mockLote());
        when(precioRepository.findByQuesoAndAndTipoCliente(any(Queso.class),any(TipoCliente.class))).thenReturn(Optional.of(mockPrecio()));

        var expedicion = mockExpedicionDTO();
        expedicion.setId(null);
        var response = expedicionService.save(expedicion);
        assertEquals(1500,response.getImporte());
    }

    private ExpedicionDTO mockExpedicionDTO(){
        var dto = new ExpedicionDTO();
        dto.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dto.setPeso(10D);
        dto.setCantidad(20);
        dto.setId(1L);
        dto.setImporte(900D);
        dto.setIdCliente(1L);
        dto.setIdLote("101020210011");
        return dto;
    }

    private Expedicion mockExpedicion(){
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

    Cliente mockCliente(){
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

    TipoCliente mockTipoCliente(){
        var mockTipoCliente = new TipoCliente();
        mockTipoCliente.setId(1L);
        mockTipoCliente.setTipo("tipo1");
        return mockTipoCliente;
    }

    Precio mockPrecio(){
        var precio = new Precio();
        precio.setId(1L);
        precio.setQueso(mockQueso());
        precio.setTipoCliente(mockTipoCliente());
        precio.setValor(150d);
        return precio;
    }
}
