package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.TipoClienteDTO;
import com.brikton.lachacra.entities.Cliente;
import com.brikton.lachacra.exceptions.ClienteAlreadyExistsException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundException;
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

    public ClienteDTO save(ClienteDTO dto) throws TipoClienteNotFoundException, ClienteAlreadyExistsException {
        if ((dto.getId() != null) && repository.existsById(dto.getId())) {
            throw new ClienteAlreadyExistsException();
        }
        return persist(dto);
    }

    private ClienteDTO persist(ClienteDTO dto) throws TipoClienteNotFoundException {
        var cliente = clienteFromDTO(dto);
        return new ClienteDTO(repository.save(cliente));
    }

    private Cliente clienteFromDTO(ClienteDTO dto) throws TipoClienteNotFoundException {
        Cliente c = new Cliente();

        var tipoCliente = tipoRepository.findById(dto.getIdTipoCliente());
        if (tipoCliente.isEmpty()) {
            throw new TipoClienteNotFoundException();
        }
        c.setTipoCliente(tipoCliente.get());

        c.setId(dto.getId());
        c.setCuit(dto.getCuit());
        c.setCodPostal(dto.getCodPostal());
        c.setDomicilio(dto.getDomicilio());
        c.setLocalidad(dto.getLocalidad());
        c.setPais(dto.getPais());
        c.setProvincia(dto.getProvincia());
        c.setTransporte(dto.getTransporte());
        c.setSenasaUta(dto.getSenasaUta());
        c.setTelefono(dto.getTelefono());
        c.setCelular(dto.getCelular());
        c.setFax(dto.getFax());
        c.setEmail(dto.getEmail());

        return c;
    }
}
