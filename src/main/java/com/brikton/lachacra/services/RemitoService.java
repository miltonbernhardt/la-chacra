package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.RemitoDTO;
import com.brikton.lachacra.dtos.RemitoReportDTO;
import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.entities.ItemRemito;
import com.brikton.lachacra.entities.Remito;
import com.brikton.lachacra.exceptions.ExpedicionNotFoundException;
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
import java.math.BigDecimal;
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
            var quesoExpedicion = e.getLote().getQueso();
            if (!mapItems.containsKey(quesoExpedicion.getTipoQueso())) {
                var newItem = new ItemRemito();
                newItem.setQueso(quesoExpedicion);
                newItem.setPrecio(precioService.getPrecioValue(quesoExpedicion, e.getCliente().getTipoCliente()));
                newItem.setCantidad(e.getCantidad());
                newItem.setImporte(e.getImporte());
                newItem.setPeso(e.getPeso());
                mapItems.putIfAbsent(quesoExpedicion.getTipoQueso(), newItem);
            } else {
                var item = mapItems.get(quesoExpedicion.getTipoQueso());
                updateItemRemito(item,e);
            }
        });
        List<ItemRemito> items = new ArrayList<>(mapItems.values());
        remito.setItemsRemito(items);
    }

    public void updateItemRemito(ItemRemito itemRemito, Expedicion expedicion){
        var quesoExpedicion = expedicion.getLote().getQueso();
        var quesoRemito = itemRemito.getQueso();
        if (quesoExpedicion.getTipoQueso().equals(quesoRemito.getTipoQueso())) {

            var cantidad = itemRemito.getCantidad() + expedicion.getCantidad();
            var peso = BigDecimal.valueOf(itemRemito.getPeso()).add(BigDecimal.valueOf(expedicion.getPeso()));
            var importe = BigDecimal.valueOf(itemRemito.getImporte()).add(BigDecimal.valueOf(expedicion.getImporte()));

            itemRemito.setCantidad(cantidad);
            itemRemito.setImporte(importe.doubleValue());
            itemRemito.setPeso(importe.doubleValue());
        }
    }

    public Remito generateRemito(Long idCliente, LocalDate fecha) {

        var cliente = clienteService.get(idCliente);
        var expediciones = expedicionService.getForRemito(cliente);

        BigDecimal importe = BigDecimal.valueOf(0d);
        for (Expedicion e : expediciones)
            importe = importe.add(BigDecimal.valueOf(e.getImporte()));

        var remito = new Remito();
        remito.setExpediciones(expediciones);
        remito.setFecha(fecha);
        remito.setImporteTotal(importe.doubleValue());

        generateItemsRemito(remito);
        return remito;
    }

    public RemitoDTO generateRemitoDTO(Long idCliente, LocalDate fecha) {
        return new RemitoDTO(generateRemito(idCliente, fecha));
    }

    public RemitoDTO generateAndSave(Long idCliente, LocalDate fecha) {
        var remito = generateRemito(idCliente, fecha);
        if (remito.getExpediciones() == null ||
        remito.getExpediciones().isEmpty()) throw new ExpedicionNotFoundException();
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

        var dto = new RemitoReportDTO(remito);

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
