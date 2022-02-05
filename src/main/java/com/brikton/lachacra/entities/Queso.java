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

//    @Id
//    @Column(name = "id_queso")
//    private Integer id; //todo si no es autogenerado le ponemos como string?

    @Id
    //todo estamos duplicando esto a menos que lo hagamos pk
    @Column(name = "codigo_queso")
    private String codigo;

    @NotBlank
    private String tipoQueso;

    @Column(unique = true)
    @NotBlank
    @Length(min = 1, max = 20)
    private String nomenclatura;

    @NotNull
    private int stock;

    @OneToMany // ToDo: ver el cascade
    @JoinColumn(name = "id_precio")
    private List<Precio> preciosActual;
}
