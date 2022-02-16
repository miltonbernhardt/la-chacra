package com.brikton.lachacra.repositories;

import com.brikton.lachacra.entities.Queso;
import com.brikton.lachacra.exceptions.QuesoNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuesoRepository extends JpaRepository<Queso, String> {
    //todo ver de hacer con query
    default Queso getById(String id) {
        var queso = findById(id);
        if (queso.isPresent() && queso.get().getFechaBaja() == null)
            return queso.get();
        else
            throw new QuesoNotFoundException();
    }
}