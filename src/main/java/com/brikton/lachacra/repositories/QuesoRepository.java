package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Queso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuesoRepository extends JpaRepository<Queso, Long> {
    /**
     * Use this method if you want entities with
     * fechaBaja unset
     * @return all entities with fechaBaja null
     */
    @Query("SELECT q FROM Queso q WHERE q.fechaBaja IS NULL ORDER BY q.codigo")
    List<Queso> findAllQuesosNotFechaBaja();

    Optional<Queso> findByCodigo(String codigo);

    /**
     * Use this method if you want entities with
     * fechaBaja unset
     * @param codigo
     * @return if entity exist with fechaBaja null
     */
    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM Queso q WHERE q.fechaBaja IS NULL AND q.codigo=:codigo")
    boolean existsByCodigoNotFechaBaja(String codigo);
}