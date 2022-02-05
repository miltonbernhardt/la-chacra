package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.exceptions.DatabaseException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.repositories.LoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            log.error("", e);
            throw new LoteNotFoundException();
        }
    }

    public LoteDTO save(LoteDTO dto) throws DatabaseException {
        try {
            var lote = loteFromDTO(dto);
            var queso = quesoService.getQueso(dto.getCodigoQueso());

            lote.setQueso(queso);
            lote = repository.save(lote);

            return new LoteDTO(lote);
        } catch (Exception e) {
            log.error("", e);
            throw new DatabaseException();
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
