package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoteRepository extends JpaRepository<Lote, String> {
    /**
     * Use this method if you want entities with
     * fechaBaja unset
     * @return all entities with fechaBaja null
     */
    @Query("SELECT l FROM Lote l WHERE l.fechaBaja IS NULL")
    List<Lote> findAllLotesNotFechaBaja();

    boolean existsByQueso(Queso queso);

    /**
     * Use this method if you want entities with
     * fechaBaja unset
     * @param id
     * @return if entity exist with fechaBaja null
     */
    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM Lote l WHERE l.fechaBaja IS NULL AND l.id=:id")
    boolean existsByIdNotFechaBaja(String id);

}