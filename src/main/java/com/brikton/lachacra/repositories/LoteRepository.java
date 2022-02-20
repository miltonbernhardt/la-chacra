package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteRepository extends JpaRepository<Lote, String> {
    boolean existsByQueso(Queso queso);
}