package com.brikton.lachacra.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @OneToMany
    @ToString.Exclude
    private List<Expedicion> expediciones;

    @Transient
    private List<ItemRemito> itemsRemito;
}
