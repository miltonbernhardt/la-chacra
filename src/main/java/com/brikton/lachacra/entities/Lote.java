package com.brikton.lachacra.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Lote {

    @Id
    @Column
    private String id;
    private LocalDate fechaElaboracion;
    private Integer numeroTina;
    private Double litrosLeche;
    private Integer cantHormas;
    private Integer stockLote;
    private Integer cantCajas;
    private Double peso;
    private Double rendimiento;
    private String loteCultivo;
    private String loteColorante;
    private String loteCalcio;
    private String loteCuajo;
    private LocalDate fechaBaja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_queso")
    @ToString.Exclude
    private Queso queso;
}
