package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.exceptions.DatabaseException;
import com.brikton.lachacra.exceptions.InvalidLote;
import com.brikton.lachacra.repositories.LoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoteService {

    private final LoteRepository repository;

    public LoteService(LoteRepository repository) {
        this.repository = repository;
    }

    public Lote get(Long id) throws DatabaseException {
        return repository.getById(id);
    }

    public LoteDTO save(LoteDTO dto) throws DatabaseException, InvalidLote {
        validLote();
        var lote = repository.save(new Lote());
        return loteToDTO(lote);
    }

    public LoteDTO update(LoteDTO dto) throws DatabaseException {
        var lote = repository.getById(dto.getId());
        setUpdatedFields(dto, lote);
        repository.save(lote); //todo do not return entity
        return dto;
    }

    private void setUpdatedFields(LoteDTO dto, Lote lote) {

    }

    private LoteDTO loteToDTO(Lote lote) {
        return new LoteDTO();
    }

    private void validLote() throws InvalidLote {

    }
}
