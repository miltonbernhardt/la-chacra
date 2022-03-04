package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.ClienteUpdateDTO;
import com.brikton.lachacra.entities.Cliente;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.ClienteNotFoundException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundConflictException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundException;
import com.brikton.lachacra.repositories.ClienteRepository;
import com.brikton.lachacra.repositories.ExpedicionRepository;
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
    private final ExpedicionRepository expedicionRepository;
    private final TipoClienteService tipoClienteService;

    public ClienteService(
            DateUtil dateUtil,
            ClienteRepository repository,
            ExpedicionRepository expedicionRepository,
            TipoClienteService tipoClienteService
    ) {
        this.dateUtil = dateUtil;
        this.repository = repository;
        this.expedicionRepository = expedicionRepository;
        this.tipoClienteService = tipoClienteService;
    }

    public Cliente get(Long id) throws ClienteNotFoundException {
        var cliente = repository.findById(id);
        if (cliente.isPresent() && cliente.get().getFechaBaja() == null)
            return cliente.get();
        throw new ClienteNotFoundException();
    }

    public List<ClienteDTO> getAll() {
        List<ClienteDTO> result = new ArrayList<>();
        repository.findAllClientesNotDadoBaja().forEach(cliente -> result.add(new ClienteDTO(cliente)));
        return result;
    }

    public ClienteDTO save(ClienteDTO dto) throws TipoClienteNotFoundConflictException {
        var cliente = clienteFromDTO(dto);
        cliente = repository.save(cliente);
        return new ClienteDTO(cliente);
    }

    private Cliente clienteFromDTO(ClienteDTO dto) throws TipoClienteNotFoundConflictException {
        var cliente = new Cliente();

        TipoCliente tipoCliente;
        try {
            tipoCliente = tipoClienteService.get(dto.getIdTipoCliente());
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

    public ClienteDTO update(ClienteUpdateDTO dtoUpdate) throws ClienteNotFoundException, TipoClienteNotFoundConflictException {
        var dto = new ClienteDTO(dtoUpdate);
        existsCliente(dto.getId());
        return save(dto);
    }

    public void delete(Long id) throws ClienteNotFoundException {
        existsCliente(id);
        if (expedicionRepository.existsByIdCliente(id))
            darBajaCliente(id);
        else
            repository.deleteById(id);
    }

    private void existsCliente(Long id) {
        var exists = repository.existsByIdAndNotDadoBaja(id);
        if (!exists)
            throw new ClienteNotFoundException();
    }

    private void darBajaCliente(Long id) {
        var cliente = repository.getById(id);
        cliente.setFechaBaja(dateUtil.now());
        repository.save(cliente);
    }
}
