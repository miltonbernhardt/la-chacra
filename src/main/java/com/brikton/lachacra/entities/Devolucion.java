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
public class Devolucion {
    @Id
    @Column(name = "id_devolucion")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private LocalDate fecha;

    private String motivo;

    @NotNull
    @Min(1)
    private int cantidad;

    @NotNull
    @Min(1)
    private double peso; //ToDo: ver tipo

    @NotNull
    @Min(1)
    private double temper; //ToDo: ver tipo

    @ManyToOne
    @JoinColumn(name = "nro_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote loteProducto;
}
