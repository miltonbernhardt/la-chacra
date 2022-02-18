package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.QuesoAlreadyExistsException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.QuesoRepository;
import com.brikton.lachacra.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class QuesoService {

    private final DateUtil dateUtil;
    private final QuesoRepository repository;

    public QuesoService(DateUtil dateUtil, QuesoRepository repository) {
        this.dateUtil = dateUtil;
        this.repository = repository;
    }

    public Queso getEntity(String codigo) throws QuesoNotFoundException {
        var queso = repository.findById(codigo);
        if (queso.isPresent() && queso.get().getFechaBaja() == null)
            return queso.get();
        throw new QuesoNotFoundException();
    }

    public QuesoDTO get(String codigoQueso) throws QuesoNotFoundException {
        return new QuesoDTO(this.getEntity(codigoQueso));
    }

    public List<QuesoDTO> getAll() {
        var listaDTO = new ArrayList<QuesoDTO>();
        repository.findALLQuesos().forEach(queso -> listaDTO.add(new QuesoDTO(queso)));
        return listaDTO;
    }

    public QuesoDTO save(QuesoDTO dto) throws QuesoAlreadyExistsException {
        if (repository.existsById(dto.getCodigo())) {
            throw new QuesoAlreadyExistsException();
        }
        var queso = quesoFromDTO(dto);
        queso = repository.save(queso);
        return new QuesoDTO(queso);
    }

    public QuesoDTO update(QuesoDTO dto) throws QuesoNotFoundException {
        if (!repository.existsById(dto.getCodigo())) {
            throw new QuesoNotFoundException();
        }
        var queso = quesoFromDTO(dto);
        var oldQueso = repository.getById(dto.getCodigo());
        if (oldQueso.getFechaBaja() != null) {
            queso.setFechaBaja(oldQueso.getFechaBaja());
        }
        queso = repository.save(queso);
        return new QuesoDTO(queso);
    }

    public String delete(String codigo) throws QuesoNotFoundException {
        var queso = getEntity(codigo);//TODO hacer la query para verificar los lotes
        queso.setFechaBaja(dateUtil.now());
        repository.save(queso);
        return codigo;
    }

    private Queso quesoFromDTO(QuesoDTO dto) {
        var queso = new Queso();
        queso.setTipoQueso(dto.getTipoQueso());
        queso.setCodigo(dto.getCodigo());
        queso.setNomenclatura(dto.getNomenclatura());
        queso.setStock(dto.getStock());
        return queso;
    }


}
