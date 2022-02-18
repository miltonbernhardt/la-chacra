package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Queso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuesoRepository extends JpaRepository<Queso, String> {
    @Query("SELECT q FROM Queso q WHERE q.fechaBaja = null ORDER BY q.codigo")
    List<Queso> findALLQuesos();
}