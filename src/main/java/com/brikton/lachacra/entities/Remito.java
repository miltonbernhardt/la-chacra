package com.brikton.lachacra.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Remito {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    @GenericGenerator(name = "seq", strategy = "increment")
    @Column(name = "id_remito")
    private Long id;
    private LocalDate fecha;
    private Double importeTotal;
    private Integer cantCajas;
    private Integer cantPallets;

    @OneToMany
    @ToString.Exclude
    private List<Expedicion> expediciones;

    @Transient
    private List<ItemRemito> itemsRemito;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Remito remito = (Remito) o;
        return Objects.equals(id, remito.id) && Objects.equals(fecha, remito.fecha) && Objects.equals(importeTotal, remito.importeTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, importeTotal);
    }

    @Override
    public String toString() {
        return "Remito{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", importeTotal=" + importeTotal +
                ", expediciones=" + expediciones +
                ", itemsRemito=" + itemsRemito +
                '}';
    }
}
