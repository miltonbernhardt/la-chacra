package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.fechaBaja IS NULL")
    List<Cliente> findAllClientesNotDadoBaja();

    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM Cliente c WHERE c.fechaBaja IS NULL AND c.id=:id")
    boolean existsByIdAndNotDadoBaja(Long id);

}