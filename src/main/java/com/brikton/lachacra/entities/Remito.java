package com.brikton.lachacra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Remito {

    @Id
    @Column(name = "idRemito")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private LocalDate fecha;

    @NotNull
    @Min(0)
    private double importeTotal;

    @OneToMany // ToDo: ver el fetch y el orphan removal
    @JoinColumn(name = "idExpedicion")
    private List<Expedicion> expediciones;
}
