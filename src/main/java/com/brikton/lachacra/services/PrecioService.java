package com.brikton.lachacra.services;

import com.brikton.lachacra.exceptions.PrecioNotFoundException;
import com.brikton.lachacra.repositories.PrecioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PrecioService {

    private final PrecioRepository precioRepository;

    public PrecioService(PrecioRepository precioRepository) {
        this.precioRepository = precioRepository;
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
}
