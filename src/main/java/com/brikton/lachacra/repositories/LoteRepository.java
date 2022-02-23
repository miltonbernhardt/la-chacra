package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoteRepository extends JpaRepository<Lote, String> {

    @Query("SELECT l FROM Lote l WHERE l.fechaBaja IS NULL")
    List<Lote> findAllLotes();

    boolean existsByQueso(Queso queso);
}