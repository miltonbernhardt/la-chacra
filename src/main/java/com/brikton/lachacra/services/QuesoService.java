package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.dtos.QuesoUpdateDTO;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.CodigoQuesoAlreadyExistsException;
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

    public Queso get(Long id) throws QuesoNotFoundException {
        var queso = repository.findById(id);
        if (queso.isPresent() && queso.get().getFechaBaja() == null)
            return queso.get();
        throw new QuesoNotFoundException();
    }

    public Queso getByCodigo(String codigo) throws QuesoNotFoundException {
        var queso = repository.findByCodigo(codigo);
        if (queso.isPresent() && queso.get().getFechaBaja() == null)
            return queso.get();
        throw new QuesoNotFoundException();
    }

    public List<QuesoDTO> getAll() {
        var listaDTO = new ArrayList<QuesoDTO>();
        repository.findAllQuesosNotFechaBaja().forEach(queso -> listaDTO.add(new QuesoDTO(queso)));
        return listaDTO;
    }

    public QuesoDTO save(QuesoDTO dto) throws CodigoQuesoAlreadyExistsException {
        if (repository.existsByCodigoNotFechaBaja(dto.getCodigo()))
            throw new CodigoQuesoAlreadyExistsException();

        var queso = quesoFromDTO(dto);
        queso = repository.save(queso);
        return new QuesoDTO(queso);
    }

    private Queso quesoFromDTO(QuesoDTO dto) {
        var queso = new Queso();
        queso.setTipoQueso(dto.getTipoQueso());
        queso.setCodigo(dto.getCodigo());
        queso.setNomenclatura(dto.getNomenclatura());
        queso.setStock(dto.getStock());
        queso.setColor(dto.getColor());
        return queso;
    }

    public QuesoDTO update(QuesoUpdateDTO dto) throws QuesoNotFoundException {
        var oldQueso = get(dto.getId());
        oldQueso.setNomenclatura(dto.getNomenclatura());
        oldQueso.setTipoQueso(dto.getTipoQueso());
        oldQueso.setColor(dto.getColor());
        var queso = repository.save(oldQueso);
        return new QuesoDTO(queso);
    }

    public void delete(Long id) throws QuesoNotFoundException {
        var queso = get(id);

        precioService.deletePreciosByQueso(queso.getId());

        if (loteRepository.existsByQueso(queso))
            darBajaQueso(queso);
        else
            repository.delete(queso);
    }

    private void darBajaQueso(Queso queso) {
        queso.setFechaBaja(dateUtil.now());
        repository.save(queso);
    }

    public void increaseStock(Queso queso, Integer cantidad) {
        var stock = queso.getStock() + cantidad;
        queso.setStock(stock);
        repository.save(queso);
    }

    public void decreaseStock(Queso queso, Integer cantidad) {
        var stock = queso.getStock() - cantidad;
        queso.setStock(stock);
        repository.save(queso);
    }
}
