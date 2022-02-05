package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.DatabaseException;
import com.brikton.lachacra.exceptions.InvalidLoteException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.repositories.LoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class LoteService {

    private final LoteRepository repository;

    public LoteService(LoteRepository repository) {
        this.repository = repository;
    }

    public LoteDTO get(Long id) throws LoteNotFoundException {
        try {
            var lote = repository.getById(id);
            return new LoteDTO(lote);
        } catch (Exception e) {
            log.error("error {}", e.getMessage());
            throw new LoteNotFoundException();
        }
    }

    public LoteDTO save(LoteDTO dto) throws InvalidLoteException {
        try {
            var lote = repository.save(new Lote());
            return new LoteDTO(lote);
        } catch (Exception e) {
//            log.error("tipo de excepcion {}", e.);
            log.error("error {}", e.getMessage());
        }
        return new LoteDTO();
    }
}
