package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Precio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioRepository extends JpaRepository<Precio, Long> {
}