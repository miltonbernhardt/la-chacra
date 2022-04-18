package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.entities.*;
import com.brikton.lachacra.exceptions.ExpedicionNotFoundException;
import com.brikton.lachacra.exceptions.RemitoNotFoundException;
import com.brikton.lachacra.repositories.RemitoRepository;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {RemitoService.class})
public class RemitoServiceTest {

    @Autowired
    RemitoService service;

    @MockBean
    PrecioService precioService;

    @MockBean
    ClienteService clienteService;

    @MockBean
    ExpedicionService expedicionService;

    @MockBean
    RemitoRepository repository;

    @Test
    void Get_Remito__OK() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(mockRemito()));
        var remito = service.getRemito(1000L);
        assertEquals(mockRemito(), remito);
    }

    @Test
    void Get_Remito__Error_Remito_Not_Found() {
        var thrown = assertThrows(
                RemitoNotFoundException.class, () -> service.getRemito(1000L)
        );
        assertEquals(ErrorMessages.MSG_REMITO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Get_Between_Dates__OK() {
        var remito = mockRemito();

        when(repository.findAllByFechaBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(List.of(remito));
        var remitoList = service.getBetweenDates(LocalDate.now(), LocalDate.now());
        assertEquals(1, remitoList.size());
        assertEquals(40, remitoList.get(0).getItemsRemito().get(0).getCantidad());
        assertEquals(20.0, remitoList.get(0).getItemsRemito().get(0).getPeso());
        assertEquals(0.0, remitoList.get(0).getItemsRemito().get(0).getPrecio());
        assertEquals(1800.0, remitoList.get(0).getItemsRemito().get(0).getImporte());
        assertEquals(mockQuesoA(), remitoList.get(0).getItemsRemito().get(0).getQueso());
    }

    @Test
    void Generate_Remito_DTO__OK() {
        when(clienteService.get(any(Long.class))).thenReturn(mockCliente());
        when(expedicionService.getForRemito(any(Cliente.class)))
                .thenReturn(List.of(mockExpedicionA(), mockExpedicionB(), mockExpedicionC()));
        var result = service.generateRemitoDTO(1L, LocalDate.of(2022, 10, 11));
        assertEquals(2, result.getItemsRemito().size());
        assertEquals(2800D, result.getImporteTotal());
    }

    @Test
    void Generate_And_Save__OK() {
        when(clienteService.get(any(Long.class))).thenReturn(mockCliente());
        when(expedicionService.getForRemito(any(Cliente.class))).thenReturn(List.of(mockExpedicionA(), mockExpedicionB(), mockExpedicionC()));
        when(repository.save(any(Remito.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        var result = service.generateAndSave(1L, LocalDate.of(2022, 10, 11));
        assertEquals(2, result.getItemsRemito().size());
        assertEquals(2800D, result.getImporteTotal());
    }

    @Test
    void Generate_And_Save__Error() {
        when(clienteService.get(any(Long.class))).thenReturn(mockCliente());
        when(expedicionService.getForRemito(any(Cliente.class))).thenReturn(List.of());
        when(repository.save(any(Remito.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        var thrown = assertThrows(
                ExpedicionNotFoundException.class, () -> service.generateAndSave(1L, LocalDate.of(2022, 10, 11))
        );
        assertEquals(ErrorMessages.MSG_EXPEDICION_NOT_FOUND, thrown.getMessage());
    }

    private Remito mockRemito() {
        var remito = new Remito();
        remito.setId(1L);
        remito.setExpediciones(List.of(mockExpedicionA(), mockExpedicionB()));
        remito.setFecha(LocalDate.of(2021, 10, 11));
        remito.setImporteTotal(500d);
        return remito;
    }

    private Expedicion mockExpedicionA() {
        var exp = new Expedicion();
        exp.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        exp.setPeso(10D);
        exp.setCantidad(20);
        exp.setId(1L);
        exp.setImporte(900D);
        exp.setCliente(mockCliente());
        exp.setLote(mockLoteA());
        return exp;
    }

    private Expedicion mockExpedicionB() {
        var exp = new Expedicion();
        exp.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        exp.setPeso(10D);
        exp.setCantidad(20);
        exp.setId(2L);
        exp.setImporte(900D);
        exp.setCliente(mockCliente());
        exp.setLote(mockLoteB());
        return exp;
    }

    private Expedicion mockExpedicionC() {
        var exp = new Expedicion();
        exp.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        exp.setPeso(10D);
        exp.setCantidad(20);
        exp.setId(3L);
        exp.setImporte(1000D);
        exp.setCliente(mockCliente());
        exp.setLote(mockLoteC());
        return exp;
    }

    private Lote mockLoteA() {
        var lote = new Lote();
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
        lote.setQueso(mockQuesoA());
        return lote;
    }

    private Lote mockLoteB() {
        var lote = new Lote();
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
        lote.setQueso(mockQuesoA());
        return lote;
    }

    private Lote mockLoteC() {
        var lote = new Lote();
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
        lote.setQueso(mockQuesoB());
        return lote;
    }

    private Queso mockQuesoA() {
        var queso = new Queso();
        queso.setId(1L);
        queso.setCodigo("001");
        queso.setTipoQueso("A");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }

    private Queso mockQuesoB() {
        var queso = new Queso();
        queso.setId(2L);
        queso.setCodigo("002");
        queso.setTipoQueso("B");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }

    private Cliente mockCliente() {
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

    private TipoCliente mockTipoCliente() {
        var mockTipoCliente = new TipoCliente();
        mockTipoCliente.setId(1L);
        mockTipoCliente.setTipo("tipo1");
        return mockTipoCliente;
    }
}
