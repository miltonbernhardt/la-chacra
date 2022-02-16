package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.QuesoRepository;
import com.brikton.lachacra.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class QuesoService {

    private final DateUtil dateUtil;
    private final QuesoRepository repository;

    public QuesoService(DateUtil dateUtil, QuesoRepository repository) {
        this.dateUtil = dateUtil;
        this.repository = repository;
    }

    public Queso getEntity(String codigo) throws QuesoNotFoundException {
        var queso = repository.findById(codigo);
        if (queso.isPresent() && queso.get().getFechaBaja() == null) //todo usamos la fecha de baja aca? debemos retornar un mensaje de que el queso se elimino?
            return queso.get();
        throw new QuesoNotFoundException();
    }

    public QuesoDTO get(String codigoQueso) throws QuesoNotFoundException {
        return new QuesoDTO(this.getEntity(codigoQueso));
    }

    public List<QuesoDTO> getAll() {
        //todo get quesos no dados de baja
        var listaDTO = new ArrayList<QuesoDTO>();
        repository.findAll().forEach(queso -> listaDTO.add(new QuesoDTO(queso)));
        return listaDTO;
    }

    public String delete(String codigo) throws QuesoNotFoundException {
        var queso = getEntity(codigo);
        queso.setFechaBaja(dateUtil.now());
        repository.save(queso);
        return codigo;
    }
}
