package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.EmbalajeDTO;
import com.brikton.lachacra.dtos.EmbalajeUpdateDTO;
import com.brikton.lachacra.entities.Embalaje;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoEmbalaje;
import com.brikton.lachacra.exceptions.EmbalajeAlreadyExistsException;
import com.brikton.lachacra.exceptions.EmbalajeNotFoundException;
import com.brikton.lachacra.repositories.EmbalajeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmbalajeService {

    public final EmbalajeRepository repository;
    public final QuesoService quesoService;

    public EmbalajeService(EmbalajeRepository repository, QuesoService quesoService) {
        this.repository = repository;
        this.quesoService = quesoService;
    }

    public List<EmbalajeDTO> getAll(){
        var embalajes = repository.findAll();
        List<EmbalajeDTO> dtos = new ArrayList<>();
        embalajes.forEach(embalaje -> dtos.add(new EmbalajeDTO(embalaje)));
        return dtos;
    }

    public EmbalajeDTO saveNew(EmbalajeDTO dto){
        if (dto.getId() != null && repository.existsById(dto.getId())) {
            throw new EmbalajeAlreadyExistsException();
        }
        var result = persist(embalajeFromDTO(dto));
        return new EmbalajeDTO(result);
    }

    public EmbalajeDTO update(EmbalajeUpdateDTO updateDTO){
        var dto = new EmbalajeDTO(updateDTO);
        var embalaje = repository.findById(dto.getId());
        if (embalaje.isPresent()) {
            //TODO do not delete if already exists any relationship between tiposQuesos and tipoEmbalaje in another entity
            //WRONG
            //TODO
            repository.delete(embalaje.get());
        } else throw new EmbalajeNotFoundException();
        var result = persist(embalajeFromDTO(dto));
        return new EmbalajeDTO(result);
    }

    private Embalaje persist(Embalaje embalaje){
        embalaje.getListaQuesos().forEach(queso ->
        {
            //TODO check if already exists any relationship between tiposQuesos and tipoEmbalaje in another entity
            if (repository.existsByTipoEmbalajeAndListaQuesosContains(embalaje.getTipoEmbalaje(), queso))
                throw new EmbalajeAlreadyExistsException();
        });
        return repository.save(embalaje);
    }

    public void delete(Long id){
        var embalaje = repository.findById(id);
        if (embalaje.isPresent()) {
            repository.delete(embalaje.get());
        } else throw new EmbalajeNotFoundException();
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

    private Embalaje embalajeFromDTO(EmbalajeDTO dto){
        Embalaje embalaje = new Embalaje();
        embalaje.setStock(dto.getStock());
        if (dto.getId()!=null)
            embalaje.setId(dto.getId());
        embalaje.setTipoEmbalaje(TipoEmbalaje.valueOf(dto.getTipoEmbalaje()));
        var quesosDTO = dto.getListaQuesos();
        var quesos = new ArrayList<Queso>();
        quesosDTO.forEach(queso -> quesos.add(quesoService.getByCodigo(queso.getCodigo())));
        embalaje.setListaQuesos(quesos);
        return embalaje;
    }
}
