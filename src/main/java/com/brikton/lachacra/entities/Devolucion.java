package com.brikton.lachacra.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Devolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id_devolucion")
    private Long id;
    private LocalDate fecha;
    private String motivo;
    private Integer cantidad;
    private Double peso;
    private Double temperatura;

    @ManyToOne
    @JoinColumn(name = "nro_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote lote;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Devolucion that = (Devolucion) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
