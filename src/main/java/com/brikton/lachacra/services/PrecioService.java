package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.PrecioDTO;
import com.brikton.lachacra.dtos.PrecioUpdateDTO;
import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.*;
import com.brikton.lachacra.repositories.PrecioRepository;
import com.brikton.lachacra.repositories.QuesoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PrecioService {

    private final PrecioRepository repository;
    private final QuesoRepository quesoRepository;
    private final TipoClienteService tipoClienteService;

    public PrecioService(
            PrecioRepository repository,
            QuesoRepository quesoRepository,
            TipoClienteService tipoClienteService
    ) {
        this.repository = repository;
        this.quesoRepository = quesoRepository;
        this.tipoClienteService = tipoClienteService;
    }

    public List<PrecioDTO> getAll() {
        List<PrecioDTO> lista = new ArrayList<>();
        repository.findAllByOrderByTipoClienteAscIdAsc().forEach(precio -> lista.add(new PrecioDTO(precio)));
        return lista;
    }

    public PrecioDTO save(PrecioDTO dto) throws PrecioAlreadyExistsException {
        var precio = precioFromDTO(dto);

        if (repository.existsByQuesoAndTipoCliente(precio.getQueso(), precio.getTipoCliente()))
            throw new PrecioAlreadyExistsException();

        precio = repository.save(precio);
        return new PrecioDTO(precio);
    }

    private Precio precioFromDTO(PrecioDTO dto) throws TipoClienteNotFoundConflictException, QuesoNotFoundConflictException {
        Precio precio = new Precio();

        TipoCliente tipoCliente;
        try {
            tipoCliente = tipoClienteService.getEntity(dto.getIdTipoCliente());
        } catch (TipoClienteNotFoundException e) {
            throw new TipoClienteNotFoundConflictException();
        }

        var queso = quesoRepository.findById(dto.getIdQueso());
        if (queso.isEmpty())
            throw new QuesoNotFoundConflictException();

        precio.setTipoCliente(tipoCliente);
        precio.setQueso(queso.get());
        precio.setValor(dto.getValor());
        precio.setId(dto.getId());
        return precio;
    }

    public PrecioDTO update(PrecioUpdateDTO dto) throws PrecioNotFoundException {
        if (!repository.existsByIdAndQuesoAndTipoCliente(dto.getId(), dto.getIdQueso(), dto.getIdTipoCliente()))
            throw new PrecioNotFoundException();

        var precio = precioFromDTO(dto);
        precio = repository.save(precio);
        return new PrecioDTO(precio);
    }


    private Precio precioFromDTO(PrecioUpdateDTO dto) {
        var precio = new Precio();

        var tipoCliente = tipoClienteService.getEntity(dto.getIdTipoCliente());
        var queso = quesoRepository.getById(dto.getIdQueso());

        precio.setTipoCliente(tipoCliente);
        precio.setQueso(queso);
        precio.setValor(dto.getValor());
        precio.setId(dto.getId());
        return precio;
    }

    public void deletePreciosByQueso(Long idQueso) {
        var allPrecioIDs = repository.findAllByIdQueso(idQueso);
        repository.deleteAllById(allPrecioIDs);
    }
}
