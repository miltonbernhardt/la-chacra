package com.brikton.lachacra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @Column(name = "nro_cliente")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String razonSocial;
    private String cuit;
    private String domicilio;
    private String codPostal;
    private String localidad;
    private String provincia;
    private String pais;
    // ToDo: ver condiciones
    private String transporte;
    private String senasaUta;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cliente")
    @NotNull
    private TipoCliente tipoCliente;

    // @OneToMany
    // @JoinColumn(name = "idDevolucion")
    // private List<Devolucion> devoluciones;

    // @OneToMany
    // @JoinColumn(name = "idExpedicion")
    // private List<Expedicion> expediciones;
}
