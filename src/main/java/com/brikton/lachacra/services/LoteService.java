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

    public LoteDTO save(LoteDTO dto) throws NotFoundConflictException {
//        var id = dto.getId() == null || dto.getId().equals("") ? generateID(dto) : dto.getId();
//        if (!repository.existsById(id)) {
//            throw new LoteAlreadyExistsException()
//        }
//
//        var lote = loteFromDTO(dto);
//        var rendimiento = (dto.getPeso() / dto.getLitrosLeche()) * 100;
//        var stock = dto.getCantHormas(); //TODO si es un update no debería asignar directamente, sino que debería sumar/restar
//        lote.setRendimiento(rendimiento);
//        lote.setStockLote(stock);
//        lote = repository.save(lote);
//        return new LoteDTO(lote);
        return null; //todo
    }

    public LoteDTO update(LoteUpdateDTO dto) throws NotFoundConflictException, LoteNotFoundException {
        delete(dto.getId());
        return save(new LoteDTO(dto));
    }

    public List<LoteDTO> getAll() {
        var lotesDTO = new ArrayList<LoteDTO>();
        var lotes = repository.findAll();
        lotes.forEach(lote -> lotesDTO.add(new LoteDTO(lote)));
        return lotesDTO;
    }

    public String delete(String id) throws LoteNotFoundException {
        //todo logica de borrar o dar de baja
        if (repository.existsById(id))
            repository.deleteById(id);
        else
            throw new LoteNotFoundException();
        return id;
    }

    private Lote loteFromDTO(LoteDTO dto) throws NotFoundConflictException {
        Queso queso;
        try {
            queso = quesoService.getEntity(dto.getIdQueso());
        } catch (QuesoNotFoundException e) {
            throw new NotFoundConflictException("Queso no encontrado", e.getCause());
        }

        var lote = new Lote();
        lote.setId(generateID(dto, queso.getCodigo()));
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

    private String generateID(LoteDTO dto, String codigoQueso) {
        String dateString = dto.getFechaElaboracion().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return dateString + codigoQueso + dto.getNumeroTina().toString();
    }
}
