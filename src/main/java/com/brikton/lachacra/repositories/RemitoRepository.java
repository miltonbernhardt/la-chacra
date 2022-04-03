package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.entities.Remito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RemitoRepository extends JpaRepository<Remito, Long> {

    boolean existsByExpedicionesContains(Expedicion expedicion);

    List<Remito> findAllByFechaBetween(LocalDate fecha, LocalDate fecha2);
}