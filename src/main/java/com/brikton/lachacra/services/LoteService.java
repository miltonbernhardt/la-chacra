package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.LoteUpdateDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.*;
import com.brikton.lachacra.repositories.LoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LoteService {

    private final LoteRepository repository;
    private final QuesoService quesoService;

    public LoteService(LoteRepository repository, QuesoService quesoService) {
        this.repository = repository;
        this.quesoService = quesoService;
    }

    public LoteDTO save(LoteDTO dto) throws QuesoNotFoundConflictException, LoteAlreadyExistsException {
        var id = generateID(dto);
        if (repository.existsById(id)) {
            throw new LoteAlreadyExistsException();
        }
        return persist(dto);
    }

    public LoteDTO update(LoteUpdateDTO dtpUpdate) throws QuesoNotFoundConflictException, LoteNotFoundException {
        var dto = new LoteDTO(dtpUpdate);
        var id = dto.getId() == null || dto.getId().equals("")
                ? generateID(dto)
                : dto.getId();
        if (!repository.existsById(id)) {
            throw new LoteNotFoundException();
        }
        repository.deleteById(dto.getId());
        return persist(dto);
    }

    private LoteDTO persist(LoteDTO dto) throws QuesoNotFoundConflictException {
        var lote = loteFromDTO(dto);
        var rendimiento = (dto.getPeso() / dto.getLitrosLeche()) * 100;
        var stock = dto.getCantHormas();
        lote.setRendimiento(rendimiento);
        lote.setStockLote(stock);
        lote = repository.save(lote);
        return new LoteDTO(lote);
    }

    public List<LoteDTO> getAll() {
        var lotesDTO = new ArrayList<LoteDTO>();
        var lotes = repository.findAll();
        lotes.forEach(lote -> lotesDTO.add(new LoteDTO(lote)));
        return lotesDTO;
    }

    //todo dependencias de expedicion,
    public String delete(String id) throws LoteNotFoundException {
        //todo logica de borrar o dar de baja
        if (repository.existsById(id))
            repository.deleteById(id);
        else
            throw new LoteNotFoundException();
        return id;
    }

    private Lote loteFromDTO(LoteDTO dto) throws QuesoNotFoundConflictException {
        Queso queso;
        try {
            queso = quesoService.getEntity(dto.getCodigoQueso());
        } catch (QuesoNotFoundException e) {
            throw new QuesoNotFoundConflictException(e.getCause());
        }

        var lote = new Lote();
        lote.setId(generateID(dto));
        lote.setQueso(queso);
        lote.setFechaElaboracion(dto.getFechaElaboracion());
        lote.setNumeroTina(dto.getNumeroTina());
        lote.setLitrosLeche(dto.getLitrosLeche());
        lote.setCantHormas(dto.getCantHormas());
        lote.setStockLote(dto.getStockLote());
        lote.setPeso(dto.getPeso());
        lote.setRendimiento(dto.getRendimiento());
        lote.setLoteCultivo(dto.getLoteCultivo());
        lote.setLoteColorante(dto.getLoteColorante());
        lote.setLoteCalcio(dto.getLoteCalcio());
        lote.setLoteCuajo(dto.getLoteCuajo());
        return lote;
    }

    private String generateID(LoteDTO dto) {
        String dateString = dto.getFechaElaboracion().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return dateString + dto.getCodigoQueso() + dto.getNumeroTina().toString();
    }
}
