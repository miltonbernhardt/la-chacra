package com.brikton.lachacra.services;

import com.brikton.lachacra.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {VentaService.class})
public class VentaServiceTest {

    @Autowired
    VentaService service;

    @MockBean
    RemitoService remitoService;

    @Test
    public void Get_Ventas__OK(){
        var remito = mockRemito();
        remitoService.generateItemsRemito(remito);
        when(remitoService.getBetweenDates(any(LocalDate.class),any(LocalDate.class)))
                .thenReturn(List.of(remito));

        var result = service.getVentas(LocalDate.of(2021, 10, 11),LocalDate.of(2021, 10, 11));
        assertEquals(1,result.size());
        assertEquals(2,result.get(0).getVentas().size());
    }

    Remito mockRemito(){
        var remito = new Remito();
        remito.setId(1L);
        remito.setExpediciones(List.of(mockExpedicionA(),mockExpedicionB()));
        remito.setFecha(LocalDate.of(2021, 10, 11));
        remito.setImporteTotal(500d);
        remito.setItemsRemito(List.of(mockItemRemitoA(),mockItemRemitoB(),mockItemRemitoC()));
        return remito;
    }

    private ItemRemito mockItemRemitoA() {
        ItemRemito item = new ItemRemito();
        item.setPeso(50d);
        item.setImporte(100d);
        item.setQueso(mockQuesoA());
        item.setPrecio(1000d);
        item.setCantidad(500);
        return item;
    }

    private ItemRemito mockItemRemitoB() {
        ItemRemito item = new ItemRemito();
        item.setPeso(50d);
        item.setImporte(100d);
        item.setQueso(mockQuesoA());
        item.setPrecio(1000d);
        item.setCantidad(500);
        return item;
    }

    private ItemRemito mockItemRemitoC() {
        var item = new ItemRemito();
        item.setPeso(50d);
        item.setImporte(100d);
        item.setQueso(mockQuesoB());
        item.setPrecio(1000d);
        item.setCantidad(500);
        return item;
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

    Lote mockLoteA() {
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
        lote.setQueso(mockQuesoA());
        return lote;
    }

    Lote mockLoteB() {
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
        lote.setQueso(mockQuesoA());
        return lote;
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
}
