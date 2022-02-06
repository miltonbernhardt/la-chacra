package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.*;
import com.brikton.lachacra.repositories.LoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import java.util.HashMap;

@Service
@Slf4j
public class LoteService {

    private final LoteRepository repository;
    private final QuesoService quesoService;

    public LoteService(LoteRepository repository, QuesoService quesoService) {
        this.repository = repository;
        this.quesoService = quesoService;
    }

    public LoteDTO get(Long id) throws LoteNotFoundException {
        try {
            var lote = repository.getById(id);
            return new LoteDTO(lote);
        } catch (Exception e) {
            throw new LoteNotFoundException(e.getCause());
        }
    }

    public LoteDTO save(LoteDTO dto) throws DatabaseException, NotFoundConflictException {
        var lote = loteFromDTO(dto);
        Queso queso;
        try {
            queso = quesoService.getQueso(dto.getCodigoQueso());
        } catch (QuesoNotFoundException e) {
            throw new NotFoundConflictException("Queso no encontrado", e.getCause());
        }

        try {
            lote.setQueso(queso);
            lote = repository.save(lote);
            return new LoteDTO(lote);
        } catch (Exception e) {
            throw new DatabaseException(e.getCause());
        }
    }
    private Lote loteFromDTO(LoteDTO dto) {
        var lote = new Lote();
        lote.setId(dto.getId());
        lote.setFechaElaboracion(dto.getFechaElaboracion());
        lote.setNumeroTina(dto.getNumeroTina());
        lote.setLitrosLeche(dto.getLitrosLeche());
        lote.setCantHormas(dto.getCantHormas());
        lote.setStockLote(dto.getStockLote());
        lote.setPeso(dto.getPeso());
        lote.setRendimiento(dto.getRendimiento());
        lote.setCultivo(dto.getCultivo());
        lote.setLoteCultivo(dto.getLoteCultivo());
        lote.setLoteColorante(dto.getLoteColorante());
        lote.setLoteCalcio(dto.getLoteCalcio());
        lote.setLoteCuajo(dto.getLoteCuajo());
        lote.setLoteCuajo(dto.getLoteCuajo());
        return lote;
    }
}
