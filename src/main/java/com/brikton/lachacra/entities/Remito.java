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
public class Remito {

    @Id
    @Column(name = "id_remito")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate fecha;
    private Double importeTotal;

    @OneToMany
    @JoinColumn(name = "id_remito")
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
