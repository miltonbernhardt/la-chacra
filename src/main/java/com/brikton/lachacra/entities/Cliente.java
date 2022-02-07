package com.brikton.lachacra.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cliente cliente = (Cliente) o;
        return id != null && Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
