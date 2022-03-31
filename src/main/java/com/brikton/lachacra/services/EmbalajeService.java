package com.brikton.lachacra.services;

import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoEmbalaje;
import com.brikton.lachacra.repositories.EmbalajeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmbalajeService {

    public final EmbalajeRepository repository;

    public EmbalajeService(EmbalajeRepository repository) {
        this.repository = repository;
    }

    public void decreaseStockCaja(Integer cantidad, Queso queso){
        updateStockEmbalaje(TipoEmbalaje.CAJA,queso,cantidad,false);
    }

    public void increaseStockCaja(Integer cantidad, Queso queso){
        updateStockEmbalaje(TipoEmbalaje.CAJA,queso,cantidad,true);
    }

    public void decreaseStockBolsa(Integer cantidad, Queso queso){
        updateStockEmbalaje(TipoEmbalaje.BOLSA,queso,cantidad,false);
    }

    public void increaseStockBolsa(Integer cantidad, Queso queso){
        updateStockEmbalaje(TipoEmbalaje.BOLSA,queso,cantidad,true);
    }

    /**
     *
     * @param tipoEmbalaje
     * @param queso
     * @param cantidad
     * @param increment if true it will increment the stock, else will decrement
     */
    private void updateStockEmbalaje(TipoEmbalaje tipoEmbalaje, Queso queso,Integer cantidad, Boolean increment){
        var embalaje = repository
                .findByTipoEmbalajeAndListaQuesosContains(tipoEmbalaje, queso)
                .get(0);
        var stock = embalaje.getStock();
        if (increment) stock += cantidad;
        else stock -= cantidad;
        embalaje.setStock(stock);
        repository.save(embalaje);
    }
}
