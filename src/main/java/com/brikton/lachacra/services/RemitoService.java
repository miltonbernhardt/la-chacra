package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ItemRemitoReportDTO;
import com.brikton.lachacra.dtos.RemitoDTO;
import com.brikton.lachacra.entities.Cliente;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class RemitoService {

    private final ResourceLoader resourceLoader;
    private final PrecioService precioService;
    private final ClienteService clienteService;
    private final ExpedicionService expedicionService;
    private final RemitoRepository repository;

    public RemitoService(
            PrecioService precioService,
            ClienteService clienteService,
            ExpedicionService expedicionService,
            RemitoRepository repository,
            ResourceLoader resourceLoader
    ) {
        this.precioService = precioService;
        this.clienteService = clienteService;
        this.expedicionService = expedicionService;
        this.repository = repository;
        this.resourceLoader = resourceLoader;
    }

    private Remito generateRemito(Long idCliente, LocalDate fecha) {

        var cliente = clienteService.get(idCliente);

        var expediciones = expedicionService.getForRemito(cliente);

        var items = generateItemsRemito(expediciones);

        var importe = BigDecimal.valueOf(0d);
        for (Expedicion e : expediciones)
            importe = importe.add(BigDecimal.valueOf(e.getImporte()));

        var remito = new Remito();
        remito.setExpediciones(expediciones);
        remito.setFecha(fecha);
        remito.setImporteTotal(importe.doubleValue());
        remito.setItemsRemito(items);

        return remito;
    }

    public RemitoDTO generateRemitoDTO(Long idCliente, LocalDate fecha) {
        return new RemitoDTO(generateRemito(idCliente, fecha));
    }

    public RemitoDTO generateAndSave(RemitoDTO dto, Long idCliente) {
        var remito = generateRemito(idCliente, dto.getFecha());
        remito.setCantCajas(dto.getCantCajas());
        remito.setCantPallets(dto.getCantPallets());
        if (remito.getExpediciones() == null || remito.getExpediciones().isEmpty())
            throw new ExpedicionNotFoundException();
        expedicionService.setOnRemitoTrue(remito.getExpediciones());
        return new RemitoDTO(repository.save(remito));
    }

    public void deleteRemito(Long id){
        var remito = getRemito(id);
        List<Long> idExpedicionesRemito = new ArrayList<>();
        remito.getExpediciones().forEach(expedicion -> idExpedicionesRemito.add(expedicion.getId()));
        repository.delete(remito);
        idExpedicionesRemito.forEach(idExp -> expedicionService.delete(idExp));
    }

    public byte[] getPdf(Long id) throws JRException, IOException {
        var remito = getRemito(id);

        var expediciones = remito.getExpediciones();
        var items = generateItemsRemito(expediciones);
        remito.setItemsRemito(items);

        var cliente = remito.getExpediciones().get(0).getCliente();
        var remitoParams = generateRemitoParams(cliente, remito);
        return generatePDF(remitoParams);
    }

    protected Remito getRemito(Long id) {
        var remito = repository.findById(id);
        if (remito.isEmpty())
            throw new RemitoNotFoundException();
        return remito.get();
    }

    private HashMap<String, Object> generateRemitoParams(Cliente cliente, Remito remito) {
        var remitoParams = new HashMap<String, Object>();
        var importeTotal = NumberFormat.getCurrencyInstance(new Locale("es", "AR")).format(remito.getImporteTotal());

        remitoParams.put("nroRemito", remito.getId());
        remitoParams.put("importeTotal", importeTotal);
        remitoParams.put("nombreCliente", cliente.getRazonSocial());
        remitoParams.put("domicilio", cliente.getDomicilio());
        remitoParams.put("localidad", cliente.getLocalidad() + ", " + cliente.getProvincia());
        remitoParams.put("cuit", cliente.getCuit());
        remitoParams.put("transporte", cliente.getTransporte());
        remitoParams.put("senasaUta", cliente.getSenasaUta());
        remitoParams.put("cajas", remito.getCantCajas());
        remitoParams.put("pallets", remito.getCantPallets());
        remitoParams.put("fecha", remito.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        remitoParams.put("ds", new JRBeanCollectionDataSource(loadItemsRemitoReportDTO(remito.getItemsRemito())));
        return remitoParams;
    }

    private List<ItemRemitoReportDTO> loadItemsRemitoReportDTO(List<ItemRemito> items) {
        var itemsList = new ArrayList<ItemRemitoReportDTO>();
        items.forEach(itemRemito -> itemsList.add(new ItemRemitoReportDTO(itemRemito)));
        return itemsList;
    }

    private byte[] generatePDF(HashMap<String, Object> remitoParams) throws JRException, IOException {
        var remitoTemplate = resourceLoader.getResource("classpath:Remito.jrxml");
//        var remitoTemplate = new ClassPathResource("/Remito.jrxml");
//        var jasperReport = JasperCompileManager.compileReport(remitoTemplate.getFile().getAbsolutePath());
        var jasperReport = JasperCompileManager.compileReport(remitoTemplate.getInputStream());
//        JasperReport jasperReport
//                = JasperCompileManager.compileReport(
//                "/Remito.jrxml");
        JRSaver.saveObject(jasperReport, "Remito.jasper");

        var empReport = JasperFillManager.fillReport(jasperReport, remitoParams, new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(empReport);
    }

    public List<Remito> getBetweenDates(LocalDate fechaDesde, LocalDate fechaHasta) {
        var remitos = repository.findAllByFechaBetween(fechaDesde, fechaHasta);
        remitos.forEach(remito -> {
            var expediciones = remito.getExpediciones();
            var items = generateItemsRemito(expediciones);
            remito.setItemsRemito(items);
        });
        return remitos;
    }

    public List<RemitoDTO> getBetweenDatesDTO(LocalDate fechaDesde,LocalDate fechaHasta){
        var remitos = getBetweenDates(fechaDesde,fechaHasta);
        ArrayList<RemitoDTO> dtos = new ArrayList<>();
        remitos.forEach(remito -> dtos.add(new RemitoDTO(remito)));
        return dtos;
    }

    private List<ItemRemito> generateItemsRemito(List<Expedicion> expediciones) {
        var mapItems = new HashMap<String, ItemRemito>();
        expediciones.forEach(expedicion -> {
            var quesoExpedicion = expedicion.getLote().getQueso();
            if (mapItems.containsKey(quesoExpedicion.getTipoQueso())) {
                var item = mapItems.get(quesoExpedicion.getTipoQueso());
                updateItemRemito(item, expedicion);
            } else {
                var newItem = new ItemRemito();
                newItem.setQueso(quesoExpedicion);
                newItem.setPrecio(precioService.getPrecioValue(quesoExpedicion, expedicion.getCliente().getTipoCliente()));
                newItem.setCantidad(expedicion.getCantidad());
                newItem.setImporte(expedicion.getImporte());
                newItem.setPeso(expedicion.getPeso());
                mapItems.put(quesoExpedicion.getTipoQueso(), newItem);
            }
        });
        return new ArrayList<>(mapItems.values());
    }

    private void updateItemRemito(ItemRemito itemRemito, Expedicion expedicion) {
        var quesoExpedicion = expedicion.getLote().getQueso();
        var quesoRemito = itemRemito.getQueso();
        if (quesoExpedicion.getTipoQueso().equals(quesoRemito.getTipoQueso())) {
            var cantidad = itemRemito.getCantidad() + expedicion.getCantidad();
            var peso = BigDecimal.valueOf(itemRemito.getPeso()).add(BigDecimal.valueOf(expedicion.getPeso()));
            var importe = BigDecimal.valueOf(itemRemito.getImporte()).add(BigDecimal.valueOf(expedicion.getImporte()));
            itemRemito.setCantidad(cantidad);
            itemRemito.setImporte(importe.doubleValue());
            itemRemito.setPeso(peso.doubleValue());
        }
    }
}
