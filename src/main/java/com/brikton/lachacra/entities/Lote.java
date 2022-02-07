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
@NoArgsConstructor
public class Lote {

    @Id
    @Column(name = "id_lote")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate fechaElaboracion;
    private Integer numeroTina;
    private Double litrosLeche;
    private Integer cantHormas;
    private Integer stockLote;
    private Double peso;
    private Double rendimiento;
    private String cultivo; // ToDo:ver
    @ElementCollection
    private List<String> loteCultivo;
    @ElementCollection
    private List<String> loteColorante;
    @ElementCollection
    private List<String> loteCalcio;
    @ElementCollection
    private List<String> loteCuajo;

    @ManyToOne
    @JoinColumn(name = "codigo_queso")
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
