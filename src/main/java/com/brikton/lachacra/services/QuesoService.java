package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.NotFoundConflictException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.QuesoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class QuesoService {

    private final QuesoRepository repository;

    public QuesoService(QuesoRepository repository) {
        this.repository = repository;
    }

    public Queso get(Long codigoQueso) throws QuesoNotFoundException {
        var queso = repository.findById(codigoQueso);
        if (queso.isPresent())
            return queso.get();
        throw new QuesoNotFoundException();
    }

    public List<QuesoDTO> getAll() {
        var listaDTO = new ArrayList<QuesoDTO>();
        repository.findAll().forEach(queso -> listaDTO.add(new QuesoDTO(queso)));
        return listaDTO;
    }

    public QuesoDTO save(QuesoDTO dto) {
        var queso = fromDto(dto);
        queso = repository.save(queso);
        return new QuesoDTO(queso);
    }

    public QuesoDTO update(QuesoDTO dto) throws QuesoNotFoundException {
        var queso = repository.findById(dto.getId());
        if (queso.isPresent()){
            var updateQueso = fromDto(dto);
            var updated = repository.save(updateQueso);
            return new QuesoDTO(updated);
        } else throw new QuesoNotFoundException();
    }

    private Queso fromDto (QuesoDTO dto){
        Queso queso = new Queso();
        queso.setCodigo(dto.getCodigo());
        queso.setNomenclatura(dto.getNomenclatura());
        queso.setStock(dto.getStock());
        queso.setId(dto.getId());
        queso.setTipoQueso(dto.getTipoQueso());
        return queso;
    }

}
