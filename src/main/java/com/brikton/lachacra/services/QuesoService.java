package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.dtos.QuesoUpdateDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.CodigoQuesoAlreadyExistsException;
import com.brikton.lachacra.exceptions.PrecioNotFoundException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.LoteRepository;
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
    private final LoteRepository loteRepository;
    private final PrecioService precioService;

    public QuesoService(
            DateUtil dateUtil,
            QuesoRepository repository,
            LoteRepository loteRepository,
            PrecioService precioService
    ) {
        this.dateUtil = dateUtil;
        this.repository = repository;
        this.loteRepository = loteRepository;
        this.precioService = precioService;
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

    public QuesoDTO save(QuesoDTO dto) throws CodigoQuesoAlreadyExistsException {
        if (repository.existsByCodigo(dto.getCodigo())) {
            throw new CodigoQuesoAlreadyExistsException();
        }

        var queso = quesoFromDTO(dto);
        queso = repository.save(queso);
        return new QuesoDTO(queso);
    }

    public QuesoDTO update(QuesoUpdateDTO dto) throws QuesoNotFoundException, CodigoQuesoAlreadyExistsException {
        var oldQuesoOptional = repository.findById(dto.getId());
        if (oldQuesoOptional.isEmpty())
            throw new QuesoNotFoundException();

        var oldQueso = oldQuesoOptional.get();

        if (!oldQueso.getCodigo().equals(dto.getCodigo()) && repository.existsByCodigo(dto.getCodigo())) {
            throw new CodigoQuesoAlreadyExistsException();
        }

        oldQueso.setCodigo(dto.getCodigo());
        oldQueso.setNomenclatura(dto.getNomenclatura());
        oldQueso.setTipoQueso(dto.getTipoQueso());
        //todo que hacemos con el stock? para mi, no deberia actualizar porque se calcula a partir de expediciones y lotes
        var queso = repository.save(oldQueso);
        return new QuesoDTO(queso);
    }

    public String delete(Long id) throws QuesoNotFoundException {
        var queso = getEntity(id);

        if (loteRepository.existsByQueso(queso)) {
            queso.setFechaBaja(dateUtil.now());
            queso = repository.save(queso);
            return queso.getCodigo();
        }

        for (Long idPrecio : precioService.getAllByQueso(queso.getId())) {
            try {
                precioService.delete(idPrecio);
            } catch (PrecioNotFoundException ignored) {
            }
        }

        repository.delete(queso);
        return "";
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
