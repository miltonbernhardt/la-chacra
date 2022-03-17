package com.brikton.lachacra.services;

import com.brikton.lachacra.entities.ItemRemito;
import com.brikton.lachacra.entities.Remito;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.addAll;

@Service
@Slf4j
public class RemitoService {

    private final PrecioService precioService;

    public RemitoService(PrecioService precioService) {
        this.precioService = precioService;
    }

    public void generateItemsRemito(Remito remito) {
        var expediciones = remito.getExpediciones();
        Map<String, ItemRemito> mapItems = new HashMap<>();
        expediciones.forEach(e -> {
            if (!mapItems.containsKey(e.getLote().getQueso().getTipoQueso())) {
                var newItem = new ItemRemito();
                newItem.setQueso(e.getLote().getQueso());
                newItem.setPrecio(precioService.getPrecioValue(e.getLote().getQueso(),e.getCliente().getTipoCliente()));
                newItem.setCantidad(e.getCantidad());
                newItem.setImporte(e.getImporte());
                newItem.setPeso(e.getPeso());
                mapItems.putIfAbsent(e.getLote().getQueso().getTipoQueso(),newItem);
            } else {
                var item = mapItems.get(e.getLote().getQueso().getTipoQueso());
                item.update(e);
            }
        });
        List<ItemRemito> items = new ArrayList<>();
        items.addAll(mapItems.values());
        remito.setItemsRemito(items);
    }
}
