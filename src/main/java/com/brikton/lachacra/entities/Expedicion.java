package com.brikton.lachacra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Expedicion {

    @Id
    @Column(name = "idExpedicion")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private LocalDate fechaExpedicion;

    @NotNull
    @Min(1)
    private int cantidad;

    @NotNull
    @Min(0)
    private double pesoConBandeja;

    @NotNull
    @Min(0)
    private double pesoBandeja;

    @NotNull
    @Min(0)
    private double pesoQueso;

    @NotNull
    @Min(0)
    private double importe;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "nroCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idLote")
    private Lote lote;
}
