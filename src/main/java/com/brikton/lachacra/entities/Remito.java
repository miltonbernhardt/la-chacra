package com.brikton.lachacra.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

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
public class Remito {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id_remito")
    private Long id;
    private LocalDate fecha;
    private Double importeTotal;

    @OneToMany
    @ToString.Exclude
    private List<Expedicion> expediciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Remito remito = (Remito) o;
        return id != null && Objects.equals(id, remito.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
