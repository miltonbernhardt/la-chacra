package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Precio;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrecioRepository extends JpaRepository<Precio, Long> {

    List<Precio> findAllByOrderByTipoClienteAscIdAsc();

    @Query("SELECT p.id FROM Precio p WHERE p.queso.id = :idQueso")
    List<Long> findAllByIdQueso(Long idQueso);

    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM Precio p WHERE p.queso.id=:idQueso AND p.tipoCliente.id=:idTipoCliente")
    boolean existsByQuesoAndTipoCliente(Long idQueso, Long idTipoCliente);

    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM Precio p WHERE p.queso.id=:idQueso AND p.tipoCliente.id=:idTipoCliente AND p.id=:id")
    boolean existsByIdAndQuesoAndTipoCliente(Long id, Long idQueso, Long idTipoCliente);

    Optional<Precio> findByQuesoAndAndTipoCliente(Queso queso, TipoCliente tipoCliente);

}