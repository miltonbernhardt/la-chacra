package com.brikton.lachacra.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class LoteDTO {
    private long id;
    private LocalDate fechaElaboracion;
    private int numeroTina;
    private double litrosLeche;
    private int cantHormas;
    private int stockLote;
    private double peso;
    private double rendimiento;
    private String cultivo;
    private List<String> loteCultivo;
    private List<String> loteColorante;
    private List<String> loteCalcio;
    private List<String> loteCuajo;
    private long quesoID;
}
