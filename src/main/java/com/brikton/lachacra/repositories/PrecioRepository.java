package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrecioRepository extends JpaRepository<Precio, Long> {
    @Query("SELECT p.id FROM Precio p WHERE p.queso.id = :idQueso")
    List<Long> findAllByIdQueso(Long idQueso);

    boolean existsByQuesoAndTipoCliente(Queso queso, TipoCliente tipoCliente);
}