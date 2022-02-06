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
public class Lote {

    @Id
    @Column(name = "id_lote")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate fechaElaboracion;
    private Integer numeroTina;
    private Double litrosLeche;
    private Integer cantHormas;
    private Integer stockLote;
    private Double peso;
    private Double rendimiento;
    private String cultivo; // ToDo:ver
    @ElementCollection
    private List<String> loteCultivo;
    @ElementCollection
    private List<String> loteColorante;
    @ElementCollection
    private List<String> loteCalcio;
    @ElementCollection
    private List<String> loteCuajo;

    @ManyToOne
    @JoinColumn(name = "codigo_queso")
    private Queso queso;
}
