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
        repository.findAll().forEach(precio -> lista.add(new PrecioDTO(precio)));
        return lista;
    }

    public List<Long> getAllByQueso(Long idQueso) {
        return repository.findAllByIdQueso(idQueso);
    }

    public PrecioDTO save(PrecioDTO dto) throws PrecioNotFoundException {
        var precio = precioFromDTO(dto);
        precio = repository.save(precio);
        return new PrecioDTO(precio);
    }

    public PrecioDTO update(PrecioUpdateDTO dtoUpdate) throws PrecioNotFoundException {
        var dto = new PrecioDTO(dtoUpdate);
        var exists = repository.existsById(dto.getId());
        if (!exists)
            throw new PrecioNotFoundException();
        return save(dto);
    }

    public Long delete(Long id) throws PrecioNotFoundException {
        if (!repository.existsById(id))
            throw new PrecioNotFoundException();
        repository.deleteById(id);
        return id;
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
        precio.setPrecio(dto.getPrecio());
        precio.setId(dto.getId());
        return precio;
    }
}
