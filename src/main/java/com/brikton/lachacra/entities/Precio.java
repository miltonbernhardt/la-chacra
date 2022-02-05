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
    @Column(name = "id_precio")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Min(0)
    private double precio;

    @OneToOne //todo testear sino se borra
    @JoinColumn(name = "codigo_queso")
    private Queso queso;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cliente")
    private TipoCliente tipoCliente;
}
