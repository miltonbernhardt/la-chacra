package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    /**
     * Use this method if you want entities with
     * fechaBaja unset
     * @return all entities with fechaBaja null
     */
    @Query("SELECT c FROM Cliente c WHERE c.fechaBaja IS NULL")
    List<Cliente> findAllClientesNotFechaBaja();

    /**
     * Use this method if you want entities with
     * fechaBaja unset
     * @param id
     * @return if entity exist with fechaBaja null
     */
    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM Cliente c WHERE c.fechaBaja IS NULL AND c.id=:id")
    boolean existsByIdNotFechaBaja(Long id);
}