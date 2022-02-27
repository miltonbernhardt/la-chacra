package com.brikton.lachacra.entities;

import lombok.*;
import org.hibernate.Hibernate;
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
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id_remito")
    private Long id;
    private LocalDate fecha;
    private Double importeTotal;

    @OneToMany
    @ToString.Exclude
    private List<Expedicion> expediciones;
}
