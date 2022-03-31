package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Embalaje;
import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.entities.TipoEmbalaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmbalajeRepository extends JpaRepository<Embalaje, Long> {

    List<Embalaje> findByTipoEmbalajeAndListaQuesosContains(TipoEmbalaje tipoEmbalaje, Queso listaQuesos);

}
