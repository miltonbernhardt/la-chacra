package com.brikton.lachacra.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Queso {

    @Id
    @Column(unique = true, name = "codigo_queso")
    private String codigo;
    private String tipoQueso;
    @Column(unique = true)
    private String nomenclatura;
    private Integer stock;
    private LocalDate fechaBaja;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Precio> preciosActual;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Queso queso = (Queso) o;
        return codigo != null && Objects.equals(codigo, queso.codigo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
