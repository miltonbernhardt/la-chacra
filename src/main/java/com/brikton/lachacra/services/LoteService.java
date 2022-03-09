package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.LoteUpdateDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.LoteAlreadyExistsException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.QuesoNotFoundConflictException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.DevolucionRepository;
import com.brikton.lachacra.repositories.ExpedicionRepository;
import com.brikton.lachacra.repositories.LoteRepository;
import com.brikton.lachacra.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LoteService {

    private final DateUtil dateUtil;
    private final DevolucionRepository devolucionRepository;
    private final ExpedicionRepository expedicionRepository;
    private final LoteRepository repository;
    private final QuesoService quesoService;

    public LoteService(
            DateUtil dateUtil,
            DevolucionRepository devolucionRepository,
            ExpedicionRepository expedicionRepository,
            LoteRepository repository,
            QuesoService quesoService
    ) {
        this.dateUtil = dateUtil;
        this.devolucionRepository = devolucionRepository;
        this.expedicionRepository = expedicionRepository;
        this.repository = repository;
        this.quesoService = quesoService;
    }

    public List<LoteDTO> getAll() {
        var lotesDTO = new ArrayList<LoteDTO>();
        repository.findAllLotesNotFechaBaja().forEach(lote -> lotesDTO.add(new LoteDTO(lote)));
        return lotesDTO;
    }

    public Lote get(String id) throws LoteNotFoundException {
        var lote = repository.findById(id);
        if (lote.isPresent() && lote.get().getFechaBaja() == null)
            return lote.get();
        throw new LoteNotFoundException();
    }

    public Lote decreaseStock(Lote lote, Integer cantidad) {
        var oldStock = lote.getStockLote();
        var actualStock = oldStock - cantidad;
        lote.setStockLote(actualStock);
        return repository.save(lote);
    }

    public void increaseStock(Lote lote, Integer cantidad) {
        var oldStock = lote.getStockLote();
        var actualStock = oldStock + cantidad;
        lote.setStockLote(actualStock);
        repository.save(lote);
    }

    public LoteDTO save(LoteDTO dto) throws QuesoNotFoundConflictException, LoteAlreadyExistsException {
        var id = generateID(dto);
        checkAlreadyExistenceLote(id);
        return persist(dto);
    }

    private void checkAlreadyExistenceLote(String id) {
        if (repository.existsByIdNotFechaBaja(id)) {
            throw new LoteAlreadyExistsException();
        }
    }

    public LoteDTO update(LoteUpdateDTO dtoUpdate) throws QuesoNotFoundConflictException, LoteNotFoundException {
        var dto = new LoteDTO(dtoUpdate);
        checkExistenceLote(dto.getId());
        return persist(dto);
    }

    private LoteDTO persist(LoteDTO dto) throws QuesoNotFoundConflictException {
        var lote = loteFromDTO(dto);

        var rendimiento = calculateRendimiento(dto.getPeso(), dto.getLitrosLeche());
        lote.setRendimiento(rendimiento);

        var stock = dto.getCantHormas();
        lote.setStockLote(stock);

        updateStockQueso(dto);

        lote = repository.save(lote);
        return new LoteDTO(lote);
    }

    private Double calculateRendimiento(Double pesoD, Double litrosLecheD) {
        var peso = BigDecimal.valueOf(pesoD);
        var litrosLeche = BigDecimal.valueOf(litrosLecheD);
        var mc = new MathContext(2);
        var rendimiento = peso.divide(litrosLeche, mc);

        rendimiento = rendimiento.multiply(BigDecimal.valueOf(100));

        return rendimiento.doubleValue();
    }

    private Lote loteFromDTO(LoteDTO dto) throws QuesoNotFoundConflictException {
        var lote = new Lote();

        var queso = getQueso(dto.getCodigoQueso());
        lote.setQueso(queso);

        lote.setId(generateID(dto));
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

    private Queso getQueso(String codigoQueso) {
        try {
            return quesoService.getByCodigo(codigoQueso);
        } catch (QuesoNotFoundException e) {
            throw new QuesoNotFoundConflictException();
        }
    }

    private String generateID(LoteDTO dto) {
        String dateString = dto.getFechaElaboracion().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return dateString + dto.getCodigoQueso() + dto.getNumeroTina().toString();
    }

    public void delete(String id) throws LoteNotFoundException {
        checkExistenceLote(id);

        if (expedicionRepository.existsByIdLote(id) || devolucionRepository.existsByIdLote(id))
            darBajaLote(id);
        else
            repository.deleteById(id);
    }

    private void checkExistenceLote(String id) {
        if (!repository.existsByIdNotFechaBaja(id)) {
            throw new LoteNotFoundException();
        }
    }

    private void darBajaLote(String id) {
        var lote = repository.getById(id);
        lote.setFechaBaja(dateUtil.now());
        repository.save(lote);
    }

    private void updateStockQueso(LoteDTO dto){
        var queso = getQueso(dto.getCodigoQueso());
        if (repository.existsById(dto.getId())) {
            var oldLote = repository.getById(dto.getId());
                quesoService.decreaseStock(oldLote.getQueso(),oldLote.getCantHormas());
        }
        quesoService.increaseStock(queso,dto.getCantHormas());
    }
}
