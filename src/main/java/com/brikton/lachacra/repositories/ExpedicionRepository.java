package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Expedicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedicionRepository extends JpaRepository<Expedicion, Long> {

    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM Expedicion e WHERE e.lote.id=:idLote")
    boolean existsByIdLote(String idLote);

    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM Expedicion e WHERE e.cliente.id=:idCliente")
    boolean existsByIdCliente(Long idCliente);

}