package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.exceptions.InvalidLoteException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.repositories.LoteRepository;
import com.brikton.lachacra.repositories.QuesoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoteService {

    private final LoteRepository repositoryLote;
    private final QuesoRepository repositoryQueso;

    public LoteService(LoteRepository repositoryLote, QuesoRepository repositoryQueso) {
        this.repositoryLote = repositoryLote;
        this.repositoryQueso = repositoryQueso;
    }

    public LoteDTO get(Long id) throws LoteNotFoundException {
        try {
            var lote = repositoryLote.getById(id);
            return new LoteDTO(lote);
        } catch (Exception e) {
            log.error("", e);
            throw new LoteNotFoundException();
        }
    }

    public LoteDTO save(LoteDTO dto) throws InvalidLoteException {
        try {
            var lote = loteFromDTO(dto);

            //todo esto se debería hacer con el service de queso
            var queso = repositoryQueso.getById(dto.getCodigoQueso());

            lote.setQueso(queso);

            lote = repositoryLote.save(lote);
            return new LoteDTO(lote);
        } catch (Exception e) {
//            log.error("tipo de excepcion {}", e.);
            log.error("error ", e);
        }
        return new LoteDTO();
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
