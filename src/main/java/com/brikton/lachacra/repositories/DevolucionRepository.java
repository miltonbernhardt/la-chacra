package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Devolucion;
import com.brikton.lachacra.entities.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {

    boolean existsByLote(Lote lote);

}