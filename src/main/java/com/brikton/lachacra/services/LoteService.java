package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.LoteUpdateDTO;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.dtos.rendimiento.RendimientoDTO;
import com.brikton.lachacra.dtos.rendimiento.RendimientoDiaDTO;
import com.brikton.lachacra.dtos.rendimiento.RendimientoQuesoDTO;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.LoteAlreadyExistsException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.QuesoNotFoundConflictException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import com.brikton.lachacra.repositories.DevolucionRepository;
import com.brikton.lachacra.repositories.ExpedicionRepository;
import com.brikton.lachacra.repositories.LoteRepository;
import com.brikton.lachacra.util.DateComparator;
import com.brikton.lachacra.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public LoteDTO getDTOById(String id){
        return new LoteDTO(get(id));
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

        dto.setStockLote(dto.getCantHormas());

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

        if (dto.getStockLote() != null)
            updateStockLote(dto);

        var updatedLote = persist(dto);

        if (updatedLote.getId() != dto.getId())
            delete(dto.getId());

        return updatedLote;
    }

    private LoteDTO persist(LoteDTO dto) throws QuesoNotFoundConflictException {
        var lote = loteFromDTO(dto);

        var rendimiento = calculateRendimiento(dto.getPeso(), dto.getLitrosLeche());
        lote.setRendimiento(rendimiento);

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

    public List<LoteDTO> getBetweenDates(LocalDate fechaDesde, LocalDate fechaHasta) {
        List<LoteDTO> result = new ArrayList<>();
        var list = repository.findAllByFechaBajaAndFechaElaboracionBetween(null,fechaDesde,fechaHasta);
        list.forEach(lote -> result.add(new LoteDTO(lote)));
        return result;
    }

    private void updateStockQueso(LoteDTO dto){
        var queso = getQueso(dto.getCodigoQueso());
        if (repository.existsByIdNotFechaBaja(dto.getId())) {
            var oldLote = repository.getById(dto.getId());
                quesoService.decreaseStock(oldLote.getQueso(),oldLote.getCantHormas());
        }
        quesoService.increaseStock(queso,dto.getCantHormas());
    }

    private void updateStockLote(LoteDTO dto) {
        var lote = repository.getById(dto.getId());
        var stock = dto.getStockLote() - lote.getCantHormas() + dto.getCantHormas();
        dto.setStockLote(stock);
    }

    public List<LoteDTO> getByQuesoAndWithStock(String codigoQueso) {
        var queso = getQueso(codigoQueso);
        var lista = repository.findAllByQuesoAndStockLoteGreaterThan(queso,0);
        List<LoteDTO> response = new ArrayList<>();
        lista.forEach(lote -> response.add(new LoteDTO(lote)));
        return response;
    }

    public List<RendimientoDiaDTO> getRendimientoByDia(LocalDate fechaDesde, LocalDate fechaHasta) {
        var lotes = repository.findAllByFechaBajaAndFechaElaboracionBetween(null,fechaDesde,fechaHasta);
        Map<LocalDate,List<Lote>> mapRendimientos = new HashMap<>();
        for (Lote lote : lotes) {
            var fecha = lote.getFechaElaboracion();
            addToMapRendimiento(mapRendimientos,fecha,lote);
        }
        var auxList = new ArrayList<>(mapRendimientos.values());
        ArrayList<RendimientoDiaDTO> rendimientos = new ArrayList<>();
        auxList.forEach(list -> {
            var dto = new RendimientoDiaDTO();
            dto.setFecha(list.get(0).getFechaElaboracion());
            updateRendimiento(dto,list);
            rendimientos.add(dto);
        });
        Collections.sort(rendimientos, new DateComparator());
        return rendimientos;
    }

    public List<RendimientoQuesoDTO> getRendimientoByQueso(LocalDate fechaDesde, LocalDate fechaHasta) {
        var lotes = repository.findAllByFechaBajaAndFechaElaboracionBetween(null,fechaDesde,fechaHasta);
        Map<String,List<Lote>> mapRendimientos = new HashMap<>();
        for (Lote lote : lotes) {
            var queso = lote.getQueso().getTipoQueso();
            addToMapRendimiento(mapRendimientos,queso,lote);
        }
        var auxList = new ArrayList<>(mapRendimientos.values());
        ArrayList<RendimientoQuesoDTO> rendimientos = new ArrayList<>();
        auxList.forEach(list -> {
            var dto = new RendimientoQuesoDTO();
            dto.setQueso(new QuesoDTO(list.get(0).getQueso()));
            updateRendimiento(dto,list);
            rendimientos.add(dto);
        });
        return rendimientos;
    }

    private <K,V> void addToMapRendimiento(Map<K,List<V>> map, K key, V value){
        if (map.containsKey(key)) {
            var list = map.get(key);
            list.add(value);
        } else {
            var list = new ArrayList<V>();
            list.add(value);
            map.put(key,list);
        }
    }

    private void updateRendimiento(RendimientoDTO dto, List<Lote> lotes){
        BigDecimal rendimientoAvg = BigDecimal.ZERO;
        for(Lote lote : lotes){
            rendimientoAvg = rendimientoAvg.add(BigDecimal.valueOf(lote.getRendimiento()));
        };
        rendimientoAvg = rendimientoAvg.divide(BigDecimal.valueOf(lotes.size()),MathContext.DECIMAL32);
        dto.setRendimiento(rendimientoAvg.doubleValue());
    }

}
