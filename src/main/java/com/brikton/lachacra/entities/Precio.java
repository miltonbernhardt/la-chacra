package com.brikton.lachacra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Precio {
    @Id
    @Column(name = "idPrecio")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Min(0)
    private double precio;

    @OneToOne //todo testear sino se borra
    @JoinColumn(name = "codigoQueso")
    private Queso queso;

    @ManyToOne
    @JoinColumn(name = "idTipoCliente")
    private TipoCliente tipoCliente;
}
