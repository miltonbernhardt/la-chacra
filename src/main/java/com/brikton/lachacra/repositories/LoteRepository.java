package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.LoteNotFoundException;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteRepository extends JpaRepository<Lote, String> {
    default Lote getById(String id) {
        return findById(id).orElseThrow(LoteNotFoundException::new);
    }
}