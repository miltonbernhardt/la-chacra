package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Queso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuesoRepository extends JpaRepository<Queso, Long> {
    @Query("SELECT q FROM Queso q WHERE q.fechaBaja IS NULL ORDER BY q.codigo")
    List<Queso> findAllQuesos();

    Optional<Queso> findByCodigo(String codigo);

    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM Queso q WHERE q.fechaBaja IS NULL AND q.codigo=:codigo")
    boolean existsQuesoByCodigo(@Param("codigo") String codigo);
}