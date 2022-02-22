package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.entities.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedicionRepository extends JpaRepository<Expedicion, Long> {
    boolean existsByLote(Lote idLote);
}