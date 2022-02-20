package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.dtos.QuesoUpdateDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.CodigoQuesoAlreadyExistsException;
import com.brikton.lachacra.exceptions.NomQuesoAlreadyExistsException;
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

    public Queso getEntity(Long id) throws QuesoNotFoundException {
        var queso = repository.findById(id);
        if (queso.isPresent() && queso.get().getFechaBaja() == null)
            return queso.get();
        throw new QuesoNotFoundException();
    }

    public Queso getEntity(String codigo) throws QuesoNotFoundException {
        var queso = repository.findByCodigo(codigo);
        if (queso.isPresent() && queso.get().getFechaBaja() == null)
            return queso.get();
        throw new QuesoNotFoundException();
    }

    public List<QuesoDTO> getAll() {
        var listaDTO = new ArrayList<QuesoDTO>();
        repository.findAllQuesos().forEach(queso -> listaDTO.add(new QuesoDTO(queso)));
        return listaDTO;
    }

    public QuesoDTO save(QuesoDTO dto) throws CodigoQuesoAlreadyExistsException, NomQuesoAlreadyExistsException {
        if (repository.existsQuesoByCodigo(dto.getCodigo())) {
            throw new CodigoQuesoAlreadyExistsException();
        }

        if (repository.existsQuesoByNomenclatura(dto.getNomenclatura())) {
            throw new NomQuesoAlreadyExistsException();
        }

        var queso = quesoFromDTO(dto);
        queso = repository.save(queso);
        return new QuesoDTO(queso);
    }

    public QuesoDTO update(QuesoUpdateDTO dto) throws QuesoNotFoundException, CodigoQuesoAlreadyExistsException, NomQuesoAlreadyExistsException {
        var oldQuesoOptional = repository.findById(dto.getId());
        if (oldQuesoOptional.isEmpty())
            throw new QuesoNotFoundException();

        var oldQueso= oldQuesoOptional.get();


        if (!oldQueso.getCodigo().equals(dto.getCodigo()) && repository.existsQuesoByCodigo(dto.getCodigo())) {
            throw new CodigoQuesoAlreadyExistsException();
        }

        if (!oldQueso.getNomenclatura().equals(dto.getNomenclatura()) && repository.existsQuesoByNomenclatura(dto.getNomenclatura())) {
            throw new NomQuesoAlreadyExistsException();
        }

        oldQueso.setCodigo(dto.getCodigo());
        oldQueso.setNomenclatura(dto.getNomenclatura());
        oldQueso.setTipoQueso(dto.getTipoQueso());
        //todo que hacemos con el stock? para mi, no deberia actualizar porque se calcula a partir de expediciones y lotes
        var queso = repository.save(oldQueso);
        return new QuesoDTO(queso);
    }

    //todo las dependencias son de Lote, (Precio se borra tambien)
    public String delete(Long id) throws QuesoNotFoundException {
        var queso = getEntity(id);//TODO hacer la query para verificar los lotes - borrar posta
        queso.setFechaBaja(dateUtil.now());
        repository.save(queso);
        return queso.getCodigo();
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
