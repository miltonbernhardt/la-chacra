package com.brikton.lachacra.services;

import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.repositories.DevolucionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DevolucionService {

    private final DevolucionRepository devolucionRepository;

    public DevolucionService(DevolucionRepository devolucionRepository) {
        this.devolucionRepository = devolucionRepository;
    }

    public boolean existsByLote(Lote lote){
        return devolucionRepository.existsByLote(lote);
    }
}
