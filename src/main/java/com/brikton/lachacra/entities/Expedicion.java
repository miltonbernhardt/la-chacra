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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expedicion {

    @Id
    @Column(name = "id_expedicion")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate fechaExpedicion;
    private int cantidad;
    private double peso;
    private double importe;
    private String comentario; // TODO esto creo se puede obviar

    @ManyToOne
    @JoinColumn(name = "nro_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote lote;
}
