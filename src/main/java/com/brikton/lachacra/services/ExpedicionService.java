package com.brikton.lachacra.services;

import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.repositories.ExpedicionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExpedicionService {

    private final ExpedicionRepository expedicionRepository;

    public ExpedicionService(ExpedicionRepository expedicionRepository) {
        this.expedicionRepository = expedicionRepository;
    }

    public boolean existsByLote(Lote lote){
        return expedicionRepository.existsByLote(lote);
    }
}
