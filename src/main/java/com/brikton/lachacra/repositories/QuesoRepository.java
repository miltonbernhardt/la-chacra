package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Queso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuesoRepository extends JpaRepository<Queso, Long> {
    @Query("SELECT q FROM Queso q WHERE q.fechaBaja = null ORDER BY q.codigo")
    List<Queso> findAllQuesos();

    Optional<Queso> findByCodigo(String codigo);
    boolean existsQuesoByNomenclatura(String nomenclatura);
    boolean existsQuesoByCodigo(String nomenclatura);
}