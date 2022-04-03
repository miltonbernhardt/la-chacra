package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Devolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {

    @Query("SELECT CASE WHEN count(*) > 0 THEN false ELSE true END FROM Devolucion d WHERE d.lote.id=:idLote")
    boolean existsByIdLote(String idLote);

}