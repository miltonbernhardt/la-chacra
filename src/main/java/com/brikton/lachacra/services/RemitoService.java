package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ItemRemitoDTO;
import com.brikton.lachacra.dtos.RemitoDTO;
import com.brikton.lachacra.entities.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RemitoService {

    private final PrecioService precioService;
    private final ClienteService clienteService;
    private final ExpedicionService expedicionService;

    public RemitoService(PrecioService precioService, ClienteService clienteService, ExpedicionService expedicionService) {
        this.precioService = precioService;
        this.clienteService = clienteService;
        this.expedicionService = expedicionService;
    }

    public void generateItemsRemito(Remito remito) {
        var expediciones = remito.getExpediciones();
        Map<String, ItemRemito> mapItems = new HashMap<>();
        expediciones.forEach(e -> {
            if (!mapItems.containsKey(e.getLote().getQueso().getTipoQueso())) {
                var newItem = new ItemRemito();
                newItem.setQueso(e.getLote().getQueso());
                newItem.setPrecio(precioService.getPrecioValue(e.getLote().getQueso(), e.getCliente().getTipoCliente()));
                newItem.setCantidad(e.getCantidad());
                newItem.setImporte(e.getImporte());
                newItem.setPeso(e.getPeso());
                mapItems.putIfAbsent(e.getLote().getQueso().getTipoQueso(), newItem);
            } else {
                var item = mapItems.get(e.getLote().getQueso().getTipoQueso());
                item.update(e);
            }
        });
        List<ItemRemito> items = new ArrayList<>();
        items.addAll(mapItems.values());
        remito.setItemsRemito(items);
    }

    public byte[] getPdf() throws JRException, FileNotFoundException {

        ItemRemito item = new ItemRemito();
        item.setPeso(23d);
        item.setCantidad(5);
        item.setQueso(mockQuesoA());
        item.setImporte(15000d);
        item.setPrecio(500d);
        ItemRemitoDTO item1 = new ItemRemitoDTO(item);
        item.setPeso(50d);
        item.setCantidad(15);
        item.setQueso(mockQuesoB());
        item.setImporte(30000d);
        item.setPrecio(500d);
        ItemRemitoDTO item2 = new ItemRemitoDTO(item);
        item.setPeso(80d);
        item.setCantidad(30);
        item.setQueso(mockQuesoC());
        item.setImporte(80000d);
        item.setPrecio(200d);
        ItemRemitoDTO item3 = new ItemRemitoDTO(item);


        RemitoDTO dto = new RemitoDTO();
        dto.setId(1L);
        dto.setFecha(LocalDate.of(2021, 10, 11));
        dto.setImporteTotal(500d);
        dto.setItemsRemito(List.of(item1, item2, item3));

        Map<String, Object> remitoParams = new HashMap<String, Object>();
        remitoParams.put("importeTotal", dto.getImporteTotal());
        //var cliente = remito.getExpediciones().get(0).getCliente();
        var cliente = mockCliente();
        remitoParams.put("nombreCliente", cliente.getRazonSocial());
        remitoParams.put("domicilio", cliente.getDomicilio());
        remitoParams.put("localidad", cliente.getLocalidad() + ", " + cliente.getProvincia());
        remitoParams.put("cuit", cliente.getCuit());
        remitoParams.put("transporte", cliente.getTransporte());
        remitoParams.put("senasaUta", cliente.getSenasaUta());
        remitoParams.put("cajas", 10);
        remitoParams.put("fecha", dto.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        remitoParams.put("ds", new JRBeanCollectionDataSource(dto.getItemsRemito()));

        JasperReport jasperReport
                = JasperCompileManager.compileReport(
                "/home/elias/Development/la-chacra/src/main/resources/Remito.jrxml"); //TODO change to relative path
        JRSaver.saveObject(jasperReport, "employeeReport.jasper");

        JasperPrint empReport =
                JasperFillManager.fillReport
                        (jasperReport, remitoParams, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(empReport);
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

    Lote mockLoteC() {
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
        lote.setQueso(mockQuesoB());
        return lote;
    }

    Queso mockQuesoA() {
        Queso queso = new Queso();
        queso.setId(1L);
        queso.setCodigo("001");
        queso.setTipoQueso("ASTOPEROTOSEMO");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }

    Queso mockQuesoB() {
        Queso queso = new Queso();
        queso.setId(2L);
        queso.setCodigo("002");
        queso.setTipoQueso("MORTAMATEKA");
        queso.setNomenclatura("tip");
        queso.setStock(1);
        return queso;
    }

    Queso mockQuesoC() {
        Queso queso = new Queso();
        queso.setId(2L);
        queso.setCodigo("002");
        queso.setTipoQueso("BUZARDEKKA");
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
    public Remito generateRemito(Long idCliente, LocalDate fecha) {

        var cliente = clienteService.get(idCliente);
        var expediciones = expedicionService.getForRemito(cliente);

        Double importe = 0d;
        for(Expedicion e: expediciones) importe+=e.getImporte();

        var remito = new Remito();
        remito.setExpediciones(expediciones);
        remito.setFecha(fecha);
        remito.setImporteTotal(importe);

        generateItemsRemito(remito);
        return remito;
    }

    public RemitoDTO generateRemitoDTO(Long idCliente, LocalDate fecha) {
       return new RemitoDTO(generateRemito(idCliente,fecha));
    }

}
