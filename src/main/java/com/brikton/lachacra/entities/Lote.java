package com.brikton.lachacra.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Lote {

    @Id
    @Column(name = "id_lote")
    private String id;
    private LocalDate fechaElaboracion;
    private Integer numeroTina;
    private Double litrosLeche;
    private Integer cantHormas;
    private Integer stockLote;
    private Double peso;
    private Double rendimiento;
    private String loteCultivo;
    private String loteColorante;
    private String loteCalcio;
    private String loteCuajo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Queso queso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Lote lote = (Lote) o;
        return id != null && Objects.equals(id, lote.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
