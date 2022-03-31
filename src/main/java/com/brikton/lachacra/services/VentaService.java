package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ventas.VentaDiaDTO;
import com.brikton.lachacra.entities.Remito;
import com.brikton.lachacra.entities.Venta;
import com.brikton.lachacra.entities.VentaDia;
import com.brikton.lachacra.util.DateComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class VentaService {

    private final RemitoService remitoService;

    public VentaService(RemitoService remitoService) {
        this.remitoService = remitoService;
    }

    public List<VentaDiaDTO> getVentas(LocalDate fechaDesde, LocalDate fechaHasta) {
        var remitos = remitoService.getBetweenDates(fechaDesde,fechaHasta);
        Map<LocalDate, VentaDia> ventas = new HashMap<>();
        remitos.forEach(remito -> {
            var fecha = remito.getFecha();
            if (ventas.containsKey(fecha)) {
                updateVenta(ventas.get(fecha),remito);
            } else {
                var venta = new VentaDia();
                venta.setFecha(fecha);
                venta.setTotal(0);
                updateVenta(venta,remito);
                ventas.put(fecha,venta);
            }
        });

        List<VentaDia> listaVentas = new ArrayList<>();
        listaVentas.addAll(ventas.values());

        List<VentaDiaDTO> dtos = new ArrayList<>();
        listaVentas.forEach(venta -> dtos.add(new VentaDiaDTO(venta)));
        Collections.sort(dtos,new DateComparator());

        return dtos;
    }

    private VentaDia updateVenta(VentaDia venta, Remito remito){
        var items = remito.getItemsRemito();
        var ventas = venta.getVentas();
        items.forEach(item -> {
            var codigoQueso = item.getQueso().getCodigo();
            var cantidad = item.getCantidad();

            if (ventas.containsKey(codigoQueso)){
                var aux = ventas.get(codigoQueso);
                cantidad += aux.getCantidad();
                aux.setCantidad(cantidad);
            } else {
                var aux = new Venta();
                aux.setCodigoQueso(codigoQueso);
                aux.setCantidad(cantidad);
                ventas.put(codigoQueso,aux);
            }

            var total = venta.getTotal();
            total += cantidad;
            venta.setTotal(total);
        });
        return venta;
    }
}
