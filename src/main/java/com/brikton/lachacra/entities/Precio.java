package com.brikton.lachacra.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id_queso","id_tipo_cliente"})})
public class Precio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column
    private Long id;

    private Double valor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_queso")
    @ToString.Exclude
    private Queso queso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_cliente")
    @ToString.Exclude
    private TipoCliente tipoCliente;
}
