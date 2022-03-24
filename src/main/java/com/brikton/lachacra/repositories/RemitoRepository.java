package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Expedicion;
import com.brikton.lachacra.entities.Remito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemitoRepository extends JpaRepository<Remito, Long> {

    boolean existsByExpedicionesContains(Expedicion expedicion);

}