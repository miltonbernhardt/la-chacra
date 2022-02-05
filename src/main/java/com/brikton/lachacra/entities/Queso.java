package com.brikton.lachacra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Queso {

    @Id
    @Column(name = "codigo_queso")
    private String codigo;
    private String tipoQueso;
    @Column(unique = true)
    private String nomenclatura;
    private int stock;

    @OneToMany
    @JoinColumn(name = "id_precio")
    private List<Precio> preciosActual;
}
