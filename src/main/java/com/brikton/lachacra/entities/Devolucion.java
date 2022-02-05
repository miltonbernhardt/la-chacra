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
public class Devolucion {
    @Id
    @Column(name = "id_devolucion")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate fecha;
    private String motivo;
    private int cantidad;
    private double peso; //ToDo: ver tipo
    private double temper; //ToDo: ver tipo

    @ManyToOne
    @JoinColumn(name = "nro_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote loteProducto;
}
