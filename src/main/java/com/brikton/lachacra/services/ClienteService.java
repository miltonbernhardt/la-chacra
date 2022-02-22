package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.TipoClienteDTO;
import com.brikton.lachacra.repositories.ClienteRepository;
import com.brikton.lachacra.repositories.TipoClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClienteService {

    private final ClienteRepository repository;
    private final TipoClienteRepository tipoRepository;

    public ClienteService(ClienteRepository repository, TipoClienteRepository tipoRepository) {
        this.repository = repository;
        this.tipoRepository = tipoRepository;
    }

    public List<ClienteDTO> getAll() {
        List<ClienteDTO> result = new ArrayList<>();
        repository.findAll().forEach(c -> result.add(new ClienteDTO(c)));
        return result;
    }

    public List<TipoClienteDTO> getAllTipoCliente(){
        List<TipoClienteDTO> result = new ArrayList<>();
        tipoRepository.findAll().forEach(t -> result.add(new TipoClienteDTO(t)));
        return result;
    }

}
