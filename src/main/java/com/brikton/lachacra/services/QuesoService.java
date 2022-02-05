package com.brikton.lachacra.services;

import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.repositories.LoteRepository;
import com.brikton.lachacra.repositories.QuesoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuesoService {

    private final QuesoRepository repository;

    public QuesoService(QuesoRepository repository) {
        this.repository = repository;
    }

    public Queso getQueso(String codigoQueso) {
        return repository.getById(codigoQueso);
    }
}
