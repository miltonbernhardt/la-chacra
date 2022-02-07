package com.brikton.lachacra.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Precio {
    @Id
    @Column(name = "id_precio")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double precio;

    @OneToOne
    @JoinColumn(name = "codigo_queso")
    private Queso queso;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cliente")
    private TipoCliente tipoCliente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Precio precio = (Precio) o;
        return id != null && Objects.equals(id, precio.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
