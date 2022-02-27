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
@RequiredArgsConstructor
public class Expedicion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id_expedicion")
    private Long id;
    private LocalDate fechaExpedicion;
    private Integer cantidad;
    private Double peso;
    private Double importe;

    @ManyToOne
    @JoinColumn(name = "nro_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote lote;
}
