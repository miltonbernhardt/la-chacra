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

    public Queso getEntity(String codigoQueso) throws QuesoNotFoundException {
        var queso = repository.findById(codigoQueso);
        if (queso.isPresent())
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

    public String delete(String id) throws QuesoNotFoundException {
        var queso = repository.findById(id);
        if (queso.isPresent()) {
            queso.get().setFechaBaja(dateUtil.now());
            repository.save(queso.get());
        } else
            throw new QuesoNotFoundException();
        return id;
    }
}
