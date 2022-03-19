package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.RemitoDTO;
import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.entities.ItemRemito;
import com.brikton.lachacra.entities.Remito;
import com.brikton.lachacra.exceptions.RemitoNotFoundException;
import com.brikton.lachacra.repositories.RemitoRepository;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RemitoService {

    @Autowired
    ResourceLoader resourceLoader;

    private final PrecioService precioService;
    private final ClienteService clienteService;
    private final ExpedicionService expedicionService;
    private final RemitoRepository repository;

    public RemitoService(PrecioService precioService, ClienteService clienteService, ExpedicionService expedicionService, RemitoRepository repository) {
        this.precioService = precioService;
        this.clienteService = clienteService;
        this.expedicionService = expedicionService;
        this.repository = repository;
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

    public Remito generateRemito(Long idCliente, LocalDate fecha) {

        var cliente = clienteService.get(idCliente);
        var expediciones = expedicionService.getForRemito(cliente);

        Double importe = 0d;
        for (Expedicion e : expediciones) importe += e.getImporte();

        var remito = new Remito();
        remito.setExpediciones(expediciones);
        remito.setFecha(fecha);
        remito.setImporteTotal(importe);

        generateItemsRemito(remito);
        return remito;
    }

    public RemitoDTO generateRemitoDTO(Long idCliente, LocalDate fecha) {
        return new RemitoDTO(generateRemito(idCliente, fecha));
    }
    //TODO do not generate if it aint contains items
    public RemitoDTO generateAndSave(Long idCliente, LocalDate fecha) {
        var remito = generateRemito(idCliente, fecha);
        expedicionService.setOnRemitoTrue(remito.getExpediciones());
        return new RemitoDTO(repository.save(remito));
    }

    public Remito getRemito(Long id) {
        var remito = repository.findById(id);
        if (remito.isEmpty()) throw new RemitoNotFoundException();
        return remito.get();
    }

    public byte[] getPdf(Long id) throws JRException, IOException {

        var remito = getRemito(id);

        generateItemsRemito(remito);

        var cliente = remito.getExpediciones().get(0).getCliente();

        var dto = new RemitoDTO(remito);

        Map<String, Object> remitoParams = new HashMap<String, Object>();
        remitoParams.put("importeTotal", dto.getImporteTotal());
        remitoParams.put("nombreCliente", cliente.getRazonSocial());
        remitoParams.put("domicilio", cliente.getDomicilio());
        remitoParams.put("localidad", cliente.getLocalidad() + ", " + cliente.getProvincia());
        remitoParams.put("cuit", cliente.getCuit());
        remitoParams.put("transporte", cliente.getTransporte());
        remitoParams.put("senasaUta", cliente.getSenasaUta());
        remitoParams.put("cajas", 10);
        remitoParams.put("fecha", dto.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        remitoParams.put("ds", new JRBeanCollectionDataSource(dto.getItemsRemito()));

        Resource remitoTemplate = resourceLoader.getResource("classpath:Remito.jrxml");

        JasperReport jasperReport
                = JasperCompileManager.compileReport(remitoTemplate.getFile().getAbsolutePath());
        JRSaver.saveObject(jasperReport, "Remito.jasper");

        JasperPrint empReport =
                JasperFillManager.fillReport
                        (jasperReport, remitoParams, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(empReport);
    }
}
