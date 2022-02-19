package com.brikton.lachacra.entities;

import lombok.*;

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
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, name = "codigo")
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
        if (o == null || getClass() != o.getClass()) return false;
        Queso queso = (Queso) o;
        return Objects.equals(id, queso.id) && Objects.equals(codigo, queso.codigo) && Objects.equals(tipoQueso, queso.tipoQueso) && Objects.equals(nomenclatura, queso.nomenclatura) && Objects.equals(stock, queso.stock) && Objects.equals(fechaBaja, queso.fechaBaja);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, tipoQueso, nomenclatura, stock, fechaBaja);
    }
}
