package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ExpedicionDTO;
import com.brikton.lachacra.dtos.ExpedicionUpdateDTO;
import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.exceptions.ClienteNotFoundException;
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

    private final ExpedicionRepository expedicionRepository;
    private final ClienteService clienteService;
    private final PrecioService precioService;
    private final RemitoRepository remitoRepository;
    private final LoteService loteService;

    public ExpedicionService(
            ExpedicionRepository expedicionRepository,
            ClienteService clienteService,
            PrecioService precioService,
            RemitoRepository remitoRepository,
            LoteService loteService
    ) {
        this.expedicionRepository = expedicionRepository;
        this.clienteService = clienteService;
        this.precioService = precioService;
        this.remitoRepository = remitoRepository;
        this.loteService = loteService;
    }

    public List<ExpedicionDTO> getAll() {
        List<ExpedicionDTO> response = new ArrayList<>();
        expedicionRepository.findAll().forEach(expedicion -> response.add(new ExpedicionDTO(expedicion)));
        return response;
    }

    public ExpedicionDTO save(ExpedicionDTO dto) throws ClienteNotFoundException, LoteNotFoundException {
        var expedicion = expedicionFromDTO(dto);

        loteService.decreaseStock(expedicion.getLote(), expedicion.getCantidad());

        double importe = getImporte(dto, expedicion);
        expedicion.setImporte(importe);

        expedicion = expedicionRepository.save(expedicion);
        return new ExpedicionDTO(expedicion);
    }

    public ExpedicionDTO update(ExpedicionUpdateDTO updateDTO) throws ExpedicionNotFoundException {
        var dto = new ExpedicionDTO(updateDTO);

        var expedicion = this.get(dto.getId());
        var expedicionUpdated = expedicionFromDTO(dto);

        updateStockLotes(expedicion, expedicionUpdated);

        var importe = getImporte(dto, expedicionUpdated);
        expedicionUpdated.setImporte(importe);

        expedicionUpdated = expedicionRepository.save(expedicionUpdated);
        return new ExpedicionDTO(expedicionUpdated);
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

    public void delete(Long id) throws ExpedicionNotFoundException {
        var expedicion = get(id);

        //TODO check if don't exists remitos associated
        if (remitoRepository.existsByExpedicionesContains(expedicion))
            return;

        loteService.increaseStock(expedicion.getLote(), expedicion.getCantidad());
        expedicionRepository.delete(expedicion);
    }

    private Expedicion get(Long id) {
        var expedicion = expedicionRepository.findById(id);
        if (expedicion.isEmpty())
            throw new ExpedicionNotFoundException();
        return expedicion.get();
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
}
