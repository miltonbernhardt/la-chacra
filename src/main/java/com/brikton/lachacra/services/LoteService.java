package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.*;
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

    //todo ver si retronar el dto o la entity (usando json properties para indicar que mostrar y que no)
    public LoteDTO get(String id) throws LoteNotFoundException {
        var lote = repository.findById(id);
        if (lote.isPresent()) {
            return new LoteDTO(lote.get());
        } else {
            throw new LoteNotFoundException();
        }
    }

    public LoteDTO save(LoteDTO dto) throws NotFoundConflictException {
        var lote = loteFromDTO(dto);
        Queso queso;
        try {
            queso = quesoService.get(dto.getIdQueso());
        } catch (QuesoNotFoundException e) {
            throw new NotFoundConflictException("Queso no encontrado", e.getCause());
        }

        lote.setQueso(queso);
        lote = repository.save(lote);
        return new LoteDTO(lote);
    }

    private Lote loteFromDTO(LoteDTO dto) {
        var lote = new Lote();
        if (dto.getId().equals(0)) { //el id es 0 si no existe
            String id = String.valueOf(dto.getFechaElaboracion().getMonthValue()) +
                    String.valueOf(dto.getFechaElaboracion().getDayOfMonth()) +
                    String.valueOf(dto.getFechaElaboracion().getYear()) +
                    dto.getIdQueso() + //TODO aca faltarian los 00 que usan ellos, usamos codigo queso o cambiamos a string
                    dto.getNumeroTina().toString();//TODO esto no deberia ser id queso, porque nos queda mal generado el id
            lote.setId(id);
        } else lote.setId(dto.getId());
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
}
