package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.ClienteUpdateDTO;
import com.brikton.lachacra.entities.Cliente;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.ClienteNotFoundException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundConflictException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundException;
import com.brikton.lachacra.repositories.ClienteRepository;
import com.brikton.lachacra.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClienteService {

    private final DateUtil dateUtil;
    private final ClienteRepository repository;
    private final ExpedicionService expedicionService;
    private final TipoClienteService tipoClienteService;

    public ClienteService(
            DateUtil dateUtil,
            ClienteRepository repository,
            ExpedicionService expedicionRepository,
            TipoClienteService tipoClienteService
    ) {
        this.dateUtil = dateUtil;
        this.repository = repository;
        this.expedicionService = expedicionRepository;
        this.tipoClienteService = tipoClienteService;
    }

    public List<ClienteDTO> getAll() {
        List<ClienteDTO> result = new ArrayList<>();
        repository.findAllClientesNotFechaBaja().forEach(c -> result.add(new ClienteDTO(c)));
        return result;
    }

    public ClienteDTO save(ClienteDTO dto) throws TipoClienteNotFoundConflictException {
        return persist(dto);
    }

    public ClienteDTO update(ClienteUpdateDTO dtoUpdate) throws ClienteNotFoundException, TipoClienteNotFoundConflictException {
        var dto = new ClienteDTO(dtoUpdate);
        var exists = repository.existsByIdNotFechaBaja(dto.getId());
        if (!exists)
            throw new ClienteNotFoundException();
        return persist(dto);
    }

    public Long delete(Long id) throws ClienteNotFoundException {
        var exists = repository.existsByIdNotFechaBaja(id);
        if (!exists)
            throw new ClienteNotFoundException();

        var cliente = repository.getById(id);

        if (expedicionService.existsByCliente(cliente)) {
            cliente.setFechaBaja(dateUtil.now());
            repository.save(cliente);
            return id;
        }

        repository.deleteById(id);
        return null;
    }

    private ClienteDTO persist(ClienteDTO dto) throws TipoClienteNotFoundConflictException {
        var cliente = clienteFromDTO(dto);
        return new ClienteDTO(repository.save(cliente));
    }

    private Cliente clienteFromDTO(ClienteDTO dto) throws TipoClienteNotFoundConflictException {
        var cliente = new Cliente();
        TipoCliente tipoCliente;
        try {
            tipoCliente = tipoClienteService.getEntity(dto.getIdTipoCliente());
        } catch (TipoClienteNotFoundException exception) {
            throw new TipoClienteNotFoundConflictException();
        }
        cliente.setTipoCliente(tipoCliente);
        cliente.setId(dto.getId());
        cliente.setCuit(dto.getCuit());
        cliente.setCodPostal(dto.getCodPostal());
        cliente.setDomicilio(dto.getDomicilio());
        cliente.setLocalidad(dto.getLocalidad());
        cliente.setPais(dto.getPais());
        cliente.setProvincia(dto.getProvincia());
        cliente.setTransporte(dto.getTransporte());
        cliente.setSenasaUta(dto.getSenasaUta());
        cliente.setTelefono(dto.getTelefono());
        cliente.setCelular(dto.getCelular());
        cliente.setFax(dto.getFax());
        cliente.setEmail(dto.getEmail());
        cliente.setRazonSocial(dto.getRazonSocial());
        return cliente;
    }
}
