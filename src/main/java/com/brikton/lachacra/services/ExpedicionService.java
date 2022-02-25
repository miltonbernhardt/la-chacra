package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.ExpedicionDTO;
import com.brikton.lachacra.entities.Cliente;
import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.exceptions.ClienteNotFoundException;
import com.brikton.lachacra.exceptions.ExpedicionAlreadyExistsException;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.repositories.ClienteRepository;
import com.brikton.lachacra.repositories.ExpedicionRepository;
import com.brikton.lachacra.repositories.LoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExpedicionService {

    private final ExpedicionRepository expedicionRepository;
    private final ClienteRepository clienteRepository;
    private final LoteService loteService;

    public ExpedicionService(ExpedicionRepository expedicionRepository, ClienteRepository clienteRepository, LoteService loteService) {
        this.expedicionRepository = expedicionRepository;
        this.clienteRepository = clienteRepository;
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

    private Expedicion expedicionFromDTO(ExpedicionDTO dto) throws LoteNotFoundException, ClienteNotFoundException {
        var expedicion = new Expedicion();
        var cliente = clienteRepository.findById(dto.getIdCliente());
        if (cliente.isEmpty()) throw new ClienteNotFoundException();
        expedicion.setCliente(cliente.get());
        var lote = loteService.getEntity(dto.getIdLote());
        expedicion.setLote(lote);
        expedicion.setFechaExpedicion(dto.getFechaExpedicion());
        expedicion.setId(dto.getId());
        expedicion.setCantidad(dto.getCantidad());
        expedicion.setImporte(dto.getImporte());
        expedicion.setPeso(dto.getPeso());
        return expedicion;
    }
}
