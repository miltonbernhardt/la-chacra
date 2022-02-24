package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.PrecioDTO;
import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.exceptions.PrecioNotFoundException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundException;
import com.brikton.lachacra.repositories.PrecioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PrecioService {

    private final PrecioRepository precioRepository;
    private final QuesoService quesoService;
    private final TipoClienteService tipoClienteService;

    public PrecioService(PrecioRepository precioRepository, QuesoService quesoService, TipoClienteService tipoClienteService) {
        this.precioRepository = precioRepository;
        this.quesoService = quesoService;
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
        var queso = quesoService.getEntity(dto.getIdQueso());
        precio.setQueso(queso);
        precio.setPrecio(dto.getPrecio());
        precio.setId(dto.getId());
        return precio;
    }
}
