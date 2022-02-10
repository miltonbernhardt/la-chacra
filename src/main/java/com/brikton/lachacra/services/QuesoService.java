package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.QuesoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class QuesoService {

    private final QuesoRepository repository;

    public QuesoService(QuesoRepository repository) {
        this.repository = repository;
    }

    public Queso get(Long codigoQueso) throws QuesoNotFoundException {
        var queso = repository.findById(codigoQueso);

        if (queso.isPresent()) {
            return queso.get();
        }

        throw new QuesoNotFoundException();
    }

    public List<QuesoDTO> getAll() {
        ArrayList<QuesoDTO> listaDTO = new ArrayList<QuesoDTO>();
        repository.findAll().forEach((q) -> listaDTO.add(new QuesoDTO(q)));
        return listaDTO;
    }


}
