package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoteRepository extends JpaRepository<Lote, String> {

    @Query("SELECT l FROM Lote l WHERE l.fechaBaja IS NULL")
    List<Lote> findAllLotesNotFechaBaja();

    boolean existsByQueso(Queso queso);

    @Query("SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM Lote l WHERE l.fechaBaja IS NULL AND l.id=:id")
    boolean existsByIdNotFechaBaja(String id);

    List<Lote> findAllByFechaBajaAndFechaElaboracionBetween(LocalDate fechaBaja, LocalDate fechaElaboracion, LocalDate fechaElaboracion2);

    List<Lote> findAllByQuesoAndStockLoteGreaterThan(Queso queso, Integer stockLote);
}