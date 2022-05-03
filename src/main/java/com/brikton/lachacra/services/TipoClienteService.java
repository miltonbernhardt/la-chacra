package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.TipoClienteDTO;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundException;
import com.brikton.lachacra.repositories.TipoClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TipoClienteService {

    private final TipoClienteRepository repository;

    public TipoClienteService(TipoClienteRepository repository) {
        this.repository = repository;
    }

    public TipoCliente get(Long id) throws TipoClienteNotFoundException {
        var tipoCliente = repository.findById(id);
        if (tipoCliente.isEmpty())
            throw new TipoClienteNotFoundException();
        return tipoCliente.get();
    }

    public List<TipoClienteDTO> getAll() {
        List<TipoClienteDTO> result = new ArrayList<>();
        repository.findAll().forEach(t -> result.add(new TipoClienteDTO(t)));
        return result;
    }
}
