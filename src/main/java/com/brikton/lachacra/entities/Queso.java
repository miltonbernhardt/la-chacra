package com.brikton.lachacra.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Queso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    @GenericGenerator(name = "seq", strategy = "increment")
    @Column
    private Long id;
    @Column(name = "codigo")
    private String codigo;
    private String tipoQueso;
    private String nomenclatura;
    private Integer stock;
    private LocalDate fechaBaja;
    private String color;
    @ManyToMany(mappedBy = "listaQuesos")
    private Collection<Embalaje> embalajes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Queso queso = (Queso) o;
        return Objects.equals(id, queso.id) && Objects.equals(codigo, queso.codigo) && Objects.equals(tipoQueso, queso.tipoQueso) && Objects.equals(nomenclatura, queso.nomenclatura) && Objects.equals(stock, queso.stock) && Objects.equals(fechaBaja, queso.fechaBaja) && Objects.equals(color, queso.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, tipoQueso, nomenclatura, stock, fechaBaja, color);
    }

    public Collection<Embalaje> getEmbalajes() {
        return embalajes;
    }

    public void setEmbalajes(Collection<Embalaje> embalajes) {
        this.embalajes = embalajes;
    }
}
