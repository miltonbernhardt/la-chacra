package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ExpedicionDTO;
import com.brikton.lachacra.dtos.ExpedicionUpdateDTO;
import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.exceptions.ClienteNotFoundException;
import com.brikton.lachacra.exceptions.ExpedicionCannotDeleteException;
import com.brikton.lachacra.exceptions.ExpedicionNotFoundException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.repositories.ExpedicionRepository;
import com.brikton.lachacra.repositories.RemitoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExpedicionService {

    private final ExpedicionRepository repository;
    private final ClienteService clienteService;
    private final PrecioService precioService;
    private final RemitoRepository remitoRepository;
    private final LoteService loteService;
    private final QuesoService quesoService;

    public ExpedicionService(
            ExpedicionRepository repository,
            ClienteService clienteService,
            PrecioService precioService,
            RemitoRepository remitoRepository,
            LoteService loteService,
            QuesoService quesoService) {
        this.repository = repository;
        this.clienteService = clienteService;
        this.precioService = precioService;
        this.remitoRepository = remitoRepository;
        this.loteService = loteService;
        this.quesoService = quesoService;
    }

    public List<ExpedicionDTO> getAll() {
        List<ExpedicionDTO> response = new ArrayList<>();
        repository.findAll().forEach(expedicion -> response.add(new ExpedicionDTO(expedicion)));
        return response;
    }

    public ExpedicionDTO save(ExpedicionDTO dto) throws ClienteNotFoundException, LoteNotFoundException {
        var expedicion = expedicionFromDTO(dto);

        loteService.decreaseStock(expedicion.getLote(), expedicion.getCantidad());
        quesoService.decreaseStock(expedicion.getLote().getQueso(),expedicion.getCantidad());

        double importe = getImporte(dto, expedicion);
        expedicion.setImporte(importe);

        expedicion = repository.save(expedicion);
        return new ExpedicionDTO(expedicion);
    }

    public ExpedicionDTO update(ExpedicionUpdateDTO updateDTO) throws ExpedicionNotFoundException {
        var dto = new ExpedicionDTO(updateDTO);

        var expedicion = get(dto.getId());
        var expedicionUpdated = expedicionFromDTO(dto);

        updateStockLotes(expedicion, expedicionUpdated);
        updateStockQuesos(expedicion, expedicionUpdated);

        var importe = getImporte(dto, expedicionUpdated);
        expedicionUpdated.setImporte(importe);

        expedicionUpdated = repository.save(expedicionUpdated);
        return new ExpedicionDTO(expedicionUpdated);
    }



    private Expedicion expedicionFromDTO(ExpedicionDTO dto) {
        var expedicion = new Expedicion();

        var cliente = clienteService.get(dto.getIdCliente());
        expedicion.setCliente(cliente);

        var lote = loteService.get(dto.getIdLote());
        expedicion.setLote(lote);

        expedicion.setFechaExpedicion(dto.getFechaExpedicion());
        expedicion.setId(dto.getId());
        expedicion.setCantidad(dto.getCantidad());
        expedicion.setPeso(dto.getPeso());
        return expedicion;
    }

    private void updateStockLotes(Expedicion expedicion, Expedicion expedicionUpdated) {
        if (isDifferentLote(expedicion, expedicionUpdated)) {
            loteService.increaseStock(expedicion.getLote(), expedicion.getCantidad());
            loteService.decreaseStock(expedicionUpdated.getLote(), expedicionUpdated.getCantidad());
        } else if (isSameLoteAndDifferentQuantity(expedicion, expedicionUpdated)) {
            var diff = expedicionUpdated.getCantidad() - expedicion.getCantidad();
            loteService.decreaseStock(expedicionUpdated.getLote(), diff);
        }
    }

    private void updateStockQuesos(Expedicion expedicion, Expedicion expedicionUpdated) {
            quesoService.increaseStock(expedicion.getLote().getQueso(),expedicion.getCantidad());
            quesoService.decreaseStock(expedicionUpdated.getLote().getQueso(),expedicionUpdated.getCantidad());
    }

    private boolean isSameLoteAndDifferentQuantity(Expedicion expedicion, Expedicion expedicionUpdated) {
        return expedicion.getLote().getId().equals(expedicionUpdated.getLote().getId()) &&
                !expedicion.getCantidad().equals(expedicionUpdated.getCantidad());
    }

    private boolean isDifferentLote(Expedicion expedicion, Expedicion expedicionUpdated) {
        return !expedicion.getLote().getId().equals(expedicionUpdated.getLote().getId());
    }

    private double getImporte(ExpedicionDTO dto, Expedicion expedicion) {
        var precio = BigDecimal.valueOf(precioService.getPrecioValue(expedicion.getLote().getQueso(), expedicion.getCliente().getTipoCliente()));
        var peso = BigDecimal.valueOf(dto.getPeso());
        return precio.multiply(peso).doubleValue();
    }

    public void delete(Long id) throws ExpedicionNotFoundException, ExpedicionCannotDeleteException {
        var expedicion = get(id);

        if (remitoRepository.existsByExpedicionesContains(expedicion))
            throw new ExpedicionCannotDeleteException();

        loteService.increaseStock(expedicion.getLote(), expedicion.getCantidad());
        repository.delete(expedicion);
    }

    private Expedicion get(Long id) {
        var expedicion = repository.findById(id);
        if (expedicion.isEmpty())
            throw new ExpedicionNotFoundException();
        return expedicion.get();
    }

    public List<ExpedicionDTO> getAllByLote(String idLote) {
        var lote = loteService.get(idLote);
        var expediciones = repository.findAllByLote(lote);
        List<ExpedicionDTO> response = new ArrayList<>();
        expediciones.forEach(e -> response.add(new ExpedicionDTO(e)));
        return response;
    }
}
