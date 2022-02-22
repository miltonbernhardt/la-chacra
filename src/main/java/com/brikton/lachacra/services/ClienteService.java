package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.repositories.ClienteRepository;
import com.brikton.lachacra.repositories.LoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<ClienteDTO> getAll() {
        List<ClienteDTO> result = new ArrayList<>();
        repository.findAll().forEach(c -> result.add(new ClienteDTO(c)));
        return result;
    }


}
