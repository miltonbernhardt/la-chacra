package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.PrecioDTO;
import com.brikton.lachacra.dtos.PrecioUpdateDTO;
import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.exceptions.PrecioAlreadyExistsException;
import com.brikton.lachacra.exceptions.PrecioNotFoundException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundException;
import com.brikton.lachacra.repositories.PrecioRepository;
import com.brikton.lachacra.repositories.QuesoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PrecioService {

    private final PrecioRepository precioRepository;
    private final QuesoRepository quesoRepository; // circular reference if use quesoService
    private final TipoClienteService tipoClienteService;

    public PrecioService(PrecioRepository precioRepository,  QuesoRepository quesoRepository, TipoClienteService tipoClienteService) {
        this.precioRepository = precioRepository;
        this.quesoRepository = quesoRepository;
        this.tipoClienteService = tipoClienteService;
    }

    public List<PrecioDTO> getAll() {
        List<PrecioDTO> lista = new ArrayList<>();
        precioRepository.findAll().forEach(precio -> {lista.add(new PrecioDTO(precio));});
        return lista;
    }

    public List<Long> getAllByQueso(Long idQueso) {
        return precioRepository.findAllByIdQueso(idQueso);
    }

    public PrecioDTO save(PrecioDTO dto) throws TipoClienteNotFoundException, QuesoNotFoundException, PrecioAlreadyExistsException {
        var precio = precioFromDTO(dto);
        // precio is unique by queso and tipoCliente
        if (precioRepository.existsByQuesoAndTipoCliente(precio.getQueso(),precio.getTipoCliente()))
            throw new PrecioAlreadyExistsException();
        return new PrecioDTO(precioRepository.save(precio));
    }

    public PrecioDTO update(PrecioUpdateDTO dto) throws PrecioNotFoundException, TipoClienteNotFoundException, QuesoNotFoundException {
        var precio = precioRepository.findById(dto.getId());
        if (precio.isEmpty())  throw new PrecioNotFoundException();
        var precioUpdate = precioFromDTOUpdate(dto);
        // queso and tipoCliente aren't updatable
        if (precio.get().getQueso().getId() != precioUpdate.getQueso().getId() ||
        precio.get().getTipoCliente().getId() != precioUpdate.getTipoCliente().getId())
            // it's the same as bad ID as queso and cliente don't correspond
            throw new PrecioNotFoundException();
        return new PrecioDTO(precioRepository.save(precioUpdate));
    }

    public Long delete(Long id) throws PrecioNotFoundException {
        if (!precioRepository.existsById(id))
            throw new PrecioNotFoundException();
        precioRepository.deleteById(id);
        return id;
    }

    private Precio precioFromDTO(PrecioDTO dto) throws QuesoNotFoundException, TipoClienteNotFoundException {
        Precio precio = new Precio();
        var tipoCliente = tipoClienteService.getEntity(dto.getIdTipoCliente());
        precio.setTipoCliente(tipoCliente);
        var queso = quesoRepository.findById(dto.getIdQueso());
        if (queso.isEmpty()) throw new QuesoNotFoundException();
        precio.setQueso(queso.get());
        precio.setPrecio(dto.getPrecio());
        precio.setId(dto.getId());
        return precio;
    }

    private Precio precioFromDTOUpdate(PrecioUpdateDTO dto) throws QuesoNotFoundException, TipoClienteNotFoundException {
        Precio precio = new Precio();
        var tipoCliente = tipoClienteService.getEntity(dto.getIdTipoCliente());
        precio.setTipoCliente(tipoCliente);
        var queso = quesoRepository.findById(dto.getIdQueso());
        if (queso.isEmpty()) throw new QuesoNotFoundException();
        precio.setQueso(queso.get());
        precio.setPrecio(dto.getPrecio());
        precio.setId(dto.getId());
        return precio;
    }
}
