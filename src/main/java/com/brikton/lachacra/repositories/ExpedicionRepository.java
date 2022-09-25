package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Cliente;
import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.entities.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpedicionRepository extends JpaRepository<Expedicion, Long> {

    boolean existsByLote(Lote lote);

    boolean existsByCliente(Cliente cliente);

    List<Expedicion> findAllByLote(Lote lote);

    List<Expedicion> findAllByClienteAndOnRemito(Cliente cliente, Boolean onRemito);

    List<Expedicion> findAllByFechaExpedicionBetween(LocalDate fechaExpedicion, LocalDate fechaExpedicion2);
}