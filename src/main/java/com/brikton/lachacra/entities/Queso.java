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
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Queso {

    @Id
    @Column(name = "codigoQueso") //ToDo:es generado o lo tienen asignado?
    private long codigo;

    @NotBlank
    private String tipoQueso;

    @Column(unique = true)
    @NotBlank
    @Length(min = 1, max = 20)
    private String nomenclatura; //ToDo:unique key

    @NotNull
    private int stock;

    @OneToMany// ToDo: ver el cascade
    @JoinColumn(name = "idPrecio")
    private List<Precio> preciosActual;
}
