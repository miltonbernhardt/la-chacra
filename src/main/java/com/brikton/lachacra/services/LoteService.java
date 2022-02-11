package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.*;
import com.brikton.lachacra.repositories.LoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        lote = repository.save(lote);
        return new LoteDTO(lote);
    }

    public LoteDTO update(LoteDTO dto) throws NotFoundConflictException, LoteNotFoundException {
        var lote = repository.findById(dto.getId());
        if (lote.isPresent()){
            repository.delete(lote.get());
            var loteUpdated = loteFromDTO(dto);
            var id = generateID(dto,lote.get().getQueso());
            loteUpdated.setId(id);
            loteUpdated = repository.save(loteUpdated);
            return new LoteDTO(loteUpdated);
        }else {
            throw new LoteNotFoundException();
        }
    }

    public List<LoteDTO> getAll() {
       ArrayList<LoteDTO> lotesDTO = new ArrayList<>();
       var lotes = repository.findAll();
       lotes.forEach(l -> lotesDTO.add(new LoteDTO(l)));
       return lotesDTO;
    }

    private Lote loteFromDTO(LoteDTO dto) throws NotFoundConflictException {
        Queso queso;
        try {
            queso = quesoService.get(dto.getIdQueso());
        } catch (QuesoNotFoundException e) {
            throw new NotFoundConflictException("Queso no encontrado", e.getCause());
        }
        var lote = new Lote();
        lote.setQueso(queso);
        if (dto.getId().equals("")) { //el id es cadena vacia si no existe
            lote.setId(generateID(dto,queso));
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

    private String generateID(LoteDTO dto, Queso queso) {
        String day,month,year;
        if (dto.getFechaElaboracion().getMonthValue() < 10){
            month = "0"+dto.getFechaElaboracion().getMonthValue();
        } else month = String.valueOf(dto.getFechaElaboracion().getMonthValue());
        if (dto.getFechaElaboracion().getDayOfMonth() < 10) {
            day = "0" + dto.getFechaElaboracion().getDayOfMonth();
        } else day = String.valueOf(dto.getFechaElaboracion().getDayOfMonth());
        String id = month + day +
                String.valueOf(dto.getFechaElaboracion().getYear()) +
                queso.getCodigo() +
                dto.getNumeroTina().toString();
       return id;
    }
}
