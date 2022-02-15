package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
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

    public LoteDTO get(String id) throws LoteNotFoundException {
        var lote = repository.findById(id);
        if (lote.isPresent())
            return new LoteDTO(lote.get());
        else
            throw new LoteNotFoundException();
    }

    public LoteDTO save(LoteDTO dto) throws NotFoundConflictException {
        var id = dto.getId() == null || dto.getId().equals("") ? generateID(dto) : dto.getId();
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }

        var lote = loteFromDTO(dto);
        var rendimiento = (dto.getPeso() / dto.getLitrosLeche()) * 100;
        var stock = 90; //todo hardcoded
        lote.setRendimiento(rendimiento);
        lote.setStockLote(stock);
        lote = repository.save(lote);
        return new LoteDTO(lote);
    }

    public LoteDTO update(LoteDTO dto) throws NotFoundConflictException, LoteNotFoundException {
        var id = dto.getId() == null || dto.getId().equals("") ? generateID(dto) : dto.getId();
        dto.setId(id);
        delete(dto.getId());
        return save(dto);
    }

    public List<LoteDTO> getAll() {
        var lotesDTO = new ArrayList<LoteDTO>();
        var lotes = repository.findAll();
        lotes.forEach(lote -> lotesDTO.add(new LoteDTO(lote)));
        return lotesDTO;
    }

    public String delete(String id) throws LoteNotFoundException {
        if (repository.existsById(id))
            repository.deleteById(id);
        else
            throw new LoteNotFoundException();
        return id;
    }

    private Lote loteFromDTO(LoteDTO dto) throws NotFoundConflictException {
        Queso queso;
        try {
            queso = quesoService.getEntity(dto.getCodigoQueso());
        } catch (QuesoNotFoundException e) {
            throw new NotFoundConflictException("Queso no encontrado", e.getCause());
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

    //todo use annotation
    private String generateID(LoteDTO dto) {
        String dateString = dto.getFechaElaboracion().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return dateString + dto.getCodigoQueso() + dto.getNumeroTina().toString();
    }
}
