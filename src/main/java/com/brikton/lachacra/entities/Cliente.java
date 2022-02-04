package com.brikton.lachacra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Cliente {

    @Id
    @Column(name = "nroCliente") //ToDo:es generado o lo tienen asignado?
    private long nroCliente;

    @Length(min = 3, max = 100)
    @NotNull
    //ToDo:agregar validaciones en los campos https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-builtin-constraints
    private String razonSocial;

    @NotNull
    @Column(unique = true)
    private String cuit;

    private String domicilio;
    private String codPostal;
    private String localidad;
    private String provincia;
    private String pais;

    //ToDo: ver condiciones
    private String transporte;

    @NotNull //ToDo: ver condiciones
    private String senasaUta;

    @ManyToOne
    @JoinColumn(name = "idTipoCliente")
    @NotNull
    private TipoCliente tipoCliente;

//    ToDo: aca esta el tema de que tal vez mejor no cargarlo aca, y si se quieren las devoluciones y expediciones del cliente, mejor buscarlas por su id en las tablas de esas entities
//    @OneToMany(cascade = CascadeType.ALL) // ToDo: ver el fetch y el orphan removal
//    @JoinColumn(name = "idDevolucion")
//    private List<Devolucion> devoluciones;
//
//    @OneToMany(cascade = CascadeType.ALL) // ToDo: ver el fetch y el orphan removal
//    @JoinColumn(name = "idExpedicion")
//    private List<Expedicion> expediciones;
}
