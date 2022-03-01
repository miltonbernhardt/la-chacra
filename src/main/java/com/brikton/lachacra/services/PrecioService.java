package com.brikton.lachacra.services;

import com.brikton.lachacra.dtos.PrecioDTO;
import com.brikton.lachacra.dtos.PrecioUpdateDTO;
import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.*;
import com.brikton.lachacra.repositories.PrecioRepository;
import com.brikton.lachacra.repositories.QuesoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PrecioService {

    private final PrecioRepository repository;
    private final QuesoRepository quesoRepository;
    private final TipoClienteService tipoClienteService;

    public PrecioService(
            PrecioRepository repository,
            QuesoRepository quesoRepository,
            TipoClienteService tipoClienteService
    ) {
        this.repository = repository;
        this.quesoRepository = quesoRepository;
        this.tipoClienteService = tipoClienteService;
    }

    public List<PrecioDTO> getAll() {
        List<PrecioDTO> lista = new ArrayList<>();
        repository.findAllByOrderByTipoClienteAscIdAsc().forEach(precio -> lista.add(new PrecioDTO(precio)));
        return lista;
    }

    public PrecioDTO save(PrecioDTO dto) throws PrecioAlreadyExistsException {
        validateExistencePrecio(dto.getIdQueso(), dto.getIdTipoCliente());
        var precio = precioFromDTO(dto);
        precio = repository.save(precio);
        return new PrecioDTO(precio);
    }

    private void validateExistencePrecio(Long idQueso, Long idTipoCliente) {
        if (repository.existsByQuesoAndTipoCliente(idQueso, idTipoCliente))
            throw new PrecioAlreadyExistsException();
    }

    public PrecioDTO update(PrecioUpdateDTO dto) throws PrecioNotFoundException {
        validateExistencePrecio(dto.getId(), dto.getIdQueso(), dto.getIdTipoCliente());
        var precio = precioFromDTO(new PrecioDTO(dto)); //todo test exceptions
        precio = repository.save(precio);
        return new PrecioDTO(precio);
    }

    private void validateExistencePrecio(Long id, Long idQueso, Long idTipoCliente) {
        if (!repository.existsByIdAndQuesoAndTipoCliente(id, idQueso, idTipoCliente))
            throw new PrecioNotFoundException();
    }


    private Precio precioFromDTO(PrecioDTO dto) throws TipoClienteNotFoundConflictException, QuesoNotFoundConflictException {
        Precio precio = new Precio();

        var tipoCliente = getTipoCliente(dto.getIdTipoCliente());
        precio.setTipoCliente(tipoCliente);

        var queso = getQueso(dto.getIdQueso());
        precio.setQueso(queso);

        precio.setValor(dto.getValor());
        precio.setId(dto.getId());
        return precio;
    }

    private Queso getQueso(Long id) {
        var queso = quesoRepository.findById(id);
        if (queso.isPresent() && queso.get().getFechaBaja() == null)
            return queso.get();
        throw new QuesoNotFoundConflictException();
    }

    private TipoCliente getTipoCliente(Long id) {
        try {
            return tipoClienteService.get(id);
        } catch (TipoClienteNotFoundException e) {
            throw new TipoClienteNotFoundConflictException();
        }
    }

    public void deletePreciosByQueso(Long idQueso) {
        var allPrecioIDs = repository.findAllByIdQueso(idQueso);
        repository.deleteAllById(allPrecioIDs);
    }

    public Double getPrecioValue(Queso queso, TipoCliente tipoCliente) {
        var precio = repository.findByQuesoAndAndTipoCliente(queso, tipoCliente);

        if (precio.isEmpty())
            throw new PrecioNotFoundException();

        return precio.get().getValor();
    }
}
