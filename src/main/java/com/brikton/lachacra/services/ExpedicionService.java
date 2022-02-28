package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ExpedicionDTO;
import com.brikton.lachacra.dtos.ExpedicionUpdateDTO;
import com.brikton.lachacra.entities.Cliente;
import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.exceptions.*;
import com.brikton.lachacra.repositories.ClienteRepository;
import com.brikton.lachacra.repositories.ExpedicionRepository;
import com.brikton.lachacra.repositories.PrecioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExpedicionService {

    private final ExpedicionRepository expedicionRepository;
    private final ClienteRepository clienteRepository;
    private final PrecioRepository precioRepository;
    private final LoteService loteService;

    public ExpedicionService(ExpedicionRepository expedicionRepository, ClienteRepository clienteRepository, PrecioRepository precioRepository, LoteService loteService) {
        this.expedicionRepository = expedicionRepository;
        this.clienteRepository = clienteRepository;
        this.precioRepository = precioRepository;
        this.loteService = loteService;
    }

    public boolean existsByLote(Lote lote) {
        return expedicionRepository.existsByLote(lote);
    }

    public boolean existsByCliente(Cliente cliente) {
        return expedicionRepository.existsByCliente(cliente);
    }

    public List<ExpedicionDTO> getAll() {
        List<ExpedicionDTO> response = new ArrayList<>();
        expedicionRepository.findAll().forEach(e -> response.add(new ExpedicionDTO(e)));
        return response;
    }

    public ExpedicionDTO save(ExpedicionDTO dto) throws ClienteNotFoundException, LoteNotFoundException, ExpedicionAlreadyExistsException {
        if (dto.getId() != null && expedicionRepository.existsById(dto.getId())) throw new ExpedicionAlreadyExistsException();
        var expedicion = expedicionFromDTO(dto);
         // decrement stock lote
        loteService.decrementStock(expedicion.getLote(),expedicion.getCantidad());
        return new ExpedicionDTO(expedicionRepository.save(expedicion));
    }

    public ExpedicionDTO update(ExpedicionUpdateDTO dto) throws ExpedicionNotFoundException {
        var expedicion = expedicionRepository.findById(dto.getId());
        if (expedicion.isEmpty()) throw new ExpedicionNotFoundException();
        // if lote is the same and updates cantidad
        if (dto.getIdLote().equals(expedicion.get().getLote().getId()) &&
                !dto.getCantidad().equals(expedicion.get().getCantidad())) {
            // return the difference to stock lote
            var diff = dto.getCantidad() - expedicion.get().getCantidad();
            loteService.decrementStock(expedicion.get().getLote(),diff);
        }
        // if lote is not the same
        if (!dto.getIdLote().equals(expedicion.get().getLote().getId())){
            // return all stock to lote and change lote (decrement -cantidad)
            loteService.decrementStock(expedicion.get().getLote(),-expedicion.get().getCantidad());
            // decrement stock of new lote
            var lote = loteService.getEntity(dto.getIdLote());
            loteService.decrementStock(lote,dto.getCantidad());
        }
        var updatedExpedicion = expedicionRepository.save(expedicionFromDTO(dto));
        return new ExpedicionDTO(updatedExpedicion);
    }

    private Expedicion expedicionFromDTO(ExpedicionDTO dto) throws PrecioNotFoundException,LoteNotFoundException, ClienteNotFoundException {
        var expedicion = new Expedicion();
        var cliente = clienteRepository.findById(dto.getIdCliente());
        if (cliente.isEmpty()) throw new ClienteNotFoundException();
        expedicion.setCliente(cliente.get());
        var lote = loteService.getEntity(dto.getIdLote());
        expedicion.setLote(lote);
        expedicion.setFechaExpedicion(dto.getFechaExpedicion());
        expedicion.setId(dto.getId());
        expedicion.setCantidad(dto.getCantidad());
        var precio = precioRepository.findByQuesoAndAndTipoCliente(lote.getQueso(),cliente.get().getTipoCliente());
        if (precio.isEmpty()) throw new PrecioNotFoundException();
        expedicion.setImporte(precio.get().getValor() * dto.getPeso());
        expedicion.setPeso(dto.getPeso());
        return expedicion;
    }

    private Expedicion expedicionFromDTO(ExpedicionUpdateDTO dto) throws PrecioNotFoundException, LoteNotFoundException, ClienteNotFoundException {
        var expedicion = new Expedicion();
        var cliente = clienteRepository.findById(dto.getIdCliente());
        if (cliente.isEmpty()) throw new ClienteNotFoundException();
        expedicion.setCliente(cliente.get());
        var lote = loteService.getEntity(dto.getIdLote());
        expedicion.setLote(lote);
        expedicion.setFechaExpedicion(dto.getFechaExpedicion());
        expedicion.setId(dto.getId());
        expedicion.setCantidad(dto.getCantidad());
        var precio = precioRepository.findByQuesoAndAndTipoCliente(lote.getQueso(),cliente.get().getTipoCliente());
        if (precio.isEmpty()) throw new PrecioNotFoundException();
        expedicion.setImporte(precio.get().getValor() * dto.getPeso());
        expedicion.setPeso(dto.getPeso());
        return expedicion;
    }
}
