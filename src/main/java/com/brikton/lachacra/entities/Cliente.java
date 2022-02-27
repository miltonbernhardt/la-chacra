package com.brikton.lachacra.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "nro_cliente")
    private Long id;
    private String razonSocial;
    private String cuit;
    private String domicilio;
    private String codPostal;
    private String localidad;
    private String provincia;
    private String pais;
    private String transporte;
    private String senasaUta;
    private String telefono;
    private String celular;
    private String fax;
    private String email;
    private LocalDate fechaBaja;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cliente")
    @NotNull
    private TipoCliente tipoCliente;
}
